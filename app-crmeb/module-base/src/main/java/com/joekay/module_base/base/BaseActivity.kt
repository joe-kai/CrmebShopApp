package com.joekay.module_base.base

import TitleBarAction
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.viewbinding.ViewBinding
import com.joekay.base.ActivityCollector
import com.joekay.base.activity.BaseBindingAct
import com.joekay.base.ext.ShowDialogShare
import com.joekay.base.ext.logD
import com.joekay.base.ext.showToast
import com.joekay.base.utils.ShareUtil
import com.joekay.base.widgets.ActionBarView
import com.joekay.module_base.event.MessageEvent
import com.joekay.network.event.LoadingEvent
import com.joekay.network.event.State
import com.joekay.network.event.StateEvent
import com.joekay.network.event.ToastEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.ref.WeakReference

/**
 * @author:  JoeKai
 * @date:  2022/11/20
 * @explain：
 */
@Suppress("DEPRECATION")
abstract class BaseActivity<VB : ViewBinding> : BaseBindingAct<VB>(), TitleBarAction {
    /**
     * 日志输出标志
     */
    protected val TAG: String = "Log:${this.javaClass.simpleName}->"

    /** 标题栏对象 */
    private var titleBar: ActionBarView? = null

    /**
     * 判断当前Activity是否在前台。
     */
    protected var isActive: Boolean = false

    /**
     * 当前Activity的实例。
     */
    protected var mActivity: Activity? = null

    /** 当前Activity的弱引用，防止内存泄露  */
    private var activityWR: WeakReference<Activity>? = null

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
        logD(TAG, "BaseActivity-->onStart()")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = this
        activityWR = WeakReference(mActivity!!)
        ActivityCollector.pushTask(activityWR)
        val titleBar = getTitleBar()
        titleBar?.setOnTitleBarListener(this)
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
        } else {
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
        //MobclickAgent.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        logD(TAG, "BaseActivity-->onPause()")
        isActive = false
        //MobclickAgent.onPause(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        logD(TAG, "BaseActivity-->onDestroy()")

        mActivity = null
        ActivityCollector.removeTask(activityWR)
    }

    override fun getTitleBar(): ActionBarView? {
        if (titleBar == null) {
            titleBar = obtainTitleBar(getContentView())
        }
        return titleBar
    }

    override fun setTitle(id: Int) {
        getTitleBar()?.setTitle(id)
    }

    override fun setTitle(title: CharSequence) {
        super<BaseBindingAct>.setTitle(title)
        getTitleBar()?.setTitle(title)
    }

    override fun onLeftClick(view: View?) {
        onBackPressed()
    }

    override fun onRightClick(view: View?) {

    }

    override fun onTitleClick(view: View?) {
        "hahha".showToast()
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