package com.joekay.network.download

import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import okio.Timeout


/**
 * @author:  JoeKai
 * @date:  2022/11/26
 * @explain：
 */
class CallProxy : Call {
    private var mCall: Call

    constructor(call: Call) {
        mCall = call
    }

    fun setCall(call: Call) {
        mCall = call
    }

    override fun cancel() {
        mCall.cancel()
    }

    override fun clone(): Call {
        return mCall.clone()
    }

    override fun enqueue(responseCallback: Callback) {
        return mCall.enqueue(responseCallback)

    }

    override fun execute(): Response {
        return mCall.execute()
    }

    override fun isCanceled(): Boolean {
        return mCall.isCanceled()
    }

    override fun isExecuted(): Boolean {
        return mCall.isExecuted()
    }

    override fun request(): Request {
        return mCall.request()
    }

    override fun timeout(): Timeout {
        return mCall.timeout()
    }
}