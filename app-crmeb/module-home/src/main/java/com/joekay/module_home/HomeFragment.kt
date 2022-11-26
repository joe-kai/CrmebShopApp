package com.joekay.module_home

import androidx.fragment.app.viewModels
import com.joekay.module_base.base.BaseFragment
import com.joekay.module_home.databinding.FragmentHomeBinding
import com.joekay.resource.RouterPath
import com.therouter.router.Route

@Route(path = RouterPath.frag_home)
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private var viewModel = viewModels<HomeViewModel>()


    override fun initObserve() {
    }

    override fun initBinding() {
    }

}