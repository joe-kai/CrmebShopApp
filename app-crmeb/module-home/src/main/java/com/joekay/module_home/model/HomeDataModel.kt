package com.joekay.module_home.model

/**
 * author:  JoeKai
 * date: 2022/8/2 21:45
 * content：
 */
class HomeDataModel(
    val banner: MutableList<Banner>,
    val menus: MutableList<Menus>,
    val roll: MutableList<Roll>,
    val explosiveMoney: MutableList<ExplosiveMoney>,

    )

data class Banner(
    val name: String,
    val pic: String,
    val id: Int,
    val url: String

)

data class Menus(
    val name: String,
    val show: String,
    val pic: String,
    val id: Int,
    val url: String
)

data class Roll(
    val show: String,
    val id: Int,
    val url: String,
    val info: String,
)

data class ExplosiveMoney(
    val name: String,
    val pic: String,
    val id: Int,
    val type: String,
    val info: String,

    )