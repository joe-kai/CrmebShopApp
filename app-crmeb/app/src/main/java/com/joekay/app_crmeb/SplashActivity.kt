package com.joekay.app_crmeb

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import com.joekay.app_crmeb.databinding.ActivitySplashBinding
import com.joekay.module_base.base.BaseActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    override fun initObserve() {
    }

    override fun initBinding() {
        // 设置动画监听
        mBinding.lavSplashLottie.addAnimatorListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                mBinding.lavSplashLottie.removeAnimatorListener(this)
                startActivity(MainActivity::class.java)
                finish()
            }
        })
    }

}