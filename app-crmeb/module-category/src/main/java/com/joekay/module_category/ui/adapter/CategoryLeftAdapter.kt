package com.joekay.module_category.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joekay.base.adapter.BaseAdapter
import com.joekay.module_category.R
import com.joekay.module_category.databinding.LayoutCategoryLeftItemBinding
import com.joekay.module_category.model.CategoryModel
import com.joekay.resource.R.color
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

/**
 * @author:  JoeKai
 * @date:  2022/11/29
 * @explain：
 */
class CategoryLeftAdapter @Inject constructor(
    @ActivityContext context: Context
) : BaseAdapter<CategoryModel>(context) {

    /** 当前选中条目位置 */
    private var selectedPosition: Int = 0

    fun getSelectedPosition(): Int {
        return selectedPosition
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSelectedPosition(position: Int) {
        selectedPosition = position
        notifyDataSetChanged()
    }

    override fun generateDefaultLayoutManager(context: Context): RecyclerView.LayoutManager? {
        return LinearLayoutManager(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ViewHolder()
    }

    inner class ViewHolder : BaseViewHolder(R.layout.layout_category_left_item) {
        private val binding by lazy {
            LayoutCategoryLeftItemBinding.bind(getItemView())
        }

        override fun onBindView(position: Int) {
            val model = getItem(position)
            binding.run {
                txvName.text = model.name
                txvName.isSelected = selectedPosition == position
                txvName.typeface = if (position == selectedPosition) {
                    txvName.setBackgroundResource(color.white)
                    Typeface.DEFAULT_BOLD
                } else {
                    txvName.setBackgroundResource(color.transparent)
                    Typeface.DEFAULT

                }
            }
        }

    }
}