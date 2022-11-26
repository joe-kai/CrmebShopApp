package com.joekay.base.ktx

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.view.Window
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.joekay.base.BaseApplication
import com.joekay.base.utils.GlobalUtil

/**
 * @author:  JoeKai
 * @date:  2022/11/20
 * @explain：
 */

/** 设置状态栏颜色 */
fun Activity.statusBarColor(color: Int) {
    window.statusBarColor = GlobalUtil.getColor(color)
    window.navigationBarColor = GlobalUtil.getColor(color)
    WindowCompat.setDecorFitsSystemWindows(window, false)
}

/** 设置状态栏颜色 */
fun Fragment.statusBarColor(color: Int) = activity?.statusBarColor(color)

fun Window.getInsertPadding(): Int {
    val rootWindowInsert = ViewCompat.getRootWindowInsets(this.decorView) ?: return 0
    val statusInsert = rootWindowInsert.getInsets(WindowInsetsCompat.Type.statusBars())
    val paddingTop = StrictMath.abs(statusInsert.top - statusInsert.bottom)
    val navInsert = rootWindowInsert.getInsets(WindowInsetsCompat.Type.navigationBars())
    val paddingBottom = StrictMath.abs(navInsert.top - navInsert.bottom)
    return paddingTop + paddingBottom
}
@SuppressLint("InternalInsetResource")
inline fun Any.getStatusBarHeight():Int{
    var result = 0
    val resourceId: Int = BaseApplication.instance.resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = BaseApplication.instance.resources.getDimensionPixelSize(resourceId)
    }
    return result
}

 fun Window.setTransparentStyle() {
    this.statusBarColor = Color.TRANSPARENT
     this.navigationBarColor = Color.TRANSPARENT
    WindowCompat.setDecorFitsSystemWindows(this, false)
}

