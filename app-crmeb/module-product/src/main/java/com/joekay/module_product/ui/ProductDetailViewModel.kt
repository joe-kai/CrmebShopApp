package com.joekay.module_product.ui

import com.joekay.base.vm.BaseViewModel
import com.joekay.module_product.api.ProductRepo
import com.joekay.module_product.model.ProductDetailModel
import com.joekay.network.liveData.BaseLiveData
import com.joekay.network.liveData.request
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author:  JoeKai
 * @date:  2022/12/15
 * @explain：
 */
@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productRepo: ProductRepo
) : BaseViewModel() {

    val tabs = arrayListOf("商品", "评价", "详情")
    var goodsSpec = ""
    var cartOrBuy: Boolean = false
    var productDetail = BaseLiveData<ProductDetailModel>()


    fun getProductDetails(id: String) {
        productDetail.request(this) {
            productRepo.getProductDetail(id)
        }
    }


}