package com.joekay.base.multiStateView.state

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import com.joekay.base.R
import com.joekay.base.databinding.LayoutMultiStateBinding
import com.joekay.base.ext.gone
import com.joekay.base.ext.isVisible
import com.joekay.base.ext.visible
import com.joekay.base.multiStateView.MultiStateConfig
import com.joekay.base.multiStateView.MultiStateView

/**
 * @author:  JoeKai
 * @date:  2022/11/28
 * @explain： 空界面
 */
class EmptyState : MultiState() {
    private lateinit var binding: LayoutMultiStateBinding

    override fun onCreateMultiStateView(
        context: Context,
        inflater: LayoutInflater,
        container: MultiStateView
    ): View {
        return inflater.inflate(R.layout.layout_multi_state, container, false)
    }

    override fun onMultiStateViewCreate(view: View) {
        binding = LayoutMultiStateBinding.bind(view)
        setEmptyMsg(MultiStateConfig().emptyMsg)
        setEmptyIcon(MultiStateConfig().emptyIcon)
    }

    fun setEmptyMsg(emptyMsg: String) {
        binding.txvMsg.isVisible(emptyMsg.isNotEmpty())
        binding.txvMsg.text = emptyMsg
    }

    fun hideEmptyMsg() {
        binding.txvMsg.gone()
    }

    fun setEmptyIcon(@DrawableRes emptyIcon: Int) {
        binding.imgIcon.setImageResource(emptyIcon)
    }

    fun hideEmptyIcon() {
        binding.imgIcon.gone()
    }
}