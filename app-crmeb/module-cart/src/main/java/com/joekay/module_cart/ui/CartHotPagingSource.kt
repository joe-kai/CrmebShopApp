package com.joekay.module_cart.ui

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.joekay.module_cart.api.CartApi
import com.joekay.module_cart.model.ProductModel

/**
 * @author:  JoeKai
 * @date:  2022/12/2
 * @explain：
 */
class CartHotPagingSource (val api: CartApi) : PagingSource<Int, ProductModel>() {
    override fun getRefreshKey(state: PagingState<Int, ProductModel>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductModel> {
        return try {
            val page = params.key ?: 1
            val limit = params.loadSize
            val data = api.getHotProductList(limit, page)
            val list = data.data?.list!!
            val preKey = if (page > 1) page - 1 else null
            val nextKey = if (list.isNotEmpty() && list.size >= limit) page + 1 else null
            LoadResult.Page(data.data?.list!!, preKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}