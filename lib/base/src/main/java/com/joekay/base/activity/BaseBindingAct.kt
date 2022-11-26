package com.joekay.base.activity

import android.app.Activity
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.joekay.base.ActivityManage
import com.joekay.base.IBaseView
import com.joekay.base.action.BundleAction
import com.joekay.base.action.HandlerAction
import com.joekay.base.ktx.setTransparentStyle
import com.joekay.base.utils.BindingReflex
import java.lang.ref.WeakReference

/**
 * @author:  JoeKai
 * @date:  2022/11/18
 * @explain：
 */
abstract class BaseBindingAct<VB : ViewBinding> : BaseAct(), IBaseView, BundleAction,
    HandlerAction {
    private val _binding: VB by lazy(mode = LazyThreadSafetyMode.NONE) {
        BindingReflex.reflexViewBinding(javaClass, layoutInflater)
    }
    protected val mBinding: VB
        get() = _binding

    /** 当前Activity的弱引用，防止内存泄露  */
    private var activityWR: WeakReference<Activity>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityWR = WeakReference(this)
        ActivityManage.pushTask(activityWR)
        setContentView(_binding.root)
        window.setTransparentStyle()
        initObserve()
        initBinding()
    }

    override fun initBundle(): Bundle? {
        return intent.extras
    }




    override fun onDestroy() {
        super.onDestroy()
        ActivityManage.removeTask(activityWR)
    }
}