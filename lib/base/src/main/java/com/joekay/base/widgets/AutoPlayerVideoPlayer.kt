package com.joekay.base.widgets

import android.app.AlertDialog
import android.content.Context
import android.graphics.Rect
import android.os.Handler
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoView
import com.joekay.base.R
import com.joekay.base.ext.dp2px
import com.joekay.base.ext.gone
import com.joekay.base.ext.logD
import com.joekay.base.ext.screenHeight
import com.shuyu.gsyvideoplayer.utils.CommonUtil
import com.shuyu.gsyvideoplayer.utils.NetworkUtils
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer

/**
 * @author:  JoeKai
 * @date:  2022/11/27
 * @explain：常见列表，视频播放器。
 */
class AutoPlayerVideoPlayer:StandardGSYVideoPlayer {
    var start: ImageView? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, fullFlag: Boolean?) : super(context, fullFlag)

    override fun getLayoutId() = R.layout.layout_auto_play_video_player

    override fun init(context: Context?) {
        super.init(context)
        start = findViewById(R.id.start)
    }

    override fun touchSurfaceMoveFullLogic(absDeltaX: Float, absDeltaY: Float) {
        super.touchSurfaceMoveFullLogic(absDeltaX, absDeltaY)
        //不给触摸快进，如果需要，屏蔽下方代码即可
        mChangePosition = false
        //不给触摸音量，如果需要，屏蔽下方代码即可
        mChangeVolume = false
        //不给触摸亮度，如果需要，屏蔽下方代码即可
        mBrightness = false
    }

    override fun updateStartImage() {
        if (mStartButton is ImageView) {
            val imageView = mStartButton as ImageView
            when (mCurrentState) {
                GSYVideoView.CURRENT_STATE_PLAYING -> imageView.setImageResource(R.drawable.ic_pause_white_24dp)
                GSYVideoView.CURRENT_STATE_ERROR -> imageView.setImageResource(R.drawable.ic_play_white_24dp)
                else -> imageView.setImageResource(R.drawable.ic_play_white_24dp)
            }
        } else {
            super.updateStartImage()
        }
    }

    override fun touchDoubleUp(e: MotionEvent?) {
        super.touchDoubleUp(e)
        //不需要双击暂停
    }

    //正常
    override fun changeUiToNormal() {
        super.changeUiToNormal()
        logD(javaClass.simpleName, "changeUiToNormal")
        mBottomContainer.gone()
    }

    //准备中
    override fun changeUiToPreparingShow() {
        super.changeUiToPreparingShow()
        logD(javaClass.simpleName, "changeUiToPreparingShow")
        mBottomContainer.gone()
    }

    //播放中
    override fun changeUiToPlayingShow() {
        super.changeUiToPlayingShow()
        logD(javaClass.simpleName, "changeUiToPlayingShow")
        mBottomContainer.gone()
        start?.gone()
    }

    //开始缓冲
    override fun changeUiToPlayingBufferingShow() {
        super.changeUiToPlayingBufferingShow()
        logD(javaClass.simpleName, "changeUiToPlayingBufferingShow")
    }

    //暂停
    override fun changeUiToPauseShow() {
        super.changeUiToPauseShow()
        logD(javaClass.simpleName, "changeUiToPauseShow")
        mBottomContainer.gone()
    }

    //自动播放结束
    override fun changeUiToCompleteShow() {
        super.changeUiToCompleteShow()
        logD(javaClass.simpleName, "changeUiToCompleteShow")
    }

    //错误状态
    override fun changeUiToError() {
        super.changeUiToError()
        logD(javaClass.simpleName, "changeUiToError")
    }
}
class AutoPlayScrollListener(private val itemPlayId: Int, private val rangeTop: Int, private val rangeBottom: Int) : RecyclerView.OnScrollListener() {

    private var isNeedShowWifiDialog = true

    private var firstVisible = 0
    private var lastVisible = 0
    private var visibleCount = 0
    private var runnable: PlayRunnable? = null
    private val playHandler = Handler()

    override fun onScrollStateChanged(recyclerView: RecyclerView, scrollState: Int) {
        when (scrollState) {
            RecyclerView.SCROLL_STATE_IDLE -> playVideo(recyclerView)
        }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val firstVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        val lastVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

        if (firstVisible == firstVisibleItem) return
        firstVisible = firstVisibleItem
        lastVisible = lastVisibleItem
        visibleCount = lastVisibleItem - firstVisibleItem
    }

    private fun playVideo(recyclerView: RecyclerView?) {
        recyclerView?.run {
            val layoutManager = recyclerView.layoutManager
            var gsyBaseVideoPlayer: GSYBaseVideoPlayer? = null
            var needPlay = false
            for (i in 0 until visibleCount) {
                if (layoutManager!!.getChildAt(i) != null && layoutManager.getChildAt(i)!!.findViewById<View?>(itemPlayId) != null) {
                    val player = layoutManager.getChildAt(i)!!.findViewById<View>(itemPlayId) as GSYBaseVideoPlayer
                    val rect = Rect()
                    player.getLocalVisibleRect(rect)
                    val height = player.height
                    //说明第一个完全可视
                    if (rect.top == 0 && rect.bottom == height) {
                        gsyBaseVideoPlayer = player
                        if (player.currentPlayer.currentState == GSYBaseVideoPlayer.CURRENT_STATE_NORMAL || player.currentPlayer.currentState == GSYBaseVideoPlayer.CURRENT_STATE_ERROR) {
                            needPlay = true
                        }
                        break
                    }
                }
            }
            if (gsyBaseVideoPlayer != null && needPlay) {
                runnable?.let {
                    val tmpPlayer = it.gsyBaseVideoPlayer
                    playHandler.removeCallbacks(it)
                    runnable = null
                    if (tmpPlayer === gsyBaseVideoPlayer) return
                }
                runnable = PlayRunnable(gsyBaseVideoPlayer)
                //降低频率
                playHandler.postDelayed(runnable!!, 400)
            }
        }
    }

    private inner class PlayRunnable(var gsyBaseVideoPlayer: GSYBaseVideoPlayer?) : Runnable {
        override fun run() {
            var inPosition = false
            //如果未播放，需要播放
            if (gsyBaseVideoPlayer != null) {
                val screenPosition = IntArray(2)
                gsyBaseVideoPlayer!!.getLocationOnScreen(screenPosition)
                val halfHeight = gsyBaseVideoPlayer!!.height / 2
                val rangePosition = screenPosition[1] + halfHeight
                //中心点在播放区域内
                if (rangePosition >= rangeTop && rangePosition <= rangeBottom) {
                    inPosition = true
                }
                if (inPosition) {
                    startPlayLogic(gsyBaseVideoPlayer!!, gsyBaseVideoPlayer!!.context)
                }
            }
        }
    }

    private fun startPlayLogic(gsyBaseVideoPlayer: GSYBaseVideoPlayer, context: Context) {
        if (!CommonUtil.isWifiConnected(context) && isNeedShowWifiDialog) {
            showWifiDialog(gsyBaseVideoPlayer, context)
            return
        }
        gsyBaseVideoPlayer.startPlayLogic()
    }

    private fun showWifiDialog(gsyBaseVideoPlayer: GSYBaseVideoPlayer, context: Context) {
        if (!NetworkUtils.isAvailable(context)) {
            Toast.makeText(context, context.resources.getString(com.shuyu.gsyvideoplayer.R.string.no_net), Toast.LENGTH_LONG).show()
            return
        }
        AlertDialog.Builder(context).apply {
            setMessage(context.resources.getString(com.shuyu.gsyvideoplayer.R.string.tips_not_wifi))
            setPositiveButton(context.resources.getString(com.shuyu.gsyvideoplayer.R.string.tips_not_wifi_confirm)) { dialog, which ->
                dialog.dismiss()
                gsyBaseVideoPlayer.startPlayLogic()
                isNeedShowWifiDialog = false
            }
            setPositiveButton(context.resources.getString(com.shuyu.gsyvideoplayer.R.string.tips_not_wifi_confirm)) { dialog, which ->
                dialog.dismiss()
                gsyBaseVideoPlayer.startPlayLogic()
                isNeedShowWifiDialog = false
            }
            setNegativeButton(context.resources.getString(com.shuyu.gsyvideoplayer.R.string.tips_not_wifi_cancel)) { dialog, which ->
                dialog.dismiss()
                isNeedShowWifiDialog = true
            }
            create()
        }.show()
    }

    companion object {

        /**
         * 指定自动播放，在屏幕上的区域范围，上
         */
        val PLAY_RANGE_TOP = screenHeight / 2 - dp2px(180f)

        /**
         * 指定自动播放，在屏幕上的区域范围，下
         */
        val PLAY_RANGE_BOTTOM = screenHeight / 2 + dp2px(180f)
    }

}