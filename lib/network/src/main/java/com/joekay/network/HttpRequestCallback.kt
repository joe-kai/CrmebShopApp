package com.joekay.network

import com.joekay.network.exception.ErrorException

/**
 * @Author JoeKay
 * @date [2022/9/30]
 * @function 网络请求回调
 */
typealias OnSuccessCallback<T> = (data: T) -> Unit
typealias OnFailureCallback = (e: ErrorException) -> Unit
typealias OnUnitCallback = () -> Unit

class HttpRequestCallback<T> {
    var startCallback: OnUnitCallback? = null
    var successCallback: OnSuccessCallback<T>? = null
    var emptyCallback: OnUnitCallback? = null
    var failureCallback: OnFailureCallback? = null
    var finishCallback: OnUnitCallback? = null

    fun onStart(block: OnUnitCallback) {
        startCallback = block
    }

    fun onSuccess(block: OnSuccessCallback<T>) {
        successCallback = block
    }

    fun onEmpty(block: OnUnitCallback) {
        emptyCallback = block
    }

    fun onFailure(block: OnFailureCallback) {
        failureCallback = block
    }

    fun onFinish(block: OnUnitCallback) {
        finishCallback = block
    }
}