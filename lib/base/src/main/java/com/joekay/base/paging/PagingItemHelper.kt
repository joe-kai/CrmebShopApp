package com.joekay.base.paging

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.StringRes

/**
 * author:  JoeKai
 * date: 2022/8/2 09:25
 * content：
 */
class PagingItemHelper(private val holder: BasePagingAdapter<*>.BaseViewHolder) {
    private val itemView = holder.itemView
    private val viewCache = SparseArray<View>()

    fun getItemView(): View {
        return itemView
    }

    /**
     * 通过控件的Id获取对应的控件，如果没有则加入mViews，则从item根控件中查找并保存到mViews中
     *
     * @param viewId
     * @return
     */
    fun <T : View?> getView(@IdRes viewId: Int): T? {
        var view = viewCache!![viewId]
        if (view == null) {
            view = itemView!!.findViewById(viewId)
            viewCache!!.put(viewId, view)
        }
        return view as T?
    }

    /**
     * 通过ImageView的Id获取ImageView
     *
     * @param viewId
     * @return
     */
    fun getImageView(@IdRes viewId: Int): ImageView? {
        return getView<ImageView>(viewId)
    }

    /**
     * 通过TextView的Id获取TextView
     *
     * @param viewId
     * @return
     */
    fun getTextView(@IdRes viewId: Int): TextView? {
        return getView<TextView>(viewId)
    }

    /**
     * 设置对应id的控件的文本内容
     *
     * @param viewId
     * @param text
     * @return
     */
    fun setText(@IdRes viewId: Int, text: CharSequence?): PagingItemHelper? {
        val view = getView<TextView>(viewId)!!
        view.text = text
        return this
    }

    /**
     * 设置对应id的控件的文本内容
     *
     * @param viewId
     * @param stringResId 字符串资源id
     * @return
     */
    fun setText(@IdRes viewId: Int, @StringRes stringResId: Int): PagingItemHelper? {
        val view = getView<TextView>(viewId)!!
        view.setText(stringResId)
        return this
    }

    fun setImageBitmap(@IdRes viewId: Int, bitmap: Bitmap?): PagingItemHelper? {
        val view = getView<ImageView>(viewId)!!
        view.setImageBitmap(bitmap)
        return this
    }

    fun setImageDrawable(@IdRes viewId: Int, drawable: Drawable?): PagingItemHelper? {
        val view = getView<ImageView>(viewId)!!
        view.setImageDrawable(drawable)
        return this
    }

    /**
     * @param viewId
     * @param textColor 颜色值
     * @return
     */
    fun setTextColor(@IdRes viewId: Int, textColor: Int): PagingItemHelper? {
        val view = getView<TextView>(viewId)!!
        view.setTextColor(textColor)
        return this
    }
}