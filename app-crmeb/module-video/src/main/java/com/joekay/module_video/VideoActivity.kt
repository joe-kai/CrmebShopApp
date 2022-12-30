package com.joekay.module_video

import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.OrientationHelper
import com.google.gson.JsonSyntaxException
import com.joekay.base.adapter.VideoLayoutManager
import com.joekay.base.ext.*
import com.joekay.base.paging.FooterAdapter
import com.joekay.base.widgets.FullVideoPlayer
import com.joekay.module_base.base.BaseActivity
import com.joekay.module_base.event.MessageEvent
import com.joekay.module_base.event.RefreshEvent
import com.joekay.module_video.databinding.ActivityVideoBinding
import com.joekay.module_video.ui.VideoViewModel
import com.joekay.module_video.ui.adapter.FullVideoAdapter
import com.joekay.resource.RouterPath
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.therouter.router.Route
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@Route(path = RouterPath.act_video)
@AndroidEntryPoint
class VideoActivity : BaseActivity<ActivityVideoBinding>() {
    private val viewModel by viewModels<VideoViewModel>()

    @Inject
    lateinit var fullVideoAdapter: FullVideoAdapter

    override fun initObserve() {
        setDarkStatusBar(false)
        lifecycleScope.launch {
            viewModel.getPagingData().collect {
                fullVideoAdapter.submitData(it)
            }
        }
    }

    override fun initBinding() {
        val layoutManager = VideoLayoutManager(this, OrientationHelper.VERTICAL)
        mBinding.recyclerView.layoutManager = layoutManager
        mBinding.recyclerView.adapter =
            fullVideoAdapter.withLoadStateFooter(FooterAdapter { fullVideoAdapter.retry() })
        mBinding.recyclerView.setHasFixedSize(true)
        mBinding.recyclerView.itemAnimator = null
        mBinding.recyclerView.addOnScrollListener(
            FullVideoPlayer.FullAutoPlayScrollListener(
                R.id.videoPlayer
            )
        )
        mBinding.refreshLayout.setOnRefreshListener { fullVideoAdapter.refresh() }
        mBinding.refreshLayout.gone()
        addLoadStateListener()
    }

    private fun loadFinished() {
        mBinding.refreshLayout.visible()
        mBinding.refreshLayout.finishRefresh()

    }

    private fun startLoading() {
        mBinding.refreshLayout.gone()
    }

    private fun loadFailed(msg: String?) {
        mBinding.refreshLayout.finishRefresh()
        msg!!.showToast()

    }

    override fun onMessageEvent(messageEvent: MessageEvent) {
        super.onMessageEvent(messageEvent)
        if (messageEvent is RefreshEvent && javaClass == messageEvent.activityClass) {
            mBinding.refreshLayout.autoRefresh()
            if ((mBinding.recyclerView.adapter?.itemCount
                    ?: 0) > 0
            ) mBinding.recyclerView.scrollToPosition(
                0
            )
        }
    }


    private fun addLoadStateListener() {
        fullVideoAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.NotLoading -> {
                    loadFinished()
                    if (it.source.append.endOfPaginationReached) {
                        mBinding.refreshLayout.setEnableLoadMore(true)
                        mBinding.refreshLayout.finishLoadMoreWithNoMoreData()
                    } else {
                        mBinding.refreshLayout.setEnableLoadMore(false)
                    }
                }
                is LoadState.Loading -> {
                    if (mBinding.refreshLayout.state != RefreshState.Refreshing) {
                        startLoading()
                    }
                }
                is LoadState.Error -> {
                    val state = it.refresh as LoadState.Error
                    loadFailed(getFailureTips(state.error))
                }
            }
        }
        fullVideoAdapter.addLoadStateListener {
            when (it.append) {
                is LoadState.NotLoading -> {
                    if (it.source.append.endOfPaginationReached) {
                        mBinding.refreshLayout.setEnableLoadMore(true)
                        mBinding.refreshLayout.finishLoadMoreWithNoMoreData()
                    } else {
                        mBinding.refreshLayout.setEnableLoadMore(false)
                    }
                }
                is LoadState.Loading -> {
                }
                is LoadState.Error -> {
                    val state = it.append as LoadState.Error
                    getFailureTips(state.error).showToast()
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        GSYVideoManager.onPause()
    }

    override fun onResume() {
        super.onResume()
        GSYVideoManager.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
    }

    /**
     * 当网络请求没有正常响应的时候，根据异常类型进行相应的处理。
     * @param e 异常实体类
     */
    private fun getFailureTips(e: Throwable?): String {
        mLogW("getFailureTips exception is ${e?.message}")
        return when (e) {
            is ConnectException -> getString(com.joekay.resource.R.string.network_connect_error)
            is SocketTimeoutException -> getString(com.joekay.resource.R.string.network_connect_timeout)
            is NoRouteToHostException -> getString(com.joekay.resource.R.string.no_route_to_host)
            is UnknownHostException -> getString(com.joekay.resource.R.string.network_error)
            is JsonSyntaxException -> getString(com.joekay.resource.R.string.json_data_error)
            else -> {
                " GlobalUtil.getString(string.unknown_error)"
            }
        }
    }

}