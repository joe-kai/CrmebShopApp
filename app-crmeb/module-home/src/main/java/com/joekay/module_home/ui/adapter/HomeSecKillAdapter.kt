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
import com.joekay.module_home.databinding.LayoutSecKillItemBinding
import com.joekay.module_home.model.ProductModel
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

/**
 * @author:  JoeKai
 * @date:  2022/12/16
 * @explain：
 */
class HomeSecKillAdapter
@Inject constructor(
    @ActivityContext context: Context
) : BaseAdapter<ProductModel>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ViewHolder()
    }

    override fun generateDefaultLayoutManager(context: Context): RecyclerView.LayoutManager? {
        return LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
    }

    inner class ViewHolder : BaseViewHolder(R.layout.layout_sec_kill_item) {
        private val binding by lazy {
            LayoutSecKillItemBinding.bind(getItemView())
        }

        @SuppressLint("SetTextI18n")
        override fun onBindView(position: Int) {
            val model = getItem(position)
            binding.run{
                ivImage.load(model.image)
                tvName.text = model.title
                tvPrice.text = "¥${model.price}"
                tvOtPrice.text = "¥${model.otPrice}"
                tvOtPrice.paint?.flags = Paint.STRIKE_THRU_TEXT_FLAG
            }
        }

    }
}