package com.joekay.base.activity

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.joekay.base.IBaseView
import com.joekay.base.ext.setTransparentStyle
import com.joekay.base.utils.BindingReflex
import com.joekay.base.widgets.LoadingDialog

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
    private lateinit var mLoadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        mLoadingDialog = LoadingDialog(this)
        window.setTransparentStyle()
        initSoftKeyboard()
        setDarkStatusBar()
        initObserve()
        initBinding()
    }

    open fun setDialogMessage(msg: String) {
        mLoadingDialog.setMessage(msg)
    }

    open fun showDialog() {
        mLoadingDialog.showDialog()
    }

    open fun dismissDialog() {
        mLoadingDialog.dismissDialog()
    }


}