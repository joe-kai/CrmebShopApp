package com.joekay.network.download

import android.util.Log
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * @author:  JoeKai
 * @date:  2022/11/26
 * @explain：
 */
abstract class BaseCallback : Callback {

    /** 请求任务对象  */
    private lateinit var mCall: CallProxy

    /** 当前重试次数  */
    private var mRetryCount = 0


    open fun setCall(call: CallProxy): BaseCallback {
        mCall = call
        return this
    }

    open fun start() {
        mCall.enqueue(this)
        onStart(mCall)
    }

    protected open fun getCall(): Call {
        return mCall
    }

    override fun onResponse(call: Call, response: Response) {
        try {
            // 收到响应
            onResponse(response)
        } catch (e: Exception) {
            // 回调失败
            onFailure(e)
        } finally {
            // 关闭响应
            response.close()
        }
    }

    override fun onFailure(call: Call, e: IOException) {
        // 服务器请求超时重试

        // 服务器请求超时重试
        if (e is SocketTimeoutException && mRetryCount < 1) {
            // 设置延迟 N 秒后重试该请求
            FileUtils.postDelayed({
                mRetryCount++
                val newCall = call.clone()
                mCall.setCall(newCall)
                newCall.enqueue(this@BaseCallback)
                Log.d(
                    "---BaseCallback",
                    "请求超时，正在延迟重试，重试次数：" + mRetryCount + "/" + 1
                )
            }, 2000)
            return
        }
    }


    /**
     * 请求开始
     */
    protected abstract fun onStart(call: Call)

    /**
     * 请求成功
     */
    @Throws(Exception::class)
    protected abstract fun onResponse(response: Response)

    /**
     * 请求失败
     */
    protected abstract fun onFailure(e: Exception)
}