package com.joekay.base.ext

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.joekay.base.dialog.ShareDialogFragment
import com.joekay.base.utils.ShareUtil


/**
 * 调用系统原生分享。
 *
 * @param activity 上下文
 * @param shareContent 分享内容
 * @param shareType SHARE_MORE=0，SHARE_QQ=1，SHARE_WECHAT=2，SHARE_WEIBO=3，SHARE_QQZONE=4
 */
fun share(activity: Activity, shareContent: String, shareType: Int) {
    ShareUtil.share(activity, shareContent, shareType)
}

/**
 * 弹出分享对话框。
 *
 * @param activity 上下文
 * @param shareContent 分享内容
 */
fun ShowDialogShare(activity: Activity, shareContent: String) {
    if (activity is AppCompatActivity) {
        ShareDialogFragment().showDialog(activity, shareContent)
    }
}

/**
 * 切换Fragment，会加入回退栈。
 */
inline fun Any.switchFragment(activity: Activity, fragment: Fragment) {
    (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
        .replace(android.R.id.content, fragment).addToBackStack(null).commitAllowingStateLoss()
}

/**
 * 先移除Fragment，并将Fragment从堆栈弹出。
 */
inline fun Any.removeFragment(activity: Activity, fragment: Fragment) {
    (activity as AppCompatActivity).supportFragmentManager.run {
        beginTransaction().remove(fragment).commitAllowingStateLoss()
        popBackStack()
    }
}

