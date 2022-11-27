package com.joekay.module_home.ui

import com.joekay.base.vm.BaseViewModel
import com.joekay.module_home.api.HomeRepo
import com.joekay.module_home.model.HomeDataModel
import com.joekay.module_home.model.ProductModel
import com.joekay.network.liveData.BaseLiveData
import com.joekay.network.liveData.request
import com.joekay.network.response.PagingResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private var homeRepo: HomeRepo
) : BaseViewModel() {
    val homeModel = BaseLiveData<HomeDataModel>()
    val homeProductList = BaseLiveData<PagingResponse<ProductModel>>()

    fun getHomeData() {
        homeModel.request(this) {
            homeRepo.getHomeData()
        }
    }

    fun getProductList(type: String) {
        homeProductList.request(this) {
            homeRepo.getProductList(type)
        }

    }

    fun getHomeProduct(type: String) = homeRepo.getHomeProduct(type)

}