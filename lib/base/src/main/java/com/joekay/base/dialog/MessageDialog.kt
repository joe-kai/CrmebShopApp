package com.joekay.base.dialog

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import com.joekay.base.R

/**
 * @author:  JoeKai
 * @date:  2022/12/30
 * @explain：
 */
class MessageDialog {
    class Builder constructor(context: Context) : CommonDialog.Builder<Builder>(context) {
        private val messageView: TextView? by lazy { findViewById(R.id.tv_message) }
        private var listener: OnListener? = null

        init {
            setCustomView(R.layout.message_dialog)
        }

        fun setMessage(@StringRes id: Int): Builder = apply {
            setMessage(getResString(id))
        }

        fun setMessage(text: CharSequence?): Builder = apply {
            messageView?.text = text
        }

        fun setListener(listener: OnListener?): Builder = apply {
            this.listener = listener
        }

        override fun create(): BaseDialog {
            // 如果内容为空就抛出异常
            if (("" == messageView?.text.toString())) {
                throw IllegalArgumentException("Dialog message not null")
            }
            return super.create()
        }

        override fun onClick(view: View) {
            when (view.id) {
                R.id.tv_ui_confirm -> {
                    autoDismiss()
                    listener?.onConfirm(this)
                }
                R.id.tv_ui_cancel -> {
                    autoDismiss()
                    listener?.onCancel(this)
                }
            }
        }
    }


    interface OnListener {

        /**
         * 点击确定时回调
         */
        fun onConfirm(dialog: Builder)

        /**
         * 点击取消时回调
         */
        fun onCancel(dialog: Builder)
    }
}