package com.joekay.module_account.ui.login

import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.joekay.base.ext.showToast
import com.joekay.module_account.databinding.ActivityLoginBinding
import com.joekay.module_base.base.BaseActivity
import com.joekay.module_base.login.LoginInterceptCoroutinesManager
import com.joekay.module_base.other.TOKEN_KEY
import com.joekay.module_base.utils.MMKVUtils
import com.joekay.network.liveData.observeLoading
import com.joekay.resource.RouterPath
import com.therouter.router.Route
import dagger.hilt.android.AndroidEntryPoint

@Route(path = RouterPath.act_login)
@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {


    private val viewModel by viewModels<LoginViewModel>()


    override fun initObserve() {
        viewModel.loginModel.observeLoading(this) {
            onSuccess {
                MMKVUtils.put(TOKEN_KEY, it.token)
                LoginInterceptCoroutinesManager.get().loginFinished()
                finish()
            }
            onFailure {
                it.errorMsg!!.showToast()
            }
        }
    }

    override fun initBinding() {

        mBinding.btnLogin.setOnClickListener {
            viewModel.loginBody.account = mBinding.etPhone.text.toString()
            viewModel.loginBody.password = mBinding.etPassword.text.toString()
            viewModel.login()

        }
    }
}