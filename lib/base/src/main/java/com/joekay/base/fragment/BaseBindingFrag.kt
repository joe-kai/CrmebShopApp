package com.joekay.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import com.joekay.base.IBaseView
import com.joekay.base.ktx.setTransparentStyle
import com.joekay.base.utils.BindingReflex

/**
 * @author:  JoeKai
 * @date:  2022/11/18
 * @explain：
 */
abstract class BaseBindingFrag<VB : ViewBinding> : BaseFrag(), IBaseView {
    private val _binding: VB by lazy(mode = LazyThreadSafetyMode.NONE) {
        BindingReflex.reflexViewBinding(javaClass, layoutInflater)
    }

    protected val mBinding
        get() = checkNotNull(_binding) {
            "初始化binding失败或者binding未初始化"
        }

    /** 当前是否加载过 */
    private var loading: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        getAttachActivity()?.window?.setTransparentStyle()
        loading = false
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()
        initBinding()
    }

    override fun onResume() {
        super.onResume()
        if (!loading) {
            loading = true
            initObserve()
            onFragmentResume(true)
            return
        }

        if (this.activity?.lifecycle?.currentState == Lifecycle.State.STARTED) {
            onActivityResume()
        } else {
            onFragmentResume(false)
        }
    }

    /**
     * 这个 Fragment 是否已经加载过了
     */
    open fun isLoading(): Boolean {
        return loading
    }

    override fun onDestroy() {
        super.onDestroy()
        loading = false

    }
}