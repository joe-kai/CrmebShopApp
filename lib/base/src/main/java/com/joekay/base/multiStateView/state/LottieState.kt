package com.joekay.base.multiStateView.state

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.joekay.base.R
import com.joekay.base.databinding.LayoutLottieStateBinding
import com.joekay.base.ext.debug
import com.joekay.base.ext.showToast
import com.joekay.base.multiStateView.MultiStateConfig
import com.joekay.base.multiStateView.MultiStateView
import com.joekay.resource.RouterPath
import com.therouter.TheRouter

/**
 * @author:  JoeKai
 * @date:  2022/11/28
 * @explain：
 */
class LottieState : MultiState() {
    private lateinit var binding: LayoutLottieStateBinding

    val TAG = "LottieState:"
    override fun onCreateMultiStateView(
        context: Context,
        inflater: LayoutInflater,
        container: MultiStateView
    ): View {
        return inflater.inflate(R.layout.layout_lottie_state, container, false)

    }

    override fun onMultiStateViewCreate(view: View) {
        binding = LayoutLottieStateBinding.bind(view)
        binding.lottieView.repeatCount = 4
        binding.lottieView.addAnimatorListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                binding.lottieView.removeAnimatorListener(this)
            }
        })
        setLottieRes(MultiStateConfig().lottieResId)
    }

    fun setLottieRes(id: Int) {
        binding.lottieView.setAnimation(id)
    }
}