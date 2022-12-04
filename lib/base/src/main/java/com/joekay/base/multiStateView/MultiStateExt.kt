package com.joekay.base.multiStateView

import android.app.Activity
import android.view.View
import com.joekay.base.multiStateView.state.EmptyState
import com.joekay.base.multiStateView.state.ErrorState
import com.joekay.base.multiStateView.state.LoadingState
import com.joekay.base.multiStateView.state.SuccessState

/**
 * @author:  JoeKai
 * @date:  2022/11/28
 * @explain：
 */

fun View.bindMultiState() = MultiStatePage.bindMultiState(this)

fun Activity.bindMultiState() = MultiStatePage.bindMultiState(this)

fun MultiStateView.showLoading(callBack: (LoadingState) -> Unit = {}) {
    show<LoadingState> {
        callBack.invoke(it)
    }
}

fun MultiStateView.showSuccess(callBack: (SuccessState) -> Unit = {}) {
    show<SuccessState> {
        callBack.invoke(it)
    }
}

fun MultiStateView.showError(callBack: (ErrorState) -> Unit = {}) {
    show<ErrorState> {
        callBack.invoke(it)
    }
}

fun MultiStateView.showEmpty(callBack: (EmptyState) -> Unit = {}) {
    show<EmptyState> {
        callBack.invoke(it)
    }
}