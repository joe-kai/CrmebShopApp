package com.joekay.base.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
abstract class BaseBindingFrag<VB : ViewBinding> : BaseFrag(), IBaseView, HandlerAction,
    BundleAction {
    private val _binding: VB by lazy(mode = LazyThreadSafetyMode.NONE) {
        BindingReflex.reflexViewBinding(javaClass, layoutInflater)
    }

    protected val mBinding
        get() = checkNotNull(_binding) {
            "初始化binding失败或者binding未初始化"
        }
    lateinit var mActivity: Activity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = requireActivity()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mActivity.window.setTransparentStyle()
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()
        initBinding()
    }

    override fun initBundle(): Bundle? {
        return arguments
    }


}