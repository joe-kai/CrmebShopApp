package com.joekay.app_crmeb

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.ComponentCallbacks
import android.content.ComponentCallbacks2
import android.content.res.Configuration
import com.joekay.app_crmeb.databinding.ActivitySplashBinding
import com.joekay.base.ext.*
import com.joekay.base.utils.GlobalUtil
import com.joekay.module_base.base.BaseActivity
import com.joekay.resource.RouterPath
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.therouter.TheRouter
import com.therouter.router.Route
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@Route(path = RouterPath.act_splash)
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    override fun initObserve() {
    }

    override fun initBinding() {
        mBinding.tvAppName.text = GlobalUtil.appName
        mLogE("TAGE", "mLogTagE")
        mLogD("TAGD", "mLogD")
        mLogV("TAGV", "mLogTagV")
        mLogV("initBinding")

        // 设置动画监听
        mBinding.lavSplashLottie.addAnimatorListener(
            object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    mBinding.lavSplashLottie.removeAnimatorListener(this)
                    TheRouter.build(RouterPath.act_main).navigation()
                    finish()
                }
            })

    }


}