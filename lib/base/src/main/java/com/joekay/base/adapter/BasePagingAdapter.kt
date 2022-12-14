package com.joekay.base.adapter

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @author:  JoeKai
 * @date:  2022/11/30
 * @explain：PagingAdapter的公共类
 */
abstract class BasePagingAdapter<T : Any>(
    private var context: Context,
    private var diffCallback: DiffUtil.ItemCallback<T>
) :
    PagingDataAdapter<T, BasePagingAdapter<T>.PagingViewHolder>(diffCallback) {
    /** RecyclerView 对象 */
    private var recyclerView: RecyclerView? = null

    /** 条目点击监听器 */
    private var itemClickListener: Adapter.OnItemClickListener? = null


    /** 条目长按监听器 */
    private var itemLongClickListener: Adapter.OnItemLongClickListener? = null

    /** 条目子 View 点击监听器 */
    private val childClickListeners: SparseArray<Adapter.OnChildClickListener?> by lazy { SparseArray() }

    /** 条目子 View 长按监听器 */
    private val childLongClickListeners: SparseArray<Adapter.OnChildLongClickListener?> by lazy { SparseArray() }

    /** ViewHolder 位置偏移值 */
    private var positionOffset: Int = 0

    fun getContext(): Context {
        return context
    }

    fun getAdapterItem(position: Int): T {
        return getItem(position)!!
    }

    override fun onBindViewHolder(holder: PagingViewHolder, position: Int) {
        // 根据 ViewHolder 绑定的位置和传入的位置进行对比
        // 一般情况下这两个位置值是相等的，但是有一种特殊的情况
        // 在外层添加头部 View 的情况下，这两个位置值是不对等的
        positionOffset = position - holder.adapterPosition
        holder.onBindView(position)
    }

    /**
     * 获取 RecyclerView 对象
     */
    open fun getRecyclerView(): RecyclerView? {
        return recyclerView
    }


    /**
     * 条目 ViewHolder，需要子类 ViewHolder 继承
     */
    abstract inner class PagingViewHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {

        constructor(@LayoutRes id: Int) : this(
            LayoutInflater.from(getContext()).inflate(id, getRecyclerView(), false)
        )

        init {
            // 设置条目的点击和长按事件
            if (itemClickListener != null) {
                itemView.setOnClickListener(this)
            }

            if (itemLongClickListener != null) {
                itemView.setOnLongClickListener(this)
            }

            // 设置条目子 View 点击事件
            for (i in 0 until childClickListeners.size()) {
                findViewById<View>(childClickListeners.keyAt(i))?.setOnClickListener(this)
            }

            // 设置条目子 View 长按事件
            for (i in 0 until childLongClickListeners.size()) {
                findViewById<View>(childLongClickListeners.keyAt(i))?.setOnLongClickListener(this)
            }
        }

        /**
         * 数据绑定回调
         */
        abstract fun onBindView(position: Int)

        /**
         * 获取 ViewHolder 位置
         */
        protected open fun getViewHolderPosition(): Int {
            // 这里解释一下为什么用 getLayoutPosition 而不用 getAdapterPosition
            // 如果是使用 getAdapterPosition 会导致一个问题，那就是快速点击删除条目的时候会出现 -1 的情况，因为这个 ViewHolder 已经解绑了
            // 而使用 getLayoutPosition 则不会出现位置为 -1 的情况，因为解绑之后在布局中不会立马消失，所以不用担心在动画执行中获取位置有异常的情况
            return layoutPosition + positionOffset
        }

        /**
         * [View.OnClickListener]
         */
        override fun onClick(view: View) {
            val position: Int = getViewHolderPosition()
            if (position < 0 || position >= itemCount) {
                return
            }
            if (view === getItemView()) {
                itemClickListener?.onItemClick(recyclerView, view, position)
                return
            }
            childClickListeners.get(view.id)?.onChildClick(recyclerView, view, position)
        }

        /**
         * [View.OnLongClickListener]
         */
        override fun onLongClick(view: View): Boolean {
            val position: Int = getViewHolderPosition()
            if (position < 0 || position >= itemCount) {
                return false
            }
            if (view === getItemView()) {
                if (itemLongClickListener != null) {
                    return itemLongClickListener!!.onItemLongClick(recyclerView, view, position)
                }
                return false
            }
            val listener: Adapter.OnChildLongClickListener? =
                childLongClickListeners.get(view.id)
            if (listener != null) {
                return listener.onChildLongClick(recyclerView, view, position)
            }
            return false
        }

        open fun getItemView(): View {
            return itemView
        }

        open fun <V : View?> findViewById(@IdRes id: Int): V? {
            return getItemView().findViewById(id)
        }
    }


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
        // 判断当前的布局管理器是否为空，如果为空则设置默认的布局管理器
        if (recyclerView.layoutManager == null) {
            recyclerView.layoutManager = generateDefaultLayoutManager(context)
        }
        if (generateDefaultDividerDecoration(context) != null) {
            recyclerView.addItemDecoration(generateDefaultDividerDecoration(context)!!)
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = null
    }

    /**
     * 生成默认的布局摆放器
     */
    protected open fun generateDefaultLayoutManager(context: Context): RecyclerView.LayoutManager {
        return LinearLayoutManager(context)
    }

    /**
     * 生成默认的分割线的效果
     */
    protected open fun generateDefaultDividerDecoration(context: Context): RecyclerView.ItemDecoration? {
        return null
    }

    /**
     * 设置 RecyclerView 条目点击监听
     */
    open fun setOnItemClickListener(listener: Adapter.OnItemClickListener?) {
        //checkRecyclerViewState()
        itemClickListener = listener
    }

    /**
     * 设置 RecyclerView 条目子 View 点击监听
     */
    open fun setOnChildClickListener(
        @IdRes vararg id: Int,
        listener: Adapter.OnChildClickListener?
    ) {
        //checkRecyclerViewState()
        id.forEach {
            childClickListeners.put(it, listener)
        }
    }

    /**
     * 设置 RecyclerView 条目长按监听
     */
    open fun setOnItemLongClickListener(listener: Adapter.OnItemLongClickListener?) {
        //checkRecyclerViewState()
        itemLongClickListener = listener
    }

    /**
     * 设置 RecyclerView 条目子 View 长按监听
     */
    open fun setOnChildLongClickListener(
        @IdRes id: Int,
        listener: Adapter.OnChildLongClickListener?
    ) {
        //checkRecyclerViewState()
        childLongClickListeners.put(id, listener)
    }

    /**
     * 检查 RecyclerView 状态
     */
    private fun checkRecyclerViewState() {
        if (recyclerView != null) {
            // 必须在 RecyclerView.setAdapter() 之前设置监听
            throw IllegalStateException("are you ok?")
        }
    }

}