package com.joekay.module_base.event

/**
 * @author:  JoeKai
 * @date:  2022/11/27
 * @explain：EventBus通知刷新界面消息
 */
open class RefreshEvent(var activityClass: Class<*>? = null) : MessageEvent()