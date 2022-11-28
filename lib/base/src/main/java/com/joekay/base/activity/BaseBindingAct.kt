package com.joekay.base.activity

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.joekay.base.IBaseView
import com.joekay.base.ext.setTransparentStyle
import com.joekay.base.utils.BindingReflex

/**
 * @author:  JoeKai
 * @date:  2022/11/18
 * @explain：
 */
abstract class BaseBindingAct<VB : ViewBinding> : BaseAct(), IBaseView {
    private val _binding: VB? by lazy(mode = LazyThreadSafetyMode.NONE) {
        BindingReflex.reflexViewBinding(javaClass, layoutInflater)
    }
    protected open val mBinding: VB
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        window.setTransparentStyle()
        initSoftKeyboard()
        setDarkStatusBar()
        initObserve()
        initBinding()
    }
}