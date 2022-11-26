package com.joekay.module_main.api

import com.joekay.network.repository.BaseRepository
import javax.inject.Inject

/**
 * @author:  JoeKai
 * @date:  2022/11/26
 * @explain：
 */
class UpdateApkRepo @Inject constructor(): BaseRepository<UpdateApkApi>(UpdateApkApi::class.java, "http://crmeb.joekai.top") {
    suspend fun getUpdateApk() = fire {
        api.getUpdateApk()
    }
}