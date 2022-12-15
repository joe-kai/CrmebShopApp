package com.joekay.module_main.model

data class UpdateApkModel(
    val fileName: String,
    val updateUrl: String,
    val versionName: String,
    val force: Boolean,
    val updateMessage: String,
    val md5: String,
    val versionCode: Int
)