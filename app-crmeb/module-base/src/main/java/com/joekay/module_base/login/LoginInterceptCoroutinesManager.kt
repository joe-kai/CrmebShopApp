package com.joekay.module_base.login

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.joekay.module_base.utils.RouterUtils
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

/**
 * @author:  JoeKai
 * @date:  2022/12/26
 * @explain：使用携程处理登录拦截
 */
class LoginInterceptCoroutinesManager constructor() : DefaultLifecycleObserver,
    CoroutineScope by MainScope() {

    companion object {
        private var instance: LoginInterceptCoroutinesManager? = null
            get() {
                if (field == null) {
                    field = LoginInterceptCoroutinesManager()
                }
                return field
            }

        fun get(): LoginInterceptCoroutinesManager {
            return instance!!
        }

    }

    private val channel = Channel<Boolean>()
    fun checkLogin(loginAction: () -> Unit, nextAction: () -> Unit) {
        launch {
            if (LoginManager.isLogin()) {
                nextAction()
                return@launch
            }
            loginAction()
            val isLogin = channel.receive()
            if (isLogin) {
                nextAction()
            }
        }
    }

    fun checkLogin(nextAction: () -> Unit) {
        launch {
            if (LoginManager.isLogin()) {
                nextAction()
                return@launch
            }
            RouterUtils.goToLogin()
            val isLogin = channel.receive()
            if (isLogin) {
                nextAction()
            }
        }
    }


    fun loginFinished() {
        launch {
            async {
                channel.send(LoginManager.isLogin())
            }
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        cancel()
    }

}