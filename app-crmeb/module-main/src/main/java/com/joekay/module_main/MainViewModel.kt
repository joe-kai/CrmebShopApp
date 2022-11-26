package com.joekay.module_main

import com.joekay.base.vm.BaseViewModel
import com.joekay.module_main.api.UpdateApkRepo
import com.joekay.module_main.model.UpdateApkModel
import com.joekay.network.liveData.BaseLiveData
import com.joekay.network.liveData.request
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author:  JoeKai
 * @date:  2022/11/26
 * @explain：
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val updateApkRope: UpdateApkRepo
) : BaseViewModel() {

    var updateApk = BaseLiveData<UpdateApkModel>()

    fun getUpdateApk() {
        updateApk.request(this) {
            updateApkRope.getUpdateApk()
        }
    }
}