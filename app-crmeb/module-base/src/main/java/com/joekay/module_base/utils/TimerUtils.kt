package com.joekay.module_base.utils



import android.os.CountDownTimer
import android.text.TextUtils
import android.util.Log
import com.joekay.module_base.BuildConfig

/**
 * @author:  JoeKai
 * @date:  2022/12/16
 * @explain：
 */
object TimerUtils {
    private val TAG = "------TimerUtilsLog"
    private var countDownTimer: CountDownTimer? = null
    private var timerFinishedListener: (() -> Unit)? = null
    private var inactiveTimeCached: String? = ""
    private var isRunning = false

    fun resetTimer() {
        startTimer(true, inactiveTimeCached)
    }

    fun startTimer(inactiveTime: String?, listener: (() -> Unit)? = null) {
        startTimer(false, inactiveTime, listener)
    }

    fun isRunning():Boolean{
        return isRunning
    }

    private fun startTimer(reset: Boolean, inactiveTime: String?, listener: (() -> Unit)? = null) {
        if (listener != null) {
            timerFinishedListener = listener
        }
        if (!TextUtils.isEmpty(inactiveTime)) {
            inactiveTimeCached = inactiveTime
        }
        if (countDownTimer == null) {
            isRunning = false
            val serverTime = getServerTime(inactiveTimeCached)
            if (serverTime == null) {
                Log.d(TAG, "serverTime '$inactiveTimeCached' is not a Number ")
                return
            }
            //time unit:minute
            val timerDuration = if (BuildConfig.DEBUG) {
                serverTime / 2
            } else {
                serverTime
            }
            Log.d(
                TAG,
                "expiredTimer serverTime=$inactiveTimeCached min,afterCalc=$serverTime,realTime=$timerDuration,isDebug :${BuildConfig.DEBUG}"
            )
            countDownTimer =
                object : CountDownTimer(timerDuration * 60 * 1000L, 1000L) {
                    override fun onFinish() {
                        Log.d(TAG, "countDownTimer onFinish.")
                        isRunning = false
                        timerFinishedListener?.invoke()
                        this.cancel()
                    }

                    override fun onTick(millisUntilFinished: Long) {
                        Log.i(TAG, "leftTime====>$millisUntilFinished")
                    }
                }
        }
        Log.d(TAG, "reset=$reset")
        if (reset) {
            countDownTimer?.cancel()
            countDownTimer?.start()
            isRunning = true
            return
        }
        if (isRunning) {
            return
        }
        isRunning = true
        countDownTimer?.start()
    }

    private fun getServerTime(s: String?): Long? {
        return if (!s.isNullOrEmpty()) {
            return try {
                java.lang.Long.valueOf(s)
            } catch (ex: Exception) {
                null
            }
        } else {
            null
        }
    }

    fun destroy() {
        isRunning = false
        inactiveTimeCached = ""
        timerFinishedListener = null
        countDownTimer?.cancel()
        countDownTimer = null
    }
}