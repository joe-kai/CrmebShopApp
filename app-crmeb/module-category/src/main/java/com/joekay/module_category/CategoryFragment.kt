package com.joekay.module_category

import androidx.fragment.app.viewModels
import com.joekay.module_base.base.BaseFragment
import com.joekay.module_category.databinding.FragmentCategoryBinding
import com.joekay.resource.RouterPath
import com.therouter.router.Route

@Route(path = RouterPath.frag_category)
class CategoryFragment : BaseFragment<FragmentCategoryBinding>() {

    companion object {
        fun newInstance() = CategoryFragment()
    }

    private var viewModel = viewModels<CategoryViewModel>()

    override fun initObserve() {

    }

    override fun initBinding() {
    }

}