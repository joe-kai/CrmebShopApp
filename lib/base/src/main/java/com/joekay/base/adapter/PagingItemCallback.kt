package com.joekay.base.adapter

import androidx.recyclerview.widget.DiffUtil

/**
 * @author:  JoeKai
 * @date:  2022/12/2
 * @explain：
 */
abstract class PagingItemCallback<T> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}