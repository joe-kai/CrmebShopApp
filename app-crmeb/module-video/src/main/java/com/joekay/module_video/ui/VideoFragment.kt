package com.joekay.module_video.ui

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonSyntaxException
import com.joekay.base.ext.gone
import com.joekay.base.ext.logW
import com.joekay.base.ext.showToast
import com.joekay.base.ext.visible
import com.joekay.base.paging.FooterAdapter
import com.joekay.base.utils.GlobalUtil
import com.joekay.base.widgets.AutoPlayScrollListener
import com.joekay.module_base.base.BaseFragment
import com.joekay.module_base.event.MessageEvent
import com.joekay.module_base.event.RefreshEvent
import com.joekay.module_video.R
import com.joekay.module_video.databinding.FragmentVideoBinding
import com.joekay.module_video.ui.adapter.VideoAdapter
import com.joekay.resource.R.string
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

@Route(path = RouterPath.frag_video)
@AndroidEntryPoint
class VideoFragment : BaseFragment<FragmentVideoBinding>() {
    private val viewModel by viewModels<VideoViewModel>()

    lateinit var videoAdapter: VideoAdapter

    override fun initObserve() {
        videoAdapter = VideoAdapter(this)

        lifecycleScope.launch {
            viewModel.getPagingData().collect {
                videoAdapter.submitData(it)
            }
        }
    }

    override fun initBinding() {
        mBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        mBinding.recyclerView.adapter =
            videoAdapter.withLoadStateFooter(FooterAdapter { videoAdapter.retry() })
        mBinding.recyclerView.setHasFixedSize(true)
        mBinding.recyclerView.itemAnimator = null
        mBinding.recyclerView.addOnScrollListener(
            AutoPlayScrollListener(
                R.id.videoPlayer,
                AutoPlayScrollListener.PLAY_RANGE_TOP,
                AutoPlayScrollListener.PLAY_RANGE_BOTTOM
            )
        )
        mBinding.refreshLayout.setOnRefreshListener { videoAdapter.refresh() }
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
            if (mBinding.recyclerView.adapter?.itemCount ?: 0 > 0) mBinding.recyclerView.scrollToPosition(
                0
            )
        }
    }


    private fun addLoadStateListener() {
        videoAdapter.addLoadStateListener {
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
        videoAdapter.addLoadStateListener {
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
        logW("TAG", "getFailureTips exception is ${e?.message}")
        return when (e) {
            is ConnectException -> getString(string.network_connect_error)
            is SocketTimeoutException -> getString(string.network_connect_timeout)
            is NoRouteToHostException -> getString(string.no_route_to_host)
            is UnknownHostException -> getString(string.network_error)
            is JsonSyntaxException -> getString(string.json_data_error)
            else -> {
                " GlobalUtil.getString(string.unknown_error)"
            }
        }
    }

}