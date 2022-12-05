package com.joekay.module_video.ui.adapter

import android.content.Context
import android.view.ViewGroup
import com.joekay.base.adapter.BasePagingAdapter
import com.joekay.base.adapter.PagingItemCallback
import com.joekay.base.ext.load
import com.joekay.module_video.R
import com.joekay.module_video.databinding.LayoutFullVideoItemBinding
import com.joekay.module_video.model.Follow
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

/**
 * @author:  JoeKai
 * @date:  2022/12/5
 * @explain：
 */
class FullVideoAdapter @Inject constructor(
    @ActivityContext context: Context
) : BasePagingAdapter<Follow.Item>(context, CALLBACK) {
    companion object {
        val CALLBACK = object : PagingItemCallback<Follow.Item>() {
            override fun areContentsTheSame(oldItem: Follow.Item, newItem: Follow.Item): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingViewHolder {
        return FullViewHolder(R.layout.layout_full_video_item)
    }

    inner class FullViewHolder(id: Int) : PagingViewHolder(id) {
        private val binding by lazy {
            LayoutFullVideoItemBinding.bind(getItemView())
        }

        override fun onBindView(position: Int) {
            val item = getAdapterItem(position)
            item.data.content.data.run {
                binding.ivAvatar.load(item.data.header.icon ?: author?.icon ?: "")
                binding.tvNickname.text = author?.name ?: ""
                binding.tvContent.text = description
                binding.tvCollectionCount.text = consumption.collectionCount.toString()
                binding.tvReplyCount.text = consumption.replyCount.toString()
            }

        }

    }
}