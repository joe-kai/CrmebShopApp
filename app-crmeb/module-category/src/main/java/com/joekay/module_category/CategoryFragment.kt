package com.joekay.module_category

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.joekay.module_base.base.BaseFragment
import com.joekay.module_category.databinding.FragmentCategoryBinding

class CategoryFragment : BaseFragment<FragmentCategoryBinding>() {

    companion object {
        fun newInstance() = CategoryFragment()
    }

    private  var viewModel = viewModels<CategoryViewModel>()

    override fun initObserve() {

    }

    override fun initBinding() {
    }

}