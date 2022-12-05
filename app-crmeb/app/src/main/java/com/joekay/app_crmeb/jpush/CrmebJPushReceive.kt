package com.joekay.app_crmeb.jpush

import android.content.Context
import cn.jpush.android.api.NotificationMessage
import cn.jpush.android.service.JPushMessageReceiver
import com.joekay.base.ext.showToast

/**
 * @author:  JoeKai
 * @date:  2022/12/5
 * @explain：
 */
class CrmebJPushReceive : JPushMessageReceiver() {
    override fun onNotifyMessageOpened(p0: Context?, p1: NotificationMessage?) {
        "点击通知".showToast()
        super.onNotifyMessageOpened(p0, p1)
    }
}