package com.joekay.base.multiStateView

import androidx.annotation.DrawableRes
import com.joekay.base.R

/**
 * @author:  JoeKai
 * @date:  2022/11/28
 * @explain：
 */
data class MultiStateConfig(
    val errorMsg: String = "哎呀,出错了",
    @DrawableRes
    var errorIcon: Int = R.drawable.ic_state_error,
    val emptyMsg: String = "空空如也",
    val retryTip: String = "点击重试",
    @DrawableRes
    val emptyIcon: Int = R.drawable.ic_state_empty,
    val loadingMsg: String = "loading...",

    val lottieResId: Int = R.raw.lottie_waiting,

    var alphaDuration: Long = 500,
) {
    class Builder {
        private var errorMsg: String = "哎呀,出错了"

        @DrawableRes
        private var errorIcon: Int = R.drawable.ic_state_error
        private var emptyMsg: String = "空空如也"
        private var retryTip: String = "点击重试"
        private var lottieResId: Int = R.raw.lottie_waiting

        @DrawableRes
        private var emptyIcon: Int = R.drawable.ic_state_empty
        private var loadingMsg: String = "loading..."
        private var alphaDuration: Long = 500


        fun setErrorMsg(msg: String) = apply {
            this.errorMsg = msg
        }

        fun setErrorIcon(@DrawableRes icon: Int) = apply {
            this.errorIcon = icon
        }

        fun setLottieRes(id: Int) = apply {
            this.lottieResId = id
        }

        fun setRetryTip(tip: String) = apply {
            this.retryTip = tip
        }


        fun setEmptyMsg(msg: String) = apply {
            this.emptyMsg = msg
        }

        fun setEmptyIcon(@DrawableRes icon: Int) = apply {
            this.emptyIcon = icon
        }

        fun setLoadingMsg(msg: String) = apply {
            this.loadingMsg = msg
            return this
        }

        fun setAlphaDuration(duration: Long) = apply {
            this.alphaDuration = duration
        }

        fun build() = MultiStateConfig(
            errorMsg = errorMsg,
            errorIcon = errorIcon,
            emptyMsg = emptyMsg,
            emptyIcon = emptyIcon,
            retryTip = retryTip,
            loadingMsg = loadingMsg,
            lottieResId = lottieResId,
            alphaDuration = alphaDuration,
        )
    }
}