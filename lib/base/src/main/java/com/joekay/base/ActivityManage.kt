package com.joekay.base

import android.app.Activity
import java.lang.ref.WeakReference
import java.util.*

/**
 * @author:  JoeKai
 * @date:  2022/11/18
 * @explain：管理所有的Activity
 */
object ActivityManage {
    private val activitys = Stack<WeakReference<Activity>>()


    /**
     * 将Activity压入Application栈
     *
     * @param task 将要压入栈的Activity对象
     */
    fun pushTask(task: WeakReference<Activity>?) {
        activitys.push(task)
    }

    /**
     * 将传入的Activity对象从栈中移除
     *
     * @param task
     */
    fun removeTask(task: WeakReference<Activity>?) {
        activitys.remove(task)
    }

    /**
     * 根据指定位置从栈中移除Activity
     *
     * @param taskIndex Activity栈索引
     */
    fun removeTask(taskIndex: Int) {
        if (activitys.size > taskIndex) activitys.removeAt(taskIndex)
    }

    /**
     * 将栈中Activity移除至栈顶
     */
    fun removeToTop() {
        val end = activitys.size
        val start = 1
        for (i in end - 1 downTo start) {
            val mActivity = activitys[i].get()
            if (null != mActivity && !mActivity.isFinishing) {
                mActivity.finish()
            }
        }
    }

    /**
     * 移除全部（用于整个应用退出）
     */
    fun removeAll() {
        for (task in activitys) {
            val mActivity = task.get()
            if (null != mActivity && !mActivity.isFinishing) {
                mActivity.finish()
            }
        }
    }
}