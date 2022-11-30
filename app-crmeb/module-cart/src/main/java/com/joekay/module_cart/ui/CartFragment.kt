package com.joekay.module_cart.ui

import android.graphics.Typeface
import androidx.fragment.app.viewModels
import com.joekay.base.dsl.buildSpannableString
import com.joekay.module_base.base.BaseFragment
import com.joekay.module_cart.R
import com.joekay.module_cart.databinding.FragmentCartBinding
import com.joekay.resource.RouterPath
import com.therouter.router.Route
import java.time.format.TextStyle

@Route(path = RouterPath.frag_cart)
class CartFragment : BaseFragment<FragmentCartBinding>() {

    companion object {
        fun newInstance() = CartFragment()
    }

    private var viewModel = viewModels<CartViewModel>()
    override fun initObserve() {
    }

    override fun initBinding() {
        mBinding.run {
            tvCartNum.buildSpannableString {
                addText(getResString(R.string.cart_cart_num) + " ")
                addText("10") {
                    setTextStyle(Typeface.BOLD)
                    setColor(getResColor(R.color.cart_top_color))
                }
            }
        }
    }


}