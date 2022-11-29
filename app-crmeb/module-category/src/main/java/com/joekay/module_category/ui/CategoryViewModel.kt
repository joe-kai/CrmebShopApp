package com.joekay.module_category.ui

import com.joekay.base.vm.BaseViewModel
import com.joekay.module_category.api.CategoryRepo
import com.joekay.module_category.model.CategoryModel
import com.joekay.network.liveData.BaseLiveData
import com.joekay.network.liveData.request
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private var repo: CategoryRepo
) : BaseViewModel() {
    val categoryList = BaseLiveData<MutableList<CategoryModel>>()

    init {
        getCategoryList()
    }

    private fun getCategoryList() {
        categoryList.request(this) {
            repo.getCategoryList()
        }
    }
}