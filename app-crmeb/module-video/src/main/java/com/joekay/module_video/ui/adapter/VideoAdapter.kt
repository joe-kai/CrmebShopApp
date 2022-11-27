package com.joekay.module_video.ui.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.joekay.base.ext.*
import com.joekay.base.utils.DateUtil
import com.joekay.base.utils.GlobalUtil
import com.joekay.module_video.BuildConfig
import com.joekay.module_video.R
import com.joekay.module_video.model.Follow
import com.joekay.module_video.model.VideoInfo
import com.joekay.module_video.ui.VideoFragment
import com.joekay.module_video.ui.details.VideoDetailActivity
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import javax.inject.Inject

/**
 * @author:  JoeKai
 * @date:  2022/11/27
 * @explain：
 */
class VideoAdapter @Inject constructor(
    val fragment: VideoFragment
) :
    PagingDataAdapter<Follow.Item, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
    override fun getItemCount() = super.getItemCount() + 1

    override fun getItemViewType(position: Int) = when {
        position == 0 -> 0
        getItem(position - 1)!!.type == "autoPlayFollowCard" && getItem(position - 1)!!.data.dataType == "FollowCard" -> AUTO_PLAY_FOLLOW_CARD
        else -> -1
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            0 -> HeaderViewHolder(
                R.layout.video_header_type.inflate(
                    parent
                )
                //LayoutInflater.from(fragment.context)
                //    .inflate(R.layout.video_header_type, parent, false)

            )
            AUTO_PLAY_FOLLOW_CARD -> VideoViewHolder(
                R.layout.layout_video_item.inflate(
                    parent
                )
                //LayoutInflater.from(fragment.context)
                //    .inflate(R.layout.layout_video_item, parent, false)
            )
            else -> EmptyViewHolder(View(parent.context))
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.itemView.setOnClickListener {
                "没有登录".showToast()
            }

            is VideoViewHolder -> {
                val item = getItem(position - 1)!!
                item.data.content.data.run {
                    holder.ivAvatar.loadUrl(item.data.header.icon ?: author?.icon ?: "")
                    holder.tvReleaseTime.text = DateUtil.getDate(
                        releaseTime ?: author?.latestReleaseTime ?: System.currentTimeMillis(),
                        "HH:mm"
                    )
                    holder.tvTitle.text = title
                    holder.tvNickname.text = author?.name ?: ""
                    holder.tvContent.text = description
                    holder.tvCollectionCount.text = consumption.collectionCount.toString()
                    holder.tvReplyCount.text = consumption.replyCount.toString()
                    holder.tvVideoDuration.visible()    //视频播放后，复用tvVideoDuration直接隐藏了
                    holder.tvVideoDuration.text = duration.conversionVideoDuration()
                    startAutoPlay(
                        fragment.requireActivity(),
                        holder.videoPlayer,
                        position,
                        playUrl,
                        cover.feed,
                        TAG,
                        object : GSYSampleCallBack() {
                            override fun onPrepared(url: String?, vararg objects: Any?) {
                                super.onPrepared(url, *objects)
                                holder.tvVideoDuration.gone()
                                GSYVideoManager.instance().isNeedMute = true
                            }

                            override fun onClickResume(url: String?, vararg objects: Any?) {
                                super.onClickResume(url, *objects)
                                holder.tvVideoDuration.gone()
                            }

                            override fun onClickBlank(url: String?, vararg objects: Any?) {
                                super.onClickBlank(url, *objects)
                                holder.tvVideoDuration.visible()
                                VideoDetailActivity.start(
                                    fragment.requireActivity(),
                                    VideoInfo(
                                        id,
                                        playUrl,
                                        title,
                                        description,
                                        category,
                                        library,
                                        consumption,
                                        cover,
                                        author!!,
                                        webUrl
                                    )
                                )
                            }
                        })
                    holder.let {
                        setOnClickListener(
                            it.videoPlayer.thumbImageView,
                            it.itemView,
                            it.ivCollectionCount,
                            it.tvCollectionCount,
                            it.ivFavorites,
                            it.tvFavorites,
                            it.ivShare
                        )
                        {
                            when (this) {
                                it.videoPlayer.thumbImageView, it.itemView -> {
                                    VideoDetailActivity.start(
                                        fragment.requireActivity(),
                                        VideoInfo(
                                            item.data.content.data.id,
                                            playUrl,
                                            title,
                                            description,
                                            category,
                                            library,
                                            consumption,
                                            cover,
                                            author!!,
                                            webUrl
                                        )
                                    )
                                }
                                it.ivCollectionCount, it.tvCollectionCount, it.ivFavorites, it.tvFavorites -> {
                                    //LoginActivity.start(fragment.activity)
                                    "登录".showToast()
                                }
                                it.ivShare -> {
                                    ShowDialogShare(fragment.requireActivity(), getShareContent(item))
                                }
                            }
                        }
                    }
                }
            }
            else -> {
                holder.itemView.gone()
                if (BuildConfig.DEBUG) "${TAG}:${"Const.Toast.BIND_VIEWHOLDER_TYPE_WARN"}\n${holder}".showToast()
            }
        }
    }


    private fun getShareContent(item: Follow.Item): String {
        item.data.content.data.run {
            val linkUrl =
                "${item.data.content.data.webUrl.raw}&utm_campaign=routine&utm_medium=share&utm_source=others&uid=0&resourceType=${resourceType}"
            return "${title}|${GlobalUtil.appName}：\n${linkUrl}"
        }
    }

    companion object {
        const val TAG = "FollowAdapter"
        const val AUTO_PLAY_FOLLOW_CARD = 100       //type:autoPlayFollowCard -> dataType:FollowCard

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Follow.Item>() {
            override fun areItemsTheSame(oldItem: Follow.Item, newItem: Follow.Item): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Follow.Item, newItem: Follow.Item): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view)
    inner class EmptyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    inner class VideoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivAvatar = view.findViewById<ImageView>(R.id.ivAvatar)
        val tvReleaseTime = view.findViewById<TextView>(R.id.tvReleaseTime)
        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        val tvNickname = view.findViewById<TextView>(R.id.tvNickname)
        val tvContent = view.findViewById<TextView>(R.id.tvContent)
        val ivCollectionCount = view.findViewById<ImageView>(R.id.ivCollectionCount)
        val tvCollectionCount = view.findViewById<TextView>(R.id.tvCollectionCount)
        val ivReply = view.findViewById<ImageView>(R.id.ivReply)
        val tvReplyCount = view.findViewById<TextView>(R.id.tvReplyCount)
        val ivFavorites = view.findViewById<ImageView>(R.id.ivFavorites)
        val tvFavorites = view.findViewById<TextView>(R.id.tvFavorites)
        val tvVideoDuration = view.findViewById<TextView>(R.id.tvVideoDuration)
        val ivShare = view.findViewById<ImageView>(R.id.ivShare)
        val videoPlayer: GSYVideoPlayer = view.findViewById<GSYVideoPlayer>(R.id.videoPlayer)
    }

    private fun startAutoPlay(
        activity: Activity,
        player: GSYVideoPlayer,
        position: Int,
        playUrl: String,
        coverUrl: String,
        playTag: String,
        callBack: GSYSampleCallBack? = null
    ) {
        player.run {
            //防止错位设置
            setPlayTag(playTag)
            //设置播放位置防止错位
            playPosition = position
            //音频焦点冲突时是否释放
            isReleaseWhenLossAudio = false
            //设置循环播放
            isLooping = true
            //增加封面
            val cover = ImageView(activity)
            cover.scaleType = ImageView.ScaleType.CENTER_CROP
            cover.load(coverUrl, 4f)
            cover.parent?.run { removeView(cover) }
            thumbImageView = cover
            //设置播放过程中的回调
            setVideoAllCallBack(callBack)
            //设置播放URL
            setUp(playUrl, false, null)
        }
    }
}