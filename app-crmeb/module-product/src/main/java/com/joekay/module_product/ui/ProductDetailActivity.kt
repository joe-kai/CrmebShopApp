package com.joekay.module_product.ui

import com.joekay.base.ext.load
import com.joekay.base.utils.GlobalUtil
import com.joekay.module_base.base.BaseActivity
import com.joekay.module_product.databinding.ActivityProductBinding
import com.joekay.module_product.databinding.ActivityProductDetailBinding
import com.joekay.resource.RouterPath
import com.therouter.router.Route

@Route(path = RouterPath.act_productDetail)
class ProductDetailActivity : BaseActivity<ActivityProductDetailBinding>() {
    override fun initObserve() {
    }

    override fun initBinding() {
        mBinding.run {
            ivImage.load(GlobalUtil.getAppIcon())
        }
    }

}