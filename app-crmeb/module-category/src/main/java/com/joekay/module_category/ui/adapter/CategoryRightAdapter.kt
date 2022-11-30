package com.joekay.module_category.ui.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joekay.base.adapter.AppAdapter
import com.joekay.base.ext.load
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
) : AppAdapter<CategoryModel>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        return ViewHolder()
    }

    inner class ViewHolder : AppViewHolder(R.layout.layout_category_right_item) {
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
    ) : AppAdapter<Child>(context) {

        override fun generateDefaultLayoutManager(context: Context): RecyclerView.LayoutManager? {
            return GridLayoutManager(context, 3)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
            return ViewHolder()
        }

        inner class ViewHolder : AppViewHolder(R.layout.layout_category_right_sub_item) {
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