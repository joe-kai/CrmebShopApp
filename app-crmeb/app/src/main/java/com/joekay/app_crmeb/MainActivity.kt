package com.joekay.app_crmeb

import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import com.joekay.app_crmeb.databinding.ActivityMainBinding
import com.joekay.lib_base.utils.GlobalUtil
import com.joekay.module_base.base.BaseActivity

/**
 * @author:  JoeKai
 * @date:  2022/11/20
 * @explain：
 */
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val viewModel: MainViewModel by viewModels()
    override fun initObserve() {
        mBinding.actionBarView.run {
            setBarTitle("niaho")
            setBackgroundColor(GlobalUtil.getColor(com.joekay.lib_resource.R.color.purple_500))
        }
    }

    override fun initBinding() {
    }


}