package com.joekay.module_home.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joekay.base.adapter.Adapter
import com.joekay.base.adapter.BaseAdapter
import com.joekay.module_home.R
import com.joekay.module_home.databinding.LayoutProductTabItemBinding
import com.joekay.module_home.model.ExplosiveMoney
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

/**
 * @author:  JoeKai
 * @date:  2022/11/27
 * @explain：
 */
class HomeProductTabAdapter @Inject constructor(
    @ActivityContext context: Context
) : BaseAdapter<ExplosiveMoney>(context), Adapter.OnItemClickListener {
    /** 当前选中条目位置 */
    private var selectedPosition: Int = 0

    /** 导航栏点击监听 */
    private var listener: OnProductTabListener? = null

    init {
        setOnItemClickListener(this)
    }

    fun getSelectedPosition(): Int {
        return selectedPosition
    }

    fun setSelectedPosition(position: Int) {
        selectedPosition = position
        notifyDataSetChanged()
    }

    /**
     * 设置导航栏监听
     */
    fun setOnProductTabListener(listener: OnProductTabListener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ViewHolder()
    }

    inner class ViewHolder : BaseViewHolder(R.layout.layout_product_tab_item) {
        private val mBinding by lazy {
            LayoutProductTabItemBinding.bind(getItemView())
        }

        override fun onBindView(position: Int) {
            getItem(position).run {
                mBinding.txvTitle.text = name
                mBinding.txvTitle.isSelected = selectedPosition == position
                mBinding.txvSubTitle.text = info
                mBinding.txvSubTitle.isSelected = selectedPosition == position
            }
        }

    }

    interface OnProductTabListener {
        fun onTabItemSelected(position: Int): Boolean
    }

    override fun onItemClick(recyclerView: RecyclerView?, itemView: View?, position: Int) {
        if (selectedPosition == position) {
            return
        }
        if (listener == null) {
            selectedPosition = position
            notifyDataSetChanged()
            return
        }
        if (listener!!.onTabItemSelected(position)) {
            selectedPosition = position
            notifyDataSetChanged()
        }
    }
}