package com.joekay.base.paging

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

/**
 * author:  JoeKai
 * date: 2022/8/2 23:50
 * content：
 */
abstract class BaseItemCallback<T> :DiffUtil.ItemCallback<T>() {

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}