package com.joekay.module_home.model

/**
 * @author:  JoeKai
 * @date:  2022/12/16
 * @explain：
 */
data class HomeSecKillModel(
    val secKillResponse: SecKillResponse,
    val productList: MutableList<ProductModel>
)

data class SecKillResponse(
    val id: Int,
    val time: String,
    val timeSwap: String,
)