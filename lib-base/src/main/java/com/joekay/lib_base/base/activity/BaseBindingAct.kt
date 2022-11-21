package com.joekay.lib_base.base.activity

import android.app.Activity
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.joekay.lib_base.base.ActivityManage
import com.joekay.lib_base.base.IBaseView
import com.joekay.lib_base.base.action.BundleAction
import com.joekay.lib_base.ktx.setTransparentStyle
import com.joekay.lib_base.utils.BindingReflex
import org.greenrobot.eventbus.EventBus
import java.lang.ref.WeakReference

/**
 * @author:  JoeKai
 * @date:  2022/11/18
 * @explain：
 */
abstract class BaseBindingAct<VB : ViewBinding> : BaseAct(), IBaseView, BundleAction {
    private val _binding: VB by lazy(mode = LazyThreadSafetyMode.NONE) {
        BindingReflex.reflexViewBinding(javaClass, layoutInflater)
    }
    protected val mBinding: VB
        get() = _binding

    /** 当前Activity的弱引用，防止内存泄露  */
    private var activityWR: WeakReference<Activity>? = null
    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityWR = WeakReference(this)
        ActivityManage.pushTask(activityWR)
        //EventBus.getDefault().register(this)
        setContentView(_binding.root)
        window.setTransparentStyle()
        initObserve()
        initBinding()
    }

    override fun initBundle(): Bundle? {
        return intent.extras
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityManage.removeTask(activityWR)
    }
}