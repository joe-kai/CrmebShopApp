package com.joekay.base.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @author:  JoeKai
 * @date:  2022/11/30
 * @explain：
 */
object Adapter {
    interface OnChildLongClickListener {

        /**
         * 当 RecyclerView 某个条目子 View 被长按时回调
         *
         * @param recyclerView      RecyclerView 对象
         * @param childView         被点击的条目子 View
         * @param position          被点击的条目位置
         */
        fun onChildLongClick(recyclerView: RecyclerView?, childView: View?, position: Int): Boolean
    }

    /**
     * RecyclerView 条目点击监听类
     */
    interface OnItemClickListener {

        /**
         * 当 RecyclerView 某个条目被点击时回调
         *
         * @param recyclerView      RecyclerView 对象
         * @param itemView          被点击的条目对象
         * @param position          被点击的条目位置
         */
        fun onItemClick(recyclerView: RecyclerView?, itemView: View?, position: Int)
    }

    /**
     * RecyclerView 条目长按监听类
     */
    interface OnItemLongClickListener {

        /**
         * 当 RecyclerView 某个条目被长按时回调
         *
         * @param recyclerView      RecyclerView 对象
         * @param itemView          被点击的条目对象
         * @param position          被点击的条目位置
         * @return                  是否拦截事件
         */
        fun onItemLongClick(recyclerView: RecyclerView?, itemView: View?, position: Int): Boolean
    }

    /**
     * RecyclerView 条目子 View 点击监听类
     */
    interface OnChildClickListener {

        /**
         * 当 RecyclerView 某个条目 子 View 被点击时回调
         *
         * @param recyclerView      RecyclerView 对象
         * @param childView         被点击的条目子 View
         * @param position          被点击的条目位置
         */
        fun onChildClick(recyclerView: RecyclerView?, childView: View?, position: Int)
    }
}
