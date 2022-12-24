package com.joekay.module_base.base

import TitleBarAction
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.ImmersionBar
import com.hjq.bar.TitleBar
import com.joekay.base.action.ResourcesAction
import com.joekay.base.activity.BaseBindingAct
import com.joekay.base.ext.ShowDialogShare
import com.joekay.base.ext.logD
import com.joekay.base.ext.showToast
import com.joekay.base.utils.ShareUtil
import com.joekay.module_base.event.MessageEvent
import com.joekay.network.event.LoadingEvent
import com.joekay.network.event.State
import com.joekay.network.event.StateEvent
import com.joekay.network.event.ToastEvent
import com.therouter.TheRouter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * @author:  JoeKai
 * @date:  2022/11/20
 * @explain：
 */
@Suppress("DEPRECATION")
abstract class BaseActivity<VB : ViewBinding> : BaseBindingAct<VB>(), TitleBarAction,
    ResourcesAction {

    /** 标题栏对象 */
    private var titleBar: TitleBar? = null

    /**
     * 判断当前Activity是否在前台。
     */
    protected var isActive: Boolean = false

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
        logD(TAG, "BaseActivity-->onStart()")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val titleBar = getTitleBar()
        ImmersionBar.setTitleBar(this, titleBar)
        titleBar?.setOnTitleBarListener(this)
        TheRouter.inject(this)

        val params = window.attributes
        params.systemUiVisibility =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE
        window.attributes = params
        logD(TAG, "BaseActivity-->onCreate()")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        logD(TAG, "BaseActivity-->onSaveInstanceState()")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        logD(TAG, "BaseActivity-->onRestoreInstanceState()")
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onMessageEvent(messageEvent: MessageEvent) {
        logD(TAG, "BaseFragment-->onMessageEvent()")
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    private fun loadingEvent(event: LoadingEvent) {
        if (event.isLoading) {
            showDialog()
        } else {
            dismissDialog()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    private fun toastEvent(event: ToastEvent) {
        event.msg.showToast()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    private fun stateEvent(event: StateEvent) {
        when (event.state) {
            State.STATE_LOADING -> {
                "STATE_LOADING".showToast()
            }
            State.STATE_SUCCESS -> {
                "STATE_SUCCESS".showToast()
            }
            State.STATE_EMPTY -> {
                "STATE_EMPTY".showToast()
            }
            State.STATE_ERROR -> {
                "STATE_ERROR".showToast()
            }
        }

    }

    /**
     * 如果当前的 Activity（singleTop 启动模式） 被复用时会回调
     */
    @SuppressLint("MissingSuperCall")
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        logD(TAG, "BaseActivity-->onNewIntent()")
    }

    override fun onRestart() {
        super.onRestart()
        logD(TAG, "BaseActivity-->onRestart()")
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
        logD(TAG, "BaseActivity-->onStop()")
    }

    override fun onResume() {
        super.onResume()
        logD(TAG, "BaseActivity-->onResume()")
        isActive = true
    }

    override fun onPause() {
        super.onPause()
        logD(TAG, "BaseActivity-->onPause()")
        isActive = false
    }


    override fun getTitleBar(): TitleBar? {
        if (titleBar == null) {
            titleBar = obtainTitleBar(getContentView())
        }
        return titleBar
    }

    override fun setTitle(id: Int) {
        title = getString(id)
    }

    override fun setTitle(title: CharSequence?) {
        super<BaseBindingAct>.setTitle(title)
        getTitleBar()?.title = title
    }

    override fun onLeftClick(titleBar: TitleBar?) {
        super.onLeftClick(titleBar)
        finish()
    }
    /**
     * 调用系统原生分享
     *
     * @param shareContent 分享内容
     * @param shareType SHARE_MORE=0，SHARE_QQ=1，SHARE_WECHAT=2，SHARE_WEIBO=3，SHARE_QQZONE=4
     */
    protected fun share(shareContent: String, shareType: Int) {
        ShareUtil.share(this, shareContent, shareType)
    }

    /**
     * 弹出分享对话框
     *
     * @param shareContent 分享内容
     */
    protected fun showDialogShare(shareContent: String) {
        ShowDialogShare(this, shareContent)
    }
}