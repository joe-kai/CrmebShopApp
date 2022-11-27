package com.joekay.module_cart.ui

import androidx.fragment.app.viewModels
import com.joekay.module_base.base.BaseFragment
import com.joekay.module_cart.databinding.FragmentCartBinding
import com.joekay.resource.RouterPath
import com.therouter.router.Route

@Route(path = RouterPath.frag_cart)
class CartFragment : BaseFragment<FragmentCartBinding>() {

    companion object {
        fun newInstance() = CartFragment()
    }

    private var viewModel = viewModels<CartViewModel>()
    override fun initObserve() {
    }

    override fun initBinding() {
    }


}