package com.joekay.module_base.login.interceptor

import androidx.annotation.CallSuper
import com.joekay.module_base.login.LoginManager
import com.joekay.module_base.utils.RouterUtils

/**
 * @author:  JoeKai
 * @date:  2022/12/26
 * @explain：使用拦截器模式实现登录拦截
 */
interface Interceptor {
    fun intercept(chain: LoginInterceptChain)
}

abstract class BaseLoginInterceptImpl : Interceptor {
    protected var mChain: LoginInterceptChain? = null

    @CallSuper
    override fun intercept(chain: LoginInterceptChain) {
        mChain = chain
    }

}

object LoginInterceptChain {
    private var index: Int = 0

    private val interceptors by lazy(LazyThreadSafetyMode.NONE) {
        ArrayList<Interceptor>(2)
    }

    //默认初始化Login的拦截器
    private val loginIntercept = LoginInterceptor()


    // 执行拦截器。
    fun process() {
        if (interceptors.isEmpty()) return
        when (index) {
            in interceptors.indices -> {
                val interceptor = interceptors[index]
                index++
                interceptor.intercept(this)
            }
            interceptors.size -> {
                clearAllInterceptors()
            }
        }
    }

    // 添加一个拦截器。
    fun addInterceptor(interceptor: Interceptor): LoginInterceptChain {
        //默认添加Login判断的拦截器
        if (!interceptors.contains(loginIntercept)) {
            interceptors.add(loginIntercept)
        }
        if (!interceptors.contains(interceptor)) {
            interceptors.add(interceptor)
        }

        return this
    }


    //放行登录判断拦截器
    fun loginFinished() {
        if (interceptors.contains(loginIntercept) && interceptors.size > 1) {
            loginIntercept.loginFinished()
        }
    }

    //清除全部的拦截器
    private fun clearAllInterceptors() {
        index = 0
        interceptors.clear()
    }
}

class LoginInterceptor : BaseLoginInterceptImpl() {
    override fun intercept(chain: LoginInterceptChain) {
        super.intercept(chain)

        if (LoginManager.isLogin()) {
            //如果已经登录 -> 放行, 转交给下一个拦截器
            chain.process()
        } else {
            //如果未登录 -> 去登录页面
            RouterUtils.goToLogin()
        }
    }

    fun loginFinished() {
        //如果登录完成，调用方法放行到下一个拦截器
        mChain?.process()
    }
}

class LoginNextIntercept(private val action: () -> Unit) : BaseLoginInterceptImpl() {
    override fun intercept(chain: LoginInterceptChain) {
        super.intercept(chain)
        if (LoginManager.isLogin()) {
            action()
        }
        chain.process()
    }

}
