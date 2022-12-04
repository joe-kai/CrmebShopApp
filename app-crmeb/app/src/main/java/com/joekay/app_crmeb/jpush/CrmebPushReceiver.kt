package com.joekay.app_crmeb.jpush

import android.app.Notification
import android.content.Context
import android.os.Bundle
import cn.jpush.android.api.JPushInterface
import cn.jpush.android.api.NotificationMessage
import cn.jpush.android.service.JPushMessageReceiver
import com.joekay.base.ext.showToast
import org.json.JSONObject

/**
 * @author:  JoeKai
 * @date:  2022/12/4
 * @explain：
 */
class CrmebPushReceiver : JPushMessageReceiver() {
    override fun getNotification(p0: Context?, p1: NotificationMessage?): Notification {
        return super.getNotification(p0, p1)
    }

    override fun onNotifyMessageOpened(p0: Context?, p1: NotificationMessage?) {
        //"onNotifyMessageOpened".showToast()
        kotlin.io.println("-----------${p1?.toString()}")

        val json = JSONObject(p1?.notificationExtras!!)
        var message_id = json.getInt("type")
        when (message_id) {
            1 -> {
                com.therouter.TheRouter.build(com.joekay.resource.RouterPath.act_mine).navigation()
            }
            else - {
                com.therouter.TheRouter.build(com.joekay.resource.RouterPath.act_product)
                    .navigation()

            }

        }

        "$message_id".showToast()
        super.onNotifyMessageOpened(p0, p1)
    }


    override fun onNotifyMessageArrived(p0: Context?, p1: NotificationMessage?) {
        "onNotifyMessageArrived".showToast()
        super.onNotifyMessageArrived(p0, p1)
    }


    override fun onNotifyMessageDismiss(p0: Context?, p1: NotificationMessage?) {
        "onNotifyMessageDismiss".showToast()
        super.onNotifyMessageDismiss(p0, p1)
    }

}