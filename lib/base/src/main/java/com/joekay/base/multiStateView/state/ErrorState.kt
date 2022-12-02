package com.joekay.base.multiStateView.state

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import com.joekay.base.R
import com.joekay.base.databinding.LayoutMultiStateBinding
import com.joekay.base.ext.*
import com.joekay.base.multiStateView.MultiStateConfig
import com.joekay.base.multiStateView.MultiStatePage
import com.joekay.base.multiStateView.MultiStateView

/**
 * @author:  JoeKai
 * @date:  2022/11/28
 * @explain：
 */
class ErrorState : MultiState() {
    private var retry: OnRetryClickListener? = null
    private lateinit var binding: LayoutMultiStateBinding

    override fun onCreateMultiStateView(
        context: Context,
        inflater: LayoutInflater,
        container: MultiStateView
    ): View {
        return inflater.inflate(R.layout.layout_multi_state, container, false)
    }

    override fun onMultiStateViewCreate(view: View) {
        binding = LayoutMultiStateBinding.bind(view)
        setErrorMsg(MultiStateConfig().errorMsg)
        setErrorIcon(MultiStateConfig().errorIcon)
        setRetryTip(MultiStateConfig().retryTip)
        binding.txvRetry.setOnClickListener { retry?.retry() }
    }

    fun setErrorMsg(errorMsg: String) {
        binding.txvRetry.isVisible(errorMsg.isNotEmpty())
        binding.txvMsg.text = errorMsg
    }

    fun hideErrorMsg() {
        binding.txvMsg.gone()
    }

    fun setErrorIcon(@DrawableRes errorIcon: Int) {
        binding.imgIcon.setImageResource(errorIcon)
    }

    fun hideErrorIcon() {
        binding.imgIcon.gone()
    }

    fun setRetryTip(retryTip: String) {
        binding.txvRetry.isInVisible(retryTip.isNotEmpty())
        binding.txvRetry.text = retryTip
    }
    fun hideRetryTip() {
        binding.txvRetry.invisible()
    }

    fun retry(retry: OnRetryClickListener) {
        this.retry = retry
    }

    fun interface OnRetryClickListener {
        fun retry()
    }
}