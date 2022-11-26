package com.joekay.base.fragment

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.joekay.base.action.BundleAction
import com.joekay.base.action.HandlerAction
import com.joekay.base.action.KeyboardAction

/**
 * @author:  JoeKai
 * @date:  2022/11/18
 * @explain：
 */
open class BaseFrag : Fragment(), HandlerAction, BundleAction, KeyboardAction {

    /** Activity 对象 */
    private var activity: ComponentActivity? = null




    @Suppress("UNCHECKED_CAST")
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // 获得全局的 Activity
        activity = requireActivity()
    }



    /**
     * Fragment 可见回调
     *
     * @param first                 是否首次调用
     */
    protected open fun onFragmentResume(first: Boolean) {}

    /**
     * Activity 可见回调
     */
    protected open fun onActivityResume() {}

    override fun onDestroy() {
        super.onDestroy()
        removeCallbacks()
    }

    override fun onDetach() {
        super.onDetach()
        activity = null
    }

    /**
     * 获取绑定的 Activity，防止出现 getActivity 为空
     */
    open fun getAttachActivity(): Activity? {
        return activity
    }


    /**
     * 获取 Application 对象
     */
    open fun getApplication(): Application? {
        activity?.let { return it.application }
        return null
    }



    override fun initBundle(): Bundle? {
        return arguments
    }

    /**
     * 销毁当前 Fragment 所在的 Activity
     */
    open fun finish() {
        this.activity?.let {
            if (it.isFinishing || it.isDestroyed) {
                return
            }
            it.finish()
        }
    }
    /**
     * Fragment 按键事件派发
     */
    open fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        val fragments: MutableList<Fragment?> = childFragmentManager.fragments
        for (fragment: Fragment? in fragments) {
            // 这个子 Fragment 必须是 BaseFragment 的子类，并且处于可见状态
            if (fragment !is BaseFrag || fragment.getLifecycle().currentState != Lifecycle.State.RESUMED) {
                continue
            }
            // 将按键事件派发给子 Fragment 进行处理
            if (fragment.dispatchKeyEvent(event)) {
                // 如果子 Fragment 拦截了这个事件，那么就不交给父 Fragment 处理
                return true
            }
        }
        return when (event?.action) {
            KeyEvent.ACTION_DOWN -> onKeyDown(event.keyCode, event)
            KeyEvent.ACTION_UP -> onKeyUp(event.keyCode, event)
            else -> false
        }
    }

    /**
     * 按键按下事件回调
     */
    open fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // 默认不拦截按键事件
        return false
    }

    /**
     * 按键抬起事件回调
     */
    open fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        // 默认不拦截按键事件
        return false
    }
    override fun getContext(): Context? {
        return activity
    }
}