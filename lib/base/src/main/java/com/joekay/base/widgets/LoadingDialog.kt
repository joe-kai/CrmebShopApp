package com.joekay.base.widgets

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import com.joekay.base.R

/**
 * @author:  JoeKai
 * @date:  2022/11/28
 * @explain：
 */
class LoadingDialog : Dialog {
    private var loadingDialog: LoadingDialog? = null

    private var textView: TextView? = null

    constructor(context: Context) : super(
        context, R.style.LoadingDialog
    ) {
        setContentView(R.layout.layout_loading_view)
        val imageView: ImageView = findViewById(R.id.iv_image)
        textView = findViewById(R.id.message)
        val animation: Animation = RotateAnimation(
            0f,
            360f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        animation.duration = 2000
        animation.repeatCount = 50
        animation.fillAfter = true
        imageView.startAnimation(animation)
    }

    fun setMessage(msg: String) {
        textView?.text = msg
    }

    fun showDialog() {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog(context)
        }
        loadingDialog?.show()
    }

    fun dismissDialog() {
        loadingDialog?.dismiss()
    }
}