package com.joekay.module_account.ui.login

import com.joekay.base.vm.BaseViewModel
import com.joekay.module_account.api.AccountRepo
import com.joekay.module_account.api.body.LoginBody
import com.joekay.module_account.model.LoginModel
import com.joekay.network.liveData.BaseLiveData
import com.joekay.network.liveData.request
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author:  JoeKai
 * @date:  2022/12/26
 * @explain：
 */
@HiltViewModel
class LoginViewModel @Inject constructor(private var repo: AccountRepo) : BaseViewModel() {
    var loginModel = BaseLiveData<LoginModel>()

    var loginBody = LoginBody()

    fun login() {
        loginModel.request(this) {
            repo.login(loginBody)
        }
    }
}