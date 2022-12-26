package com.joekay.module_account.model

/**
 * @author:  JoeKai
 * @date:  2022/12/26
 * @explain：
 */
data class LoginModel(
    val key: Any,
    val nikeName: String,
    val phone: String,
    val token: String,
    val type: Any,
    val uid: Int
)
