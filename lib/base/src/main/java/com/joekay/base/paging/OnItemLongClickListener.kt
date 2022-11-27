package com.joekay.base.paging

import android.view.View
import android.view.ViewGroup

/**
 * author:  JoeKai
 * date: 2022/7/26 15:26
 * content：
 */
interface OnItemLongClickListener {
    fun onItemLongClick(itemView: View?, position: Int): Boolean

}