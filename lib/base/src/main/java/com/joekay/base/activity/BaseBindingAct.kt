package com.joekay.base.activity

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.joekay.base.IBaseView
import com.joekay.base.action.BundleAction
import com.joekay.base.action.HandlerAction
import com.joekay.base.ktx.setTransparentStyle
import com.joekay.base.utils.BindingReflex

/**
 * @author:  JoeKai
 * @date:  2022/11/18
 * @explain：
 */
abstract class BaseBindingAct<VB : ViewBinding> : BaseAct(), IBaseView {
    private val _binding: VB by lazy(mode = LazyThreadSafetyMode.NONE) {
        BindingReflex.reflexViewBinding(javaClass, layoutInflater)
    }
    protected val mBinding: VB
        get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(_binding.root)
        window.setTransparentStyle()
        initSoftKeyboard()
        initObserve()
        initBinding()
        setDarkStatusBar()
    }

}