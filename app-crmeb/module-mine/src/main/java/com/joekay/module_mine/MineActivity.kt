package com.joekay.module_mine

import com.joekay.module_base.base.BaseActivity
import com.joekay.module_mine.databinding.ActivityMineBinding
import com.joekay.resource.RouterPath
import com.therouter.router.Route
import dagger.hilt.android.AndroidEntryPoint

@Route(path = RouterPath.act_mine)
@AndroidEntryPoint
class MineActivity : BaseActivity<ActivityMineBinding>() {
    override fun initObserve() {
    }

    override fun initBinding() {
    }

}