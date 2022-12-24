package com.joekay.module_category.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joekay.base.adapter.BaseAdapter
import com.joekay.base.ext.getStatusBarHeight
import com.joekay.base.ext.load
import com.joekay.base.ext.screenHeight
import com.joekay.module_category.R
import com.joekay.module_category.databinding.LayoutCategoryRightItemBinding
import com.joekay.module_category.databinding.LayoutCategoryRightSubItemBinding
import com.joekay.module_category.model.CategoryModel
import com.joekay.module_category.model.Child
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

/**
 * @author:  JoeKai
 * @date:  2022/11/29
 * @explain：
 */
class CategoryRightAdapter @Inject constructor(
    @ActivityContext context: Context
) : BaseAdapter<CategoryModel>(context) {
    override fun getItemCount(): Int {
        return super.getItemCount() + 1
    }

    override fun getItemViewType(position: Int): Int {
        if (position == itemCount - 1) {
            return -1
        }
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (viewType == -1) {
            return EmptyViewHolder()
        }
        return ViewHolder()
    }

    inner class EmptyViewHolder : BaseViewHolder(LinearLayout(getContext())) {

        override fun onBindView(position: Int) {
            getItemView().setPadding(0, 0, 0, screenHeight - 400)
        }

    }

    inner class ViewHolder : BaseViewHolder(R.layout.layout_category_right_item) {
        private val binding by lazy {
            LayoutCategoryRightItemBinding.bind(getItemView())
        }

        override fun onBindView(position: Int) {
            val subAdapter = SubAdapter(getContext())
            val model = getItem(position)
            binding.run {
                txvCategory.text = model.name
                subAdapter.setData(model.child)
                rvSub.adapter = subAdapter
            }
        }

    }


    inner class SubAdapter constructor(
        context: Context
    ) : BaseAdapter<Child>(context) {

        override fun generateDefaultLayoutManager(context: Context): RecyclerView.LayoutManager? {
            return GridLayoutManager(context, 3)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
            return ViewHolder()
        }

        inner class ViewHolder : BaseViewHolder(R.layout.layout_category_right_sub_item) {
            private val binding by lazy {
                LayoutCategoryRightSubItemBinding.bind(getItemView())
            }

            override fun onBindView(position: Int) {
                val model = getItem(position)
                binding.run {
                    imvIcon.load(model.extra)
                    txvName.text = model.name
                }
            }
        }
    }
}