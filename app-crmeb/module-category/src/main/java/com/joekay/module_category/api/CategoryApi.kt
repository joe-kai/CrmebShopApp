package com.joekay.module_category.api

import com.joekay.module_category.model.CategoryModel
import com.joekay.network.response.ApiResponse
import retrofit2.http.GET

/**
 * @author:  JoeKai
 * @date:  2022/11/28
 * @explain：
 */
interface CategoryApi {
    @GET("/api/front/category")
    suspend fun getCategoryList(): ApiResponse<MutableList<CategoryModel>>
}