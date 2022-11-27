package com.joekay.base.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.joekay.base.R

/**
 * author:  JoeKai
 * date: 2022/7/26 13:16
 * content：
 */
object GlideUtils {
    fun loadImage(context: Context, url: String, imageView: ImageView) {
        Glide.with(context).load(url).centerCrop().into(imageView)
    }

    fun loadImage(context: Context, drawable: Drawable, imageView: ImageView) {
        Glide.with(context).load(drawable).centerCrop().into(imageView)
    }

    fun loadImageFitCenter(context: Context, url: String, imageView: ImageView) {
        Glide.with(context).load(url).fitCenter().into(imageView)
    }

    fun loadUrlImage(context: Context, url: String, imageView: ImageView) {
        Glide.with(context).load(url)
            .placeholder(R.drawable.img_loading)
            .error(R.drawable.img_error)
            .centerCrop()
            .into(imageView)
    }

    fun loadImage(context: Context, url: String, imageView: ImageView, id: Int) {
        Glide.with(context).load(url)
            .placeholder(R.drawable.img_error)
            .error(id)
            .centerCrop()
            .into(imageView)
    }

    fun loadRadiusImage(context: Context, url: String, radius: Int, imageView: ImageView) {
        Glide.with(context).load(url)
            .placeholder(R.drawable.img_loading)
            .error(R.drawable.img_error)
            .transform(RoundedCorners(radius))
            .into(imageView)
    }

}