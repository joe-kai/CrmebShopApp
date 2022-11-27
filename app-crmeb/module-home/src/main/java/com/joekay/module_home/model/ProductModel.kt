package com.joekay.module_home.model

/**
 * author:  JoeKai
 * date: 2022/8/2 23:49
 * content：
 */
data class ProductModel(
    val id: Int,
    val storeName: String,
    val image: String,
    val price: String,
    val otPrice: String,
    val activity: String?,
)
