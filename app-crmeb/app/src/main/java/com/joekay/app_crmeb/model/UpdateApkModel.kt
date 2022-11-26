package com.joekay.app_crmeb.model

data class UpdateApkModel(
    val fileName: String,
    val updateUrl: String,
    val versionName: String,
    val updateMessage: String,
    val md5: String,
    val versionCode: Int
)