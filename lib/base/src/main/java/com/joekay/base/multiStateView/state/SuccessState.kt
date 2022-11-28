package com.joekay.base.multiStateView.state

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.joekay.base.multiStateView.MultiStateView

/**
 * @author:  JoeKai
 * @date:  2022/11/28
 * @explain：成功State
 */
class SuccessState : MultiState() {
    override fun onCreateMultiStateView(
        context: Context,
        inflater: LayoutInflater,
        container: MultiStateView
    ): View = View(context)

    override fun onMultiStateViewCreate(view: View) {
    }
}