package com.joekay.module_cart.ui

import com.joekay.base.vm.BaseViewModel
import com.joekay.module_cart.api.CartRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repo: CartRepo,
) : BaseViewModel() {
    fun getHotProductList() = repo.getHotProductList()
}