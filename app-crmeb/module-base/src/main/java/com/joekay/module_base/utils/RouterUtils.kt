package com.joekay.module_base.utils

import com.joekay.resource.RouterPath
import com.therouter.TheRouter

/**
 * @author:  JoeKai
 * @date:  2022/12/15
 * @explain：
 */
object RouterUtils {
    fun goToProductDetail(id: Int) {
        TheRouter
            .build(RouterPath.act_productDetail)
            .withString(BundleKey.PRODUCT_ID, id.toString())
            .navigation()

    }

}