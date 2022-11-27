package com.joekay.module_home.api

import androidx.paging.Pager
import androidx.paging.PagingData
import com.joekay.module_home.model.ProductModel
import com.joekay.module_home.ui.HomePagingSource
import com.joekay.network.repository.BaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author:  JoeKai
 * @date:  2022/11/26
 * @explain：
 */
class HomeRepo @Inject constructor() : BaseRepository<HomeApi>(HomeApi::class.java) {
    suspend fun getHomeData() = fire {
        api.getHomeData()
    }

    suspend fun getProductList(type: String)= fireList {
        api.getHomeProduct(type, page, config.pageSize)
    }
    fun getHomeProduct(type: String): Flow<PagingData<ProductModel>> {
        return Pager(config) {
            HomePagingSource(type, api)
        }.flow
    }
}