package com.joekay.base.multiStateView

import com.joekay.base.multiStateView.state.MultiState


fun interface OnNotifyListener<T : MultiState> {
    fun onNotify(multiState: T)
}