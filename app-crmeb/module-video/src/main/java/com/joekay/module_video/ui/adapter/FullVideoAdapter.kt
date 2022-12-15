package com.joekay.module_video.ui.adapter

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import com.joekay.base.adapter.BasePagingAdapter
import com.joekay.base.adapter.PagingItemCallback
import com.joekay.base.ext.gone
import com.joekay.base.ext.load
import com.joekay.base.ext.visible
import com.joekay.module_video.R
import com.joekay.module_video.databinding.LayoutFullVideoItemBinding
import com.joekay.module_video.model.Follow
import com.joekay.module_video.model.VideoInfo
import com.joekay.module_video.ui.details.VideoDetailActivity
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

/**
 * @author:  JoeKai
 * @date:  2022/12/5
 * @explain：
 */
class FullVideoAdapter @Inject constructor(
    @ActivityContext context: Context
) : BasePagingAdapter<Follow.Item>(context, CALLBACK) {
    companion object {
        val CALLBACK = object : PagingItemCallback<Follow.Item>() {
            override fun areContentsTheSame(oldItem: Follow.Item, newItem: Follow.Item): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingViewHolder {
        return FullViewHolder(R.layout.layout_full_video_item)
    }

    inner class FullViewHolder(id: Int) : PagingViewHolder(id) {
        private val binding by lazy {
            LayoutFullVideoItemBinding.bind(getItemView())
        }

        override fun onBindView(position: Int) {
            val item = getAdapterItem(position)
            item.data.content.data.run {
                binding.ivAvatar.load(item.data.header.icon ?: author?.icon ?: "")
                binding.tvNickname.text = author?.name ?: ""
                binding.tvContent.text = description
                binding.tvCollectionCount.text = consumption.collectionCount.toString()
                binding.tvReplyCount.text = consumption.replyCount.toString()
                startAutoPlay(
                    getContext(),
                    binding.videoPlayer,
                    position,
                    playUrl,
                    cover.feed,
                    VideoAdapter.TAG,
                    object : GSYSampleCallBack() {
                        override fun onPrepared(url: String?, vararg objects: Any?) {
                            super.onPrepared(url, *objects)
                            //holder.tvVideoDuration.gone()
                            GSYVideoManager.instance().isNeedMute = false
                        }

                        override fun onClickResume(url: String?, vararg objects: Any?) {
                            super.onClickResume(url, *objects)
                        }

                        override fun onClickBlank(url: String?, vararg objects: Any?) {
                            super.onClickBlank(url, *objects)

                        }
                    })
            }

        }

    }

    private fun startAutoPlay(
        context: Context,
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
            val cover = ImageView(context)
            //cover.scaleType = ImageView.ScaleType.CENTER_CROP
            cover.load(coverUrl)
            cover.parent?.run { removeView(cover) }
            thumbImageView = cover
            //设置播放过程中的回调
            setVideoAllCallBack(callBack)
            //设置播放URL
            setUp(playUrl, false, null)
        }
    }
}