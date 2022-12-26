package com.joekay.module_account.api.body

/**
 * @author:  JoeKai
 * @date:  2022/12/26
 * @explain：
 */
data class LoginBody(
    var account: String = "18700800435",
    var password: String = "123456",
    var spread_spid: Int = 0,
)
