package com.joekay.base.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * author:  JoeKai
 * date: 2022/8/2 09:11
 * content：Paging3Adapter的公共类，主要减少adapter的冗余代码。
 */
abstract class BasePagingAdapter<T : Any>(private var diffCallback: DiffUtil.ItemCallback<T>) :
    PagingDataAdapter<T, RecyclerView.ViewHolder>(diffCallback) {
    companion object {
        private const val TAG = "BasePagingAdapter"
    }

    private var onItemClickListener: OnItemClickListener? = null
    private var onItemLongClickListener: OnItemLongClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
        this.onItemLongClickListener = listener
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position) ?: return
        (holder as BasePagingAdapter<*>.BaseViewHolder).bindNormalData(item)
    }

    fun getAdapterItem(p: Int): T {
        return getItem(p)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder = BaseViewHolder(parent, viewType)
        //holder.itemView.setOnClickListener {
        //    onItemClick(getItem(holder.layoutPosition))
        //}
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(holder.itemView, holder.layoutPosition)
        }
        holder.itemView.setOnLongClickListener {
            onItemLongClickListener?.onItemLongClick(holder.itemView, holder.layoutPosition) == true
        }
        return holder
    }

    override fun getItemViewType(position: Int): Int {
        return getItemLayout(position)
    }

    /**
     * 子类获取layout
     */
    protected abstract fun getItemLayout(position: Int): Int

    /**
     * itemView的点击事件，子类实现
     */
    //protected abstract fun onItemClick(data: T?)

    /**
     * 子类绑定数据
     */
    protected abstract fun bindData(helper: PagingItemHelper, data: T)

    inner class BaseViewHolder(parent: ViewGroup, layout: Int) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(layout, parent, false)
    ) {
        private val helper: PagingItemHelper = PagingItemHelper(this)

        fun bindNormalData(item: Any?) {
            bindData(helper, item as T)
        }
    }

}