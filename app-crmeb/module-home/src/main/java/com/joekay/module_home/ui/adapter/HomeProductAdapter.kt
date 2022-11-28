package com.joekay.module_home.ui.adapter

import android.content.Context
import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.joekay.base.ext.inflate
import com.joekay.base.ext.loadUrl
import com.joekay.base.ext.showToast
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
) : PagingDataAdapter<ProductModel, RecyclerView.ViewHolder>(callback) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            R.layout.layout_home_product_item.inflate(parent)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val mBinding = LayoutHomeProductItemBinding.bind(holder.itemView)
        val data = getItem(position)!!
        mBinding.run {
            root.setOnClickListener {
                data.id.toString().showToast()
            }
            imvProduct.loadUrl(data.image)
            txvProductName.text = data.storeName
            txvOtPrice.text = "¥${data.otPrice}"
            txvOtPrice.paint?.flags = Paint.STRIKE_THRU_TEXT_FLAG
            txvPrice.text = "¥${data.price}"
        }

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)


    //override fun getItemLayout(position: Int): Int {
    //    return R.layout.layout_home_product_item
    //}
    //
    //override fun bindData(helper: PagingItemHelper, data: ProductModel) {
    //    val mBinding = LayoutHomeProductItemBinding.bind(helper.getItemView())
    //    mBinding.run {
    //        imvProduct.loadUrl(data.image)
    //        txvProductName.text = data.storeName
    //        txvOtPrice.text = "¥${data.otPrice}"
    //        txvOtPrice.paint?.flags = Paint.STRIKE_THRU_TEXT_FLAG
    //        txvPrice.text = "¥${data.price}"
    //    }
    //}
}