import android.view.ViewGroup
import androidx.annotation.StringRes
import com.joekay.base.widgets.ActionBarView
import com.joekay.base.widgets.OnTitleBarListener

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject-Kotlin
 *    time   : 2019/12/08
 *    desc   : 标题栏意图
 */
interface TitleBarAction : OnTitleBarListener {

    /**
     * 获取标题栏对象
     */
    fun getTitleBar(): ActionBarView?


    /**
     * 设置标题栏的标题
     */
    fun setTitle(@StringRes id: Int) {
        getTitleBar()?.setTitle(id)
    }

    /**
     * 设置标题栏的标题
     */
    fun setTitle(title: CharSequence) {
        getTitleBar()?.setTitle(title)
    }

    /**
     * 设置标题栏的左标题
     */
    fun setLeftTitle(id: Int) {
        getTitleBar()?.setLeftTitle(id)
    }

    fun setLeftTitle(text: CharSequence) {
        getTitleBar()?.setLeftTitle(text)
    }

    fun getLeftTitle(): CharSequence? {
        return getTitleBar()?.getLeftTitle()
    }

    /**
     * 设置标题栏的右标题
     */
    fun setRightTitle(id: Int) {
        getTitleBar()?.setRightTitle(id)
    }

    fun setRightTitle(text: CharSequence) {
        getTitleBar()?.setRightTitle(text)
    }

    fun getRightTitle(): CharSequence? {
        return getTitleBar()?.getRightTitle()
    }

    /**
     * 设置标题栏的左图标
     */
    fun setLeftIcon(id: Int) {
        getTitleBar()?.setLeftIcon(id)
    }

    /**
     * 设置标题栏的右图标
     */
    fun setRightIcon(id: Int) {
        getTitleBar()?.setRightIcon(id)
    }


    /**
     * 递归获取 ViewGroup 中的 TitleBar 对象
     */
    fun obtainTitleBar(group: ViewGroup?): ActionBarView? {
        if (group == null) {
            return null
        }
        for (i in 0 until group.childCount) {
            val view = group.getChildAt(i)
            if (view is ActionBarView) {
                return view
            }
            if (view is ViewGroup) {
                val titleBar = obtainTitleBar(view)
                if (titleBar != null) {
                    return titleBar
                }
            }
        }
        return null
    }
}