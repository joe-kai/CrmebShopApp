package com.joekay.base.multiStateView.state

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import com.joekay.base.R
import com.joekay.base.databinding.LayoutMultiStateBinding
import com.joekay.base.ext.gone
import com.joekay.base.multiStateView.MultiStateConfig
import com.joekay.base.multiStateView.MultiStateView

/**
 * @author:  JoeKai
 * @date:  2022/10/14
 * @content：
 */
class LoadingState : MultiState() {
    private lateinit var binding: LayoutMultiStateBinding
    override fun onCreateMultiStateView(
        context: Context, inflater: LayoutInflater, container: MultiStateView
    ): View {
        return inflater.inflate(R.layout.layout_multi_state, container, false)
    }

    override fun onMultiStateViewCreate(view: View) {
        binding = LayoutMultiStateBinding.bind(view)
        val animation: Animation = RotateAnimation(
            0f,
            360f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        animation.duration = 2000
        animation.repeatCount = 30
        animation.fillAfter = true
        binding.imgIcon.startAnimation(animation)
        setLoadingMsg(MultiStateConfig().loadingMsg)
        hideRetryTip()
    }

    private fun hideRetryTip() {
        binding.txvRetry.gone()
    }

    fun setLoadingMsg(loadingMsg: String) {
        binding.txvMsg.text = loadingMsg
    }
}