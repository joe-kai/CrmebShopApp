package com.joekay.module_cart.api

import com.joekay.module_cart.model.ProductModel
import com.joekay.network.response.ApiResponse
import com.joekay.network.response.PagingResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author:  JoeKai
 * @date:  2022/12/2
 * @explain：
 */
interface CartApi {

    /**
     * 购物车 热门推荐
     */
    @GET("/api/front/product/hot")
    suspend fun getHotProductList(
        @Query("limit") limit: Int, @Query("page") page: Int
    ): ApiResponse<PagingResponse<ProductModel>>
}