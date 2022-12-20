package com.joekay.module_home.ui

import com.joekay.base.vm.BaseViewModel
import com.joekay.module_home.api.HomeRepo
import com.joekay.module_home.model.*
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
    val homeCouponList = BaseLiveData<MutableList<CouponModel>>()

    val homeCombinationModel = BaseLiveData<HomeCombinationModel>()
    val homeSecKillModel = BaseLiveData<HomeSecKillModel>()
    val homeBargainModel = BaseLiveData<HomeBargainModel>()

    fun getHomeData() {
        homeModel.request(this) {
            homeRepo.getHomeData()
        }
    }

    fun onRefresh() {
        getHomeCoupon()
        getHomeBargain()
        getHomeSecKill()
        getCombinationProductList()
    }

    fun getHomeCoupon() {
        homeCouponList.request(this) {
            homeRepo.getHomeCoupon()
        }
    }

    fun getCombinationProductList() {
        homeCombinationModel.request(this) {
            homeRepo.getCombinationProductList()
        }
    }

    fun getHomeSecKill() {
        homeSecKillModel.request(this) {
            homeRepo.getHomeSecKill()
        }
    }

    fun getHomeBargain() {
        homeBargainModel.request(this) {
            homeRepo.getHomeBargain()
        }
    }

    //fun getProductList(type: String) {
    //    homeProductList.request(this) {
    //        homeRepo.getProductList(type)
    //    }
    //}

    fun getHomeProduct(type: String) = homeRepo.getHomeProduct(type)

}