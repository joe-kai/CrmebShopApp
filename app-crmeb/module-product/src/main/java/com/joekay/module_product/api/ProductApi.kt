package com.joekay.module_product.api

import com.joekay.module_product.model.ProductDetailModel
import com.joekay.network.response.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author:  JoeKai
 * @date:  2022/12/15
 * @explain：
 */
interface ProductApi {
    @GET("/api/front/product/detail/{id}")
    suspend fun getProductDetail(@Path("id") id: String): ApiResponse<ProductDetailModel>

}