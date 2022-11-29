package com.joekay.module_category.model

data class CategoryModel(
    val child: MutableList<Child>,
    val extra: String,
    val id: Int,
    val name: String,
    val path: String,
    val pid: Int,
    val sort: Int,
    val status: Boolean,
    val type: Int,
    val url: String
)
data class Child(
    val extra: String,
    val id: Int,
    val name: String,
    val path: String,
    val pid: Int,
    val sort: Int,
    val status: Boolean,
    val type: Int,
    val url: String
)