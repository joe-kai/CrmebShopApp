package com.joekay.base.multiStateView.state

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.joekay.base.multiStateView.MultiStateView

/**
 * @author:  JoeKai
 * @date:  2022/11/28
 * @explain：自定义State基类，所有State都要实现
 */
abstract class MultiState {
    /**
     * 创建stateView
     */
    abstract fun onCreateMultiStateView(
        context: Context,
        inflater: LayoutInflater,
        container: MultiStateView
    ): View

    /**
     * stateView创建完成
     */
    abstract fun onMultiStateViewCreate(view: View)
}