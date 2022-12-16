package com.joekay.module_home.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joekay.base.adapter.BaseAdapter
import com.joekay.base.dsl.buildSpannableString
import com.joekay.module_home.R
import com.joekay.module_home.databinding.LayoutCouponItemBinding
import com.joekay.module_home.model.CouponModel
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

/**
 * @author:  JoeKai
 * @date:  2022/12/16
 * @explain：
 */
class HomeCouponAdapter
@Inject constructor(
    @ActivityContext context: Context
) : BaseAdapter<CouponModel>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ViewHolder()
    }

    override fun generateDefaultLayoutManager(context: Context): RecyclerView.LayoutManager? {
        return LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    inner class ViewHolder : BaseViewHolder(R.layout.layout_coupon_item) {
        private val binding by lazy {
            LayoutCouponItemBinding.bind(getItemView())
        }

        @SuppressLint("SetTextI18n")
        override fun onBindView(position: Int) {
            val model = getItem(position)
            binding.run {
                tvName.text = model.name
                tvMoney.buildSpannableString {
                    addText(model.money)
                    addText("元") {
                        setTextSize(14)
                    }
                }
                tvMinPrice.text = "满${model.minPrice}元可用"
            }
        }

    }
}