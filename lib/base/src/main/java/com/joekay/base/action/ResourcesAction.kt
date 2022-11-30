package com.joekay.base.action

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.joekay.base.ActivityManager

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject-Kotlin
 *    time   : 2019/09/15
 *    desc   : Context 意图处理（扩展非 Context 类的方法，请不要用 Context 子类实现此接口）
 */
interface ResourcesAction {

    private fun getResContext(): Context {
        return ActivityManager.getInstance().getApplication()
    }


    fun getResResources(): Resources {
        return getResContext().resources
    }

    fun getResString(@StringRes id: Int): String {
        return getResContext().getString(id)
    }

    fun getResString(@StringRes id: Int, vararg formatArgs: Any?): String {
        return getResResources().getString(id, *formatArgs)
    }

    fun getResDrawable(@DrawableRes id: Int): Drawable {
        return ContextCompat.getDrawable(getResContext(), id)!!
    }

    @ColorInt
    fun getResColor(@ColorRes id: Int): Int {
        return ContextCompat.getColor(getResContext(), id)
    }

    fun <S> getResSystemService(serviceClass: Class<S>): S {
        return ContextCompat.getSystemService(getResContext(), serviceClass)!!
    }
}