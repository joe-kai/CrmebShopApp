package com.joekay.module_home.ui.adapter

import android.content.Context
import android.graphics.Paint
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.joekay.base.adapter.BasePagingAdapter
import com.joekay.base.ext.load
import com.joekay.base.ext.showToast
import com.joekay.module_home.R
import com.joekay.module_home.databinding.LayoutHomeProductItemBinding
import com.joekay.module_home.model.ProductModel
import com.joekay.resource.RouterPath
import com.therouter.TheRouter
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

/**
 * @author:  JoeKai
 * @date:  2022/11/27
 * @explain：
 */
class HomeProductAdapter @Inject constructor(
    @ActivityContext context: Context
) : BasePagingAdapter<ProductModel>(context, callback) {
    companion object {
        val callback = object : DiffUtil.ItemCallback<ProductModel>() {
            override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun generateDefaultLayoutManager(context: Context): RecyclerView.LayoutManager {
        return StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingViewHolder {
        return ViewHolder(R.layout.layout_home_product_item)
    }

    inner class ViewHolder(id: Int) : PagingViewHolder(id) {
        override fun onBindView(position: Int) {
            val mBinding = LayoutHomeProductItemBinding.bind(getItemView())
            val data = getItem(position)!!
            mBinding.run {
                imvProduct.load(
                    data.image,
                    placeholder = com.joekay.resource.R.drawable.ic_img_loading_2
                )
                txvProductName.text = data.storeName
                txvOtPrice.text = "¥${data.otPrice}"
                txvOtPrice.paint?.flags = Paint.STRIKE_THRU_TEXT_FLAG
                txvPrice.text = "¥${data.price}"
            }
        }
    }


}