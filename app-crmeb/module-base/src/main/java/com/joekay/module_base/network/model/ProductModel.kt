package com.joekay.module_base.network.model

/**
 * @author:  JoeKai
 * @date:  2022/12/16
 * @explain：
 */
data class ProductModel(
    val id: Int,
    val storeName: String,
    val info: String,
    val image: String,
    val price: String,
    val otPrice: String,
    val activity: String?,
)
