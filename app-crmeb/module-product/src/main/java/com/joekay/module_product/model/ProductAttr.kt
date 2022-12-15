package com.joekay.module_product.model

data class ProductAttr(
    val attrName: String,
    val attrValues: String,
    val id: Int,
    val isDel: Boolean,
    val productId: Int,
    val type: Int
)