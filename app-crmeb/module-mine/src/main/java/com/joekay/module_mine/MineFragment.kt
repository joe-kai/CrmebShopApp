package com.joekay.module_mine

import androidx.fragment.app.activityViewModels
import com.joekay.module_base.base.BaseFragment
import com.joekay.module_mine.databinding.FragmentMineBinding

class MineFragment : BaseFragment<FragmentMineBinding>() {

    companion object {
        fun newInstance() = MineFragment()
    }

    private var viewModel = activityViewModels<MineViewModel>()
    override fun initObserve() {
    }

    override fun initBinding() {
    }


}