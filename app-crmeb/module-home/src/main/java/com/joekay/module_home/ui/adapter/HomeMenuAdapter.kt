package com.joekay.module_home.ui.adapter

import android.content.Context
import android.view.ViewGroup
import com.joekay.base.adapter.AppAdapter
import com.joekay.base.ext.load
import com.joekay.module_home.R
import com.joekay.module_home.databinding.LayoutMenusItemBinding
import com.joekay.module_home.model.Menus
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

/**
 * @author:  JoeKai
 * @date:  2022/11/27
 * @explain：
 */
class HomeMenuAdapter @Inject constructor(
    @ActivityContext context: Context
) : AppAdapter<Menus>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder =
        ViewHolder()


    inner class ViewHolder : AppViewHolder(R.layout.layout_menus_item) {
        private val binding by lazy {
            LayoutMenusItemBinding.bind(getItemView())
        }

        override fun onBindView(position: Int) {
            val item = getItem(position)
            binding.run {
                imvIcon.load(item.pic)
                txvName.text = item.name
            }
        }

    }
}