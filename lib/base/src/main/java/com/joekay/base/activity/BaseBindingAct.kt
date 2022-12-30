package com.joekay.base.activity

import android.app.Activity
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.joekay.base.ActivityCollector
import com.joekay.base.IBaseView
import com.joekay.base.ext.mLogD
import com.joekay.base.ext.setTransparentStyle
import com.joekay.base.utils.BindingReflex
import com.joekay.base.widgets.LoadingDialog
import java.lang.ref.WeakReference

/**
 * @author:  JoeKai
 * @date:  2022/11/18
 * @explain：
 */
abstract class BaseBindingAct<VB : ViewBinding> : BaseAct(), IBaseView {
    /**
     * 日志输出标志
     */
    protected val TAG: String = "-${this.javaClass.simpleName}"

    private val _binding: VB? by lazy(mode = LazyThreadSafetyMode.NONE) {
        BindingReflex.reflexViewBinding(javaClass, layoutInflater)
    }
    protected open val mBinding: VB
        get() = checkNotNull(_binding) {
            "初始化binding失败或者binding未初始化"
        }
    private lateinit var mLoadingDialog: LoadingDialog

    /**
     * 当前Activity的实例。
     */
    protected var mActivity: Activity? = null

    /** 当前Activity的弱引用，防止内存泄露  */
    private var activityWR: WeakReference<Activity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //_binding = BindingReflex.reflexViewBinding(javaClass, layoutInflater)
        activityWR = WeakReference(this)
        mActivity = activityWR?.get()
        ActivityCollector.pushTask(activityWR)
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


    override fun onDestroy() {
        super.onDestroy()
        mLogD(TAG, "onDestroy()")
        mActivity = null
        //_binding = null
        ActivityCollector.removeTask(activityWR)
    }

}