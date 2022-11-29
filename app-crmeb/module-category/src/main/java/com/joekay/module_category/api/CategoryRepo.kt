package com.joekay.module_category.api

import com.joekay.network.repository.BaseRepository
import javax.inject.Inject

/**
 * @author:  JoeKai
 * @date:  2022/11/28
 * @explain：
 */
class CategoryRepo @Inject constructor() : BaseRepository<CategoryApi>(CategoryApi::class.java) {

    suspend fun getCategoryList() = fire {
        api.getCategoryList()
    }
}