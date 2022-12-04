package com.joekay.module_mine.ui

import com.joekay.base.dsl.buildSpannableString
import com.joekay.base.ext.load
import com.joekay.base.utils.GlobalUtil
import com.joekay.module_base.base.BaseActivity
import com.joekay.module_mine.databinding.ActivityAboutBinding
import com.joekay.resource.RouterPath
import com.therouter.router.Route
import dagger.hilt.android.AndroidEntryPoint
import com.joekay.resource.R.*

@Route(path = RouterPath.act_about)
@AndroidEntryPoint
class AboutActivity : BaseActivity<ActivityAboutBinding>() {
    override fun initObserve() {
    }

    override fun initBinding() {
        mBinding.run {
            ivAppIcon.load(GlobalUtil.getAppIcon())
            tvAppName.text = GlobalUtil.appName
            tvAppVersionCode.buildSpannableString {
                addText("版本号：")
                addText(
                    GlobalUtil.appVersionCode.toString()
                ) {
                    setColor(getColor(color.red))
                }
            }
            tvAppVersionName.buildSpannableString {
                addText("版本名称：")
                addText(
                    GlobalUtil.appVersionName
                ) {
                    setColor(getColor(color.red))
                }
            }
        }
    }
}