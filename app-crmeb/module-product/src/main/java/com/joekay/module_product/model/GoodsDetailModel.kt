package com.joekay.module_product.model

/**
 * @author:  JoeKai
 * @date:  2022/12/15
 * @explain：
 */
data class GoodsDetailModel(
    val activityAllH5: Any,
    val priceName: Any,
    val productAttr: List<ProductAttr>,
    val productInfo: ProductInfo,
    val productValue: Map<String, AttrVauel>,
    val userCollect: Boolean
)
