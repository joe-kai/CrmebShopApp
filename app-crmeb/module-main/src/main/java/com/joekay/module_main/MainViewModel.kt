package com.joekay.module_main

import com.joekay.base.vm.BaseViewModel
import com.joekay.module_main.api.UpdateApkRepo
import com.joekay.module_main.model.UpdateApkModel
import com.joekay.network.liveData.BaseLiveData
import com.joekay.network.liveData.request

/**
 * @author:  JoeKai
 * @date:  2022/11/26
 * @explain：
 */
class MainViewModel : BaseViewModel() {
    private val updateApkRope by lazy {
        UpdateApkRepo()
    }
    var updateApk = BaseLiveData<UpdateApkModel>()

    fun getUpdateApk() {
        updateApk.request(this) {
            updateApkRope.getUpdateApk()
        }
    }
}