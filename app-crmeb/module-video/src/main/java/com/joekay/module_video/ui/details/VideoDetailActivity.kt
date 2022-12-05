package com.joekay.module_video.ui.details

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.joekay.base.ext.*
import com.joekay.base.utils.*
import com.joekay.base.widgets.NoStatusFooter
import com.joekay.module_base.base.BaseActivity
import com.joekay.module_video.databinding.ActivityVideoDetailBinding
import com.joekay.module_video.model.VideoInfo
import com.joekay.resource.RouterPath
import com.shuyu.gsyvideoplayer.GSYVideoADManager
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoView
import com.therouter.router.Route
import kotlinx.coroutines.*

@Suppress("DEPRECATION")
@Route(path = RouterPath.act_videoDetail)
class VideoDetailActivity : BaseActivity<ActivityVideoDetailBinding>() {

    //private val viewModel by viewModels<VideoDetailViewModel>()
    private var orientationUtils: OrientationUtils? = null
    private val viewModel by lazy { ViewModelProvider(this)[VideoDetailViewModel::class.java] }

    private val globalJob by lazy { Job() }

    private var hideTitleBarJob: Job? = null

    private var hideBottomContainerJob: Job? = null

    override fun initObserve() {
    }

    override fun initBinding() {
        setDarkStatusBar(false)
        setupViews()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        if (checkArguments()) {
            initParams()
            startVideoPlayer()
            viewModel.onRefresh()
        }
    }

    override fun onPause() {
        super.onPause()
        logD(TAG, "onPause")
        mBinding.videoPlayer.onVideoPause()
    }

    override fun onResume() {
        super.onResume()
        logD(TAG, "onResume")
        mBinding.videoPlayer.onVideoResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        logD(TAG, "onDestroy")
        GSYVideoADManager.releaseAllVideos()
        orientationUtils?.releaseListener()
        mBinding.videoPlayer.release()
        mBinding.videoPlayer.setVideoAllCallBack(null)
        globalJob.cancel()

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        logD(TAG, "onBackPressed()")
        orientationUtils?.backToProtVideo()
        if (GSYVideoManager.backFromWindowFull(this)) return
        finish()
    }

    override fun finish() {
        GSYVideoADManager.releaseAllVideos()
        orientationUtils?.releaseListener()
        mBinding.videoPlayer.release()
        mBinding.videoPlayer.setVideoAllCallBack(null)
        globalJob.cancel()
        overridePendingTransition(0, com.joekay.base.R.anim.window_bottom_out)
        super.finish()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mBinding.videoPlayer.onConfigurationChanged(this, newConfig, orientationUtils, true, true)
    }

    private fun setupViews() {

        initParams()
        orientationUtils = OrientationUtils(this, mBinding.videoPlayer)
        //relatedAdapter = NewDetailRelatedAdapter(this, viewModel.relatedDataList, viewModel.videoInfoData)
        //replyAdapter = NewDetailReplyAdapter(this, viewModel.repliesDataList)
        //mergeAdapter = ConcatAdapter(relatedAdapter, replyAdapter)
        mBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        //binding.recyclerView.adapter = mergeAdapter
        mBinding.recyclerView.setHasFixedSize(true)
        mBinding.recyclerView.itemAnimator = null
        mBinding.refreshLayout.run {
            setDragRate(0.7f)
            setHeaderTriggerRate(0.6f)
            setFooterTriggerRate(0.6f)
            setEnableLoadMoreWhenContentNotFull(true)
            setEnableFooterFollowWhenNoMoreData(true)
            setEnableFooterTranslationContent(true)
            setEnableScrollContentWhenLoaded(true)
            setEnableNestedScroll(true)
            setFooterHeight(153f)
            setRefreshFooter(NoStatusFooter(this@VideoDetailActivity).apply {
                setAccentColorId(com.joekay.resource.R.color.white)
                setTextTitleSize(16f)
            })
            closeHeaderOrFooter()
            setOnRefreshListener { finish() }
            setOnLoadMoreListener { viewModel.onLoadMore() }
        }
        setOnClickListener(
            mBinding.ivPullDown,
            mBinding.ivMore,
            mBinding.ivShare,
            mBinding.ivCollection,
            mBinding.ivToWechatFriends,
            mBinding.ivShareToWechatMemories,
            mBinding.ivShareToWeibo,
            mBinding.ivShareToQQ,
            mBinding.ivShareToQQzone,
            mBinding.ivAvatar,
            mBinding.etComment,
            mBinding.ivReply,
            mBinding.tvReplyCount,
            listener = ClickListener()
        )
        //observe()
        startVideoPlayer()
        viewModel.onRefresh()
    }

    private fun checkArguments() =
        if (intent.getParcelableExtra<VideoInfo>(EXTRA_VIDEOINFO) == null && intent.getLongExtra(
                EXTRA_VIDEO_ID,
                0L
            ) == 0L
        ) {
            getString(com.joekay.base.R.string.jump_page_unknown_error).showToast()
            finish()
            false
        } else {
            true
        }

    private fun initParams() {
        if (intent.getParcelableExtra<VideoInfo>(EXTRA_VIDEOINFO) != null) viewModel.videoInfoData =
            intent.getParcelableExtra(EXTRA_VIDEOINFO)
        if (intent.getLongExtra(EXTRA_VIDEO_ID, 0L) != 0L) viewModel.videoId =
            intent.getLongExtra(EXTRA_VIDEO_ID, 0L)
    }

    private fun startVideoPlayer() {
        viewModel.videoInfoData?.run {
            mBinding.ivBlurredBg.load(cover.blurred)
            mBinding.tvReplyCount.text = consumption.replyCount.toString()
            mBinding.videoPlayer.startPlay()
        }
    }

    //private fun observe() {
    //    //刷新，视频信息+相关推荐+评论
    //    if (!viewModel.videoDetailLiveData.hasObservers()) {
    //        viewModel.videoDetailLiveData.observe(this, Observer { result ->
    //            val response = result.getOrNull()
    //            if (response == null) {
    //                ResponseHandler.getFailureTips(result.exceptionOrNull()).showToast()
    //                return@Observer
    //            }
    //            viewModel.nextPageUrl = response.videoReplies.nextPageUrl
    //            if (response.videoRelated == null || response.videoRelated.itemList.isNullOrEmpty() && response.videoReplies.itemList.isNullOrEmpty()) {
    //                return@Observer
    //            }
    //            response.videoBeanForClient?.run {
    //                viewModel.videoInfoData = VideoInfo(id, playUrl, title, description, category, library, consumption, cover, author, webUrl)
    //                startVideoPlayer()
    //                relatedAdapter.bindVideoInfo(viewModel.videoInfoData)
    //            }
    //            viewModel.relatedDataList.clear()
    //            viewModel.repliesDataList.clear()
    //            viewModel.relatedDataList.addAll(response.videoRelated.itemList)
    //            viewModel.repliesDataList.addAll(response.videoReplies.itemList)
    //            relatedAdapter.notifyDataSetChanged()
    //            replyAdapter.notifyDataSetChanged()
    //            when {
    //                viewModel.repliesDataList.isNullOrEmpty() -> binding.refreshLayout.finishLoadMoreWithNoMoreData()
    //                response.videoReplies.nextPageUrl.isNullOrEmpty() -> binding.refreshLayout.finishLoadMoreWithNoMoreData()
    //                else -> binding.refreshLayout.closeHeaderOrFooter()
    //            }
    //        })
    //    }
    //    //刷新，相关推荐+评论
    //    if (!viewModel.repliesAndRepliesLiveData.hasObservers()) {
    //        viewModel.repliesAndRepliesLiveData.observe(this, Observer { result ->
    //            val response = result.getOrNull()
    //            if (response == null) {
    //                ResponseHandler.getFailureTips(result.exceptionOrNull()).showToast()
    //                return@Observer
    //            }
    //            viewModel.nextPageUrl = response.videoReplies.nextPageUrl
    //            if (response.videoRelated == null || response.videoRelated.itemList.isNullOrEmpty() && response.videoReplies.itemList.isNullOrEmpty()) {
    //                return@Observer
    //            }
    //            viewModel.relatedDataList.clear()
    //            viewModel.repliesDataList.clear()
    //            viewModel.relatedDataList.addAll(response.videoRelated.itemList)
    //            viewModel.repliesDataList.addAll(response.videoReplies.itemList)
    //            relatedAdapter.bindVideoInfo(viewModel.videoInfoData)
    //            relatedAdapter.notifyDataSetChanged()
    //            replyAdapter.notifyDataSetChanged()
    //            when {
    //                viewModel.repliesDataList.isNullOrEmpty() -> binding.refreshLayout.finishLoadMoreWithNoMoreData()
    //                response.videoReplies.nextPageUrl.isNullOrEmpty() -> binding.refreshLayout.finishLoadMoreWithNoMoreData()
    //                else -> binding.refreshLayout.closeHeaderOrFooter()
    //            }
    //        })
    //    }
    //    //上拉加载，评论
    //    if (!viewModel.repliesLiveData.hasObservers()) {
    //        viewModel.repliesLiveData.observe(this, Observer { result ->
    //            val response = result.getOrNull()
    //            if (response == null) {
    //                ResponseHandler.getFailureTips(result.exceptionOrNull()).showToast()
    //                return@Observer
    //            }
    //            viewModel.nextPageUrl = response.nextPageUrl
    //            if (response.itemList.isNullOrEmpty()) {
    //                return@Observer
    //            }
    //            val itemCount = replyAdapter.itemCount
    //            viewModel.repliesDataList.addAll(response.itemList)
    //            replyAdapter.notifyItemRangeInserted(itemCount, response.itemList.size)
    //            if (response.nextPageUrl.isNullOrEmpty()) {
    //                binding.refreshLayout.finishLoadMoreWithNoMoreData()
    //            } else {
    //                binding.refreshLayout.closeHeaderOrFooter()
    //            }
    //        })
    //    }
    //}

    private fun GSYVideoPlayer.startPlay() {
        viewModel.videoInfoData?.let {
            //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
            fullscreenButton.setOnClickListener {
                showFull()
            }
            //防止错位设置
            playTag = it.playUrl
            //音频焦点冲突时是否释放
            isReleaseWhenLossAudio = false
            //增加封面
            val imageView = ImageView(this@VideoDetailActivity)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.load(it.cover.detail)
            thumbImageView = imageView
            thumbImageView.setSingleClickListener { switchTitleBarVisible() }
            //是否开启自动旋转
            isRotateViewAuto = false
            //是否需要全屏锁定屏幕功能
            isNeedLockFull = true
            //是否可以滑动调整
            setIsTouchWiget(true)
            //设置触摸显示控制ui的消失时间
            dismissControlTime = 5000
            //设置播放过程中的回调
            setVideoAllCallBack(VideoCallPlayBack())
            //设置播放URL
            setUp(it.playUrl, false, it.title)
            //开始播放
            startPlayLogic()
        }
    }

    private fun switchTitleBarVisible() {
        if (mBinding.videoPlayer.currentPlayer.currentState == GSYVideoView.CURRENT_STATE_AUTO_COMPLETE) return
        if (mBinding.flHeader.visibility == View.VISIBLE) {
            hideTitleBar()
        } else {
            mBinding.flHeader.visibleAlphaAnimation(1000)
            mBinding.ivPullDown.visibleAlphaAnimation(1000)
            mBinding.ivCollection.visibleAlphaAnimation(1000)
            mBinding.ivMore.visibleAlphaAnimation(1000)
            mBinding.ivShare.visibleAlphaAnimation(1000)
            delayHideTitleBar()
        }
    }

    private fun hideTitleBar() {
        mBinding.flHeader.invisibleAlphaAnimation(1000)
        mBinding.ivPullDown.goneAlphaAnimation(1000)
        mBinding.ivCollection.goneAlphaAnimation(1000)
        mBinding.ivMore.goneAlphaAnimation(1000)
        mBinding.ivShare.goneAlphaAnimation(1000)
    }

    private fun delayHideTitleBar() {
        hideTitleBarJob?.cancel()
        hideTitleBarJob = CoroutineScope(globalJob).launch(Dispatchers.Main) {
            delay(mBinding.videoPlayer.dismissControlTime.toLong())
            hideTitleBar()
        }
    }

    private fun delayHideBottomContainer() {
        hideBottomContainerJob?.cancel()
        hideBottomContainerJob = CoroutineScope(globalJob).launch(Dispatchers.Main) {
            delay(mBinding.videoPlayer.dismissControlTime.toLong())
            mBinding.videoPlayer.getBottomContainer().gone()
            mBinding.videoPlayer.startButton.gone()
        }
    }

    private fun showFull() {
        orientationUtils?.resolveByClick()
    }

    fun scrollTop() {
        //if (relatedAdapter.itemCount != 0) {
        //    binding.recyclerView.scrollToPosition(0)
        //    binding.refreshLayout.invisibleAlphaAnimation(2500)
        //    binding.refreshLayout.visibleAlphaAnimation(1500)
        //}
    }

    //private fun scrollRepliesTop() {
    //    val targetPostion = (relatedAdapter.itemCount - 1) + 2  //+相关推荐最后一项，+1评论标题，+1条评论
    //    if (targetPostion < mergeAdapter.itemCount - 1) {
    //        binding.recyclerView.smoothScrollToPosition(targetPostion)
    //    }
    //}

    inner class VideoCallPlayBack : GSYSampleCallBack() {
        override fun onStartPrepared(url: String?, vararg objects: Any?) {
            super.onStartPrepared(url, *objects)
            mBinding.flHeader.gone()
            mBinding.llShares.gone()
        }

        override fun onClickBlank(url: String?, vararg objects: Any?) {
            super.onClickBlank(url, *objects)
            switchTitleBarVisible()
        }

        override fun onClickStop(url: String?, vararg objects: Any?) {
            super.onClickStop(url, *objects)
            delayHideBottomContainer()
        }

        override fun onAutoComplete(url: String?, vararg objects: Any?) {
            super.onAutoComplete(url, *objects)
            mBinding.flHeader.visible()
            mBinding.ivPullDown.visible()
            mBinding.ivCollection.gone()
            mBinding.ivShare.gone()
            mBinding.ivMore.gone()
            mBinding.llShares.visible()
        }
    }

    inner class ClickListener : View.OnClickListener {
        override fun onClick(v: View) {
            viewModel.videoInfoData?.let {
                when (v) {
                    mBinding.ivPullDown -> finish()
                    mBinding.ivMore -> {
                    }
                    mBinding.ivShare -> {
                        showDialogShare(it.webUrl.raw)
                        //it.webUrl.raw.showToast()
                    }
                    mBinding.ivCollection -> {
                        //LoginActivity.start(this@NewDetailActivity)
                        "登录".showToast()
                    }
                    mBinding.ivToWechatFriends -> {
                        share(it.webUrl.raw, SHARE_WECHAT)
                        it.webUrl.raw.showToast()
                    }

                    mBinding.ivShareToWechatMemories -> {
                        share(it.webUrl.raw, SHARE_WECHAT_MEMORIES)
                    }
                    mBinding.ivShareToWeibo -> {
                        share(it.webUrl.forWeibo, SHARE_WEIBO)
                    }
                    mBinding.ivShareToQQ -> {
                        share(it.webUrl.raw, SHARE_QQ)
                    }
                    mBinding.ivShareToQQzone -> {
                        share(it.webUrl.raw, SHARE_QQZONE)
                    }
                    mBinding.ivAvatar, mBinding.etComment -> {
                        //LoginActivity.start(this@NewDetailActivity)
                        "登录".showToast()
                    }
                    mBinding.ivReply, mBinding.tvReplyCount -> {
                        //scrollRepliesTop()
                        "滑动到".showToast()
                    }
                    else -> {
                    }
                }
            }
        }
    }

    companion object {
        const val TAG = "NewDetailActivity"

        const val EXTRA_VIDEOINFO = "videoInfo"
        const val EXTRA_VIDEO_ID = "videoId"

        fun start(context: Activity, videoInfo: VideoInfo) {
            val starter = Intent(context, VideoDetailActivity::class.java)
            starter.putExtra(EXTRA_VIDEOINFO, videoInfo)
            context.startActivity(starter)
            context.overridePendingTransition(
                com.joekay.base.R.anim.window_bottom_in,
                com.joekay.base.R.anim.window_bottom_out
            )
        }

        fun start(context: Activity, videoId: Long) {
            val starter = Intent(context, VideoDetailActivity::class.java)
            starter.putExtra(EXTRA_VIDEO_ID, videoId)
            context.startActivity(starter)
            context.overridePendingTransition(
                com.joekay.base.R.anim.window_bottom_in,
                com.joekay.base.R.anim.window_bottom_out
            )
        }
    }

}