package com.joekay.app_crmeb

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import com.joekay.app_crmeb.databinding.ActivitySplashBinding
import com.joekay.module_base.base.BaseActivity
import com.joekay.resource.RouterPath
import com.therouter.TheRouter
import com.therouter.router.Route

@SuppressLint("CustomSplashScreen")
@Route(path = RouterPath.act_splash)
class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    override fun initObserve() {
    }

    override fun initBinding() {
        // 设置动画监听
        mBinding.lavSplashLottie.addAnimatorListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                mBinding.lavSplashLottie.removeAnimatorListener(this)
                TheRouter.build(RouterPath.act_main).navigation()
                finish()
            }
        })
    }

}