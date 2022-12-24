package com.joekay.module_product.ui.dialog

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.joekay.base.adapter.Adapter
import com.joekay.base.adapter.BaseAdapter
import com.joekay.base.dialog.BaseDialog
import com.joekay.base.ext.load
import com.joekay.module_product.R
import com.joekay.module_product.databinding.ProductAttrItemBinding
import com.joekay.module_product.databinding.ProductSpecItemBinding
import com.joekay.module_product.model.AttrVauel
import com.joekay.module_product.model.ProductAttr
import com.joekay.module_product.model.ProductDetailModel

/**
 * @author:  JoeKai
 * @date:  2022/12/20
 * @explain：
 */
class ProductSpecDialog {
    class Builder constructor(context: Context, data: ProductDetailModel) :
        BaseDialog.Builder<Builder>(context) {

        private val ivImage: ImageView? by lazy { findViewById(R.id.iv_image) }
        private val tvProductName: TextView? by lazy { findViewById(R.id.tv_product_name) }
        private val tvPrice: TextView? by lazy { findViewById(R.id.tv_price) }
        private val tvStock: TextView? by lazy { findViewById(R.id.tv_stock) }
        private val rvSpec: RecyclerView? by lazy { findViewById(R.id.rv_spec) }

        private var mGoodsDetailModel: ProductDetailModel
        private var attr: AttrVauel
        private var productSpecAdapter: ProductSpecAdapter


        init {
            setContentView(R.layout.product_spec_dialog)
            this.mGoodsDetailModel = data
            this.attr = data.productValue[data.productValue.keys.first()]!!
            tvProductName?.text = mGoodsDetailModel.productInfo.storeName
            productSpecAdapter = ProductSpecAdapter(context)
            productSpecAdapter.setData(mGoodsDetailModel.productAttr)
            rvSpec?.adapter = productSpecAdapter
            initAttrValue(attr)

        }

        private fun initAttrValue(attr: AttrVauel) {
            ivImage?.load(attr.image)
            tvPrice?.text = "¥${attr.price}"
            tvStock?.text = "库存：${attr.stock}"
        }


    }

    private class ProductSpecAdapter(context: Context) : BaseAdapter<ProductAttr>(context) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
            return ViewHolder()
        }

        inner class ViewHolder : BaseViewHolder(R.layout.product_spec_item) {
            private val binding by lazy {
                ProductSpecItemBinding.bind(getItemView())
            }

            override fun onBindView(position: Int) {
                val model = getItem(position)
                binding.run {
                    tvAttrName.text = model.attrName
                    val productAttrAdapter = ProductAttrAdapter(getContext())
                    val attrs = model.attrValues.split(",")
                    productAttrAdapter.setData(attrs.toMutableList())
                    rvAttr.adapter = productAttrAdapter
                    productAttrAdapter.setOnItemClickListener(object : Adapter.OnItemClickListener {
                        override fun onItemClick(
                            recyclerView: RecyclerView?,
                            itemView: View?,
                            index: Int
                        ) {
                            productAttrAdapter.setSelectIndex(index)
                        }
                    }


                    )
                }
            }
        }
    }

    private class ProductAttrAdapter(context: Context) : BaseAdapter<String>(context) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
            return ViewHolder()
        }

        override fun generateDefaultLayoutManager(context: Context): RecyclerView.LayoutManager? {
            val flexbox = FlexboxLayoutManager(context)
            flexbox.flexDirection = FlexDirection.ROW
            flexbox.flexWrap = FlexWrap.WRAP;
            return flexbox
        }

        private var selectIndex = 0
        fun setSelectIndex(index: Int) {
            selectIndex = index
            notifyDataSetChanged()
        }

        inner class ViewHolder : BaseViewHolder(R.layout.product_attr_item) {
            private val binding by lazy {
                ProductAttrItemBinding.bind(getItemView())
            }

            override fun onBindView(position: Int) {
                val attr = getItem(position)
                binding.tvAttr.run {
                    text = attr
                    if (selectIndex == position) {
                        shapeDrawableBuilder
                            .setSolidColor(com.joekay.base.R.color.gray)
                            .setStrokeColor(com.joekay.base.R.color.common_accent_color)
                            .setStrokeWidth(2)
                            .intoBackground()
                    } else {
                        shapeDrawableBuilder
                            .setSolidColor(com.joekay.base.R.color.black5)
                            .intoBackground()
                    }
                }
            }
        }

    }

}