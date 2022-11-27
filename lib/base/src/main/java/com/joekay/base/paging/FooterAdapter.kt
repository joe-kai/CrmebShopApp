package com.joekay.base.paging

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.joekay.base.databinding.PagingFooterItemBinding

/**
 * author:  JoeKai
 * date: 2022/8/2 09:44
 * content：Paging 上拉尾部Adapter
 */
class FooterAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<FooterAdapter.FooterViewHolder>() {
    class FooterViewHolder(binding: PagingFooterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var pagingBinding = binding
    }

    private val TAG = "FooterAdapter"
    override fun onBindViewHolder(holder: FooterViewHolder, loadState: LoadState) {
        Log.d(TAG, "onBindViewHolder: $loadState ")
        holder.pagingBinding.run {
            progressBar.isVisible = loadState is LoadState.Loading
            txvRetry.isVisible = loadState is LoadState.Error
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): FooterViewHolder {
        val binding =
            PagingFooterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.txvRetry.setOnClickListener {
            retry()
        }
        return FooterViewHolder(binding)
    }
}