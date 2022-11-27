package com.joekay.base.paging

import android.view.View
import android.view.ViewGroup

/**
 * author:  JoeKai
 * date: 2022/7/26 15:25
 * content：
 */
interface OnItemChildLongClickListener {
    fun onItemChildLongClick(childView: View?, position: Int): Boolean

}