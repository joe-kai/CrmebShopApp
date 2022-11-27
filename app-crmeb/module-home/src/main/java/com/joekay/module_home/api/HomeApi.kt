package com.joekay.module_home.api

import com.joekay.module_home.model.HomeDataModel
import com.joekay.module_home.model.ProductModel
import com.joekay.network.response.ApiResponse
import com.joekay.network.response.PagingResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author:  JoeKai
 * @date:  2022/11/26
 * @explain：
 */
interface HomeApi {
    @GET("/api/front/index")
    suspend fun getHomeData(): ApiResponse<HomeDataModel>


    @GET("/api/front/index/product/{type}")
    suspend fun getHomeProduct(
        @Path("type") type: String,
        @Query("limit") limit: Int,
        @Query("page") page: Int,
    ): ApiResponse<PagingResponse<ProductModel>>
}