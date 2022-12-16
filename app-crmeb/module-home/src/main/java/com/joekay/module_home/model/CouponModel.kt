package com.joekay.module_home.model

data class CouponModel(
    val day: Int,
    val id: Int,
    val isFixedTime: Boolean,
    val isLimited: Boolean,
    val isUse: Boolean,
    val lastTotal: Int,
    val minPrice: String,
    val money: String,
    val name: String,
    val primaryKey: String,
    val type: Int,
    val useType: Int
)