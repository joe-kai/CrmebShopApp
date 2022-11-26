package com.joekay.module_base.base

import android.annotation.SuppressLint
import android.content.Intent
import androidx.viewbinding.ViewBinding
import com.joekay.base.activity.BaseBindingAct
import com.joekay.base.ktx.showToast
import com.joekay.network.event.LoadingEvent
import com.joekay.network.event.State
import com.joekay.network.event.StateEvent
import com.joekay.network.event.ToastEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author:  JoeKai
 * @date:  2022/11/20
 * @explain：
 */
abstract class BaseActivity<VB : ViewBinding> : BaseBindingAct<VB>() {
    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun loadingEvent(event: LoadingEvent) {
        if (event.isLoading) {
            "xiangshi".showToast()
        } else {
            "yinchang".showToast()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun toastEvent(event: ToastEvent) {
        event.msg.showToast()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun stateEvent(event: StateEvent) {
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
        setIntent(intent)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

}