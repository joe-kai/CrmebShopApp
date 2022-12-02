package com.joekay.module_cart.api

import androidx.paging.Pager
import androidx.paging.PagingData
import com.joekay.module_cart.model.ProductModel
import com.joekay.module_cart.ui.CartHotPagingSource
import com.joekay.network.repository.BaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author:  JoeKai
 * @date:  2022/12/2
 * @explain：
 */
class CartRepo @Inject constructor() : BaseRepository<CartApi>(CartApi::class.java) {

    fun getHotProductList(): Flow<PagingData<ProductModel>> {
        return Pager(config) {
            CartHotPagingSource(api)
        }.flow
    }
}