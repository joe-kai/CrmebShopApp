package com.joekay.base.multiStateView

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * @author:  JoeKai
 * @date:  2022/11/28
 * @explain：
 */
object MultiStatePage {
    /**
     * 实现原理
     * 1.根据目标view在父view中的位置索引,移除原目标view,
     * 2.将MultiStateContainer添加到原view的索引处
     * 3.MultiStateContainer 的 layoutParams 是原目标View的 layoutParams
     */
    @JvmStatic
    @JvmOverloads
    fun bindMultiState(
        targetView: View,
    ): MultiStateView {
        val parent = targetView.parent as ViewGroup?
        var targetViewIndex = 0
        val multiStateContainer = MultiStateView(targetView.context, targetView)
        parent?.let { targetViewParent ->
            for (i in 0 until targetViewParent.childCount) {
                if (targetViewParent.getChildAt(i) == targetView) {
                    targetViewIndex = i
                    break
                }
            }
            targetViewParent.removeView(targetView)
            targetViewParent.addView(multiStateContainer, targetViewIndex, targetView.layoutParams)
        }
        multiStateContainer.initialization()
        return multiStateContainer
    }

    /**
     * 实现原理
     * 1. android.R.id.content 是Activity setContentView 内容的父view
     * 2. 在这个view中移除原本要添加的contentView
     * 3. 将MultiStateContainer设置为 content的子View  MultiStateContainer中持有原有的Activity setContentView
     */
    @JvmStatic
    @JvmOverloads
    fun bindMultiState(
        activity: Activity,
    ): MultiStateView {
        val targetView = activity.findViewById<ViewGroup>(android.R.id.content)
        val targetViewIndex = 0
        val oldContent: View = targetView.getChildAt(targetViewIndex)
        targetView.removeView(oldContent)
        val oldLayoutParams = oldContent.layoutParams
        val multiStateContainer = MultiStateView(oldContent.context, oldContent)
        targetView.addView(multiStateContainer, targetViewIndex, oldLayoutParams)
        multiStateContainer.initialization()
        return multiStateContainer
    }



}