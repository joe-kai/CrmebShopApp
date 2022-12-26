package com.joekay.module_account.api

import com.joekay.module_account.api.body.LoginBody
import com.joekay.network.repository.BaseRepository
import javax.inject.Inject

/**
 * @author:  JoeKai
 * @date:  2022/12/26
 * @explain：
 */
class AccountRepo @Inject constructor() : BaseRepository<AccountApi>(AccountApi::class.java) {
    suspend fun login(map: LoginBody) = fire {
        api.login(map)
    }
}