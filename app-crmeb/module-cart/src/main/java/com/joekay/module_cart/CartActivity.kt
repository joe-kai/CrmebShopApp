package com.joekay.module_cart

import com.joekay.module_base.base.BaseActivity
import com.joekay.module_cart.databinding.ActivityCartBinding
import com.joekay.resource.RouterPath
import com.therouter.router.Route
import dagger.hilt.android.AndroidEntryPoint

@Route(path = RouterPath.act_cart)
@AndroidEntryPoint
class CartActivity : BaseActivity<ActivityCartBinding>() {
    override fun initObserve() {
    }

    override fun initBinding() {
    }

}