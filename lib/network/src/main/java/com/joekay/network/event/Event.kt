package com.joekay.network.event

/**
 * @author:  JoeKai
 * @date:  2022/11/21
 * @explain：
 */
class LoadingEvent(var isLoading: Boolean)

class StateEvent(var state: State)

class ToastEvent(var msg: String)

enum class State {
    STATE_LOADING,
    STATE_SUCCESS,
    STATE_EMPTY,
    STATE_ERROR,
}