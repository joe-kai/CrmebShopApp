package com.joekay.module_cart.ui.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.joekay.base.adapter.BasePagingAdapter
import com.joekay.base.adapter.GridDividerItemDecoration
import com.joekay.base.adapter.PagingItemCallback
import com.joekay.base.ext.load
import com.joekay.module_cart.R
import com.joekay.module_cart.databinding.HotProductItemBinding
import com.joekay.module_cart.model.ProductModel
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

/**
 * @author:  JoeKai
 * @date:  2022/12/2
 * @explain：
 */
class HotProductAdapter @Inject constructor(
    @ActivityContext context: Context
) : BasePagingAdapter<ProductModel>(context, callback) {
    companion object {
        val callback = object : PagingItemCallback<ProductModel>() {
            override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingViewHolder {
        return ViewHolder(R.layout.hot_product_item)
    }

    override fun generateDefaultLayoutManager(context: Context): RecyclerView.LayoutManager {
        return StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
    }

    override fun generateDefaultDividerDecoration(context: Context): RecyclerView.ItemDecoration? {
        return GridDividerItemDecoration(10)
    }

    inner class ViewHolder(id: Int) : PagingViewHolder(id) {
        private val binding by lazy {
            HotProductItemBinding.bind(getItemView())
        }

        override fun onBindView(position: Int) {
            var model = getAdapterItem(position)
            binding.ivProduct.load(model.image,5f)
            binding.tvProductName.text = model.storeName
            binding.tvPrice.text = model.price
        }

    }
}