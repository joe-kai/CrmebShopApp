package com.joekay.module_cart.ui

import android.graphics.Typeface
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.joekay.base.dsl.buildSpannableString
import com.joekay.base.multiStateView.MultiStateView
import com.joekay.base.multiStateView.bindMultiState
import com.joekay.base.multiStateView.state.EmptyState
import com.joekay.base.paging.FooterAdapter
import com.joekay.module_base.base.BaseFragment
import com.joekay.module_cart.R
import com.joekay.module_cart.databinding.FragmentCartBinding
import com.joekay.module_cart.ui.adapter.HotProductAdapter
import com.joekay.resource.RouterPath
import com.therouter.router.Route
import com.joekay.resource.R.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@Route(path = RouterPath.frag_cart)
@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>() {

    companion object {
        fun newInstance() = CartFragment()
    }

    private val viewModel by viewModels<CartViewModel>()


    @Inject
    lateinit var hotProductAdapter: HotProductAdapter
    private lateinit var cartState: MultiStateView
    override fun initObserve() {
        cartState = mBinding.rvCart.bindMultiState()
        getHotProduct()
    }

    override fun initBinding() {
        cartState.show<EmptyState> {
            it.setEmptyIcon(drawable.no_cart)
            it.hideEmptyMsg()
        }
        mBinding.run {
            tvCartNum.buildSpannableString {
                addText(getResString(R.string.cart_cart_num) + " ")
                addText("10") {
                    setTextStyle(Typeface.BOLD)
                    setColor(getResColor(R.color.cart_top_color))
                }
            }
            rvHot.adapter =
                hotProductAdapter.withLoadStateFooter(FooterAdapter { hotProductAdapter.retry() })
        }
    }

    private fun getHotProduct() {
        lifecycleScope.launchWhenCreated {
            viewModel.getHotProductList().collectLatest {
                hotProductAdapter.submitData(it)
            }
        }
    }
}