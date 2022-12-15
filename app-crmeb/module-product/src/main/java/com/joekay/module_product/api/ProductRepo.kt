package com.joekay.module_product.api

import com.joekay.network.repository.BaseRepository
import javax.inject.Inject

/**
 * @author:  JoeKai
 * @date:  2022/12/15
 * @explain：
 */
class ProductRepo @Inject constructor() : BaseRepository<ProductApi>(ProductApi::class.java) {
    suspend fun getGoodsDetail(id: String) = fire {
        api.getGoodsDetail(id)
    }
}