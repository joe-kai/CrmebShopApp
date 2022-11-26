package com.joekay.module_cart

import android.view.View
import androidx.fragment.app.viewModels
import com.joekay.base.widgets.ActionBarView
import com.joekay.module_base.base.BaseFragment
import com.joekay.module_cart.databinding.FragmentCartBinding

class CartFragment : BaseFragment<FragmentCartBinding>() {

    companion object {
        fun newInstance() = CartFragment()
    }

    private  var viewModel = viewModels<CartViewModel>()
    override fun initObserve() {
    }

    override fun initBinding() {
    }




}