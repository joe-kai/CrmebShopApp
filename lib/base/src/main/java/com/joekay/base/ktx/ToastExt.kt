package com.joekay.base.ktx

import android.widget.Toast
import com.joekay.base.ActivityManager
import com.joekay.base.BaseApplication

/**
 * @author:  JoeKai
 * @date:  2022/11/22
 * @explain：
 */
/**
 *  author:  JoeKai
 * date: 2022/7/16 14:18
 * content：弹出Toast提示。
 * @param duration 显示消息的时间  Either {@link #LENGTH_SHORT} or {@link #LENGTH_LONG}
 */
private var time: Long = 0
private var oldMsg: String? = null
fun String.showToast(duration: Int = Toast.LENGTH_SHORT) {
    if (this != oldMsg) {
        Toast.makeText(ActivityManager.getInstance().getApplication(), this, duration).show()
        time = System.currentTimeMillis()
    } else {
        if (System.currentTimeMillis() - time > 2000) {
            Toast.makeText(ActivityManager.getInstance().getApplication(), this, duration).show()
            time = System.currentTimeMillis()
        }
    }
    oldMsg = this
}