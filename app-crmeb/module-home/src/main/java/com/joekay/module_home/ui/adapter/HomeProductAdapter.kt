package com.joekay.module_home.ui.adapter

import android.content.Context
import android.graphics.Paint
import com.joekay.base.ext.loadUrl
import com.joekay.base.paging.BaseItemCallback
import com.joekay.base.paging.BasePagingAdapter
import com.joekay.base.paging.PagingItemHelper
import com.joekay.module_home.R
import com.joekay.module_home.databinding.LayoutHomeProductItemBinding
import com.joekay.module_home.model.ProductModel
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

/**
 * @author:  JoeKai
 * @date:  2022/11/27
 * @explain：
 */
class HomeProductAdapter @Inject constructor(
    @ActivityContext context: Context
) : BasePagingAdapter<ProductModel>(callback) {
    companion object {
        val callback = object : BaseItemCallback<ProductModel>() {
            override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
                return oldItem.storeName == newItem.storeName
            }
        }
    }

    override fun getItemLayout(position: Int): Int {
        return R.layout.layout_home_product_item
    }

    override fun bindData(helper: PagingItemHelper, data: ProductModel) {
        val mBinding = LayoutHomeProductItemBinding.bind(helper.getItemView())
        mBinding.run {
            imvProduct.loadUrl(data.image)
            txvProductName.text = data.storeName
            txvOtPrice.text = "¥${data.otPrice}"
            txvOtPrice.paint?.flags = Paint.STRIKE_THRU_TEXT_FLAG
            txvPrice.text = "¥${data.price}"
        }
    }
}