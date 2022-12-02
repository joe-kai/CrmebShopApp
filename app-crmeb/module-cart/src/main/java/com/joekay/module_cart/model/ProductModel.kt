package com.joekay.module_cart.model

/**
 * @author:  JoeKai
 * @date:  2022/12/2
 * @explain：
 */
data class ProductModel(
    val id: Int,
    val storeName: String,
    val image: String,
    val price: String,
    val otPrice: String,
    val activity: String?,
)
