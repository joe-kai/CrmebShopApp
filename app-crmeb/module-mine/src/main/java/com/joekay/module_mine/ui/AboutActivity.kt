package com.joekay.module_mine.ui

import android.os.Build
import com.joekay.base.ActivityManager
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
        val versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            application.packageManager.getPackageInfo(
                GlobalUtil.appPackage,
                0
            ).longVersionCode
        } else {
            application.packageManager.getPackageInfo(
                GlobalUtil.appPackage,
                0
            ).versionCode.toLong()
        }
        mBinding.run {
            ivAppIcon.load(GlobalUtil.getAppIcon())
            tvAppName.text = getResString(application.applicationInfo.labelRes)
            tvAppVersionCode.buildSpannableString {
                addText("版本号：")
                addText(
                    versionCode.toString()
                ) {
                    setColor(getColor(color.red))
                }
            }
            tvAppVersionName.buildSpannableString {
                addText("版本名称：")
                addText(
                    application.packageManager.getPackageInfo(
                        GlobalUtil.appPackage,
                        0
                    ).versionName
                ) {
                    setColor(getColor(color.red))
                }
            }

        }
    }

}