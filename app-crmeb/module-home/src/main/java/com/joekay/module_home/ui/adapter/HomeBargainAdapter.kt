package com.joekay.module_home.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joekay.base.adapter.BaseAdapter
import com.joekay.base.ext.load
import com.joekay.module_home.R
import com.joekay.module_home.databinding.LayoutBargainItemBinding
import com.joekay.module_home.model.ProductModel
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

/**
 * @author:  JoeKai
 * @date:  2022/12/16
 * @explain：
 */
class HomeBargainAdapter
@Inject constructor(
    @ActivityContext context: Context
) : BaseAdapter<ProductModel>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ViewHolder()
    }

    override fun generateDefaultLayoutManager(context: Context): RecyclerView.LayoutManager? {
        return LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    inner class ViewHolder : BaseViewHolder(R.layout.layout_bargain_item) {
        private val binding by lazy {
            LayoutBargainItemBinding.bind(getItemView())
        }

        @SuppressLint("SetTextI18n")
        override fun onBindView(position: Int) {
            val model = getItem(position)
            binding.run {
                ivImage.load(model.image)
                tvName.text = model.title
                tvPrice.text = "¥${model.price}"
            }
        }

    }
}