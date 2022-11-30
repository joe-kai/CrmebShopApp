package com.joekay.base.ext

import android.widget.ImageView
import com.bumptech.glide.request.RequestOptions
import com.joekay.base.gilde.GlideApp
import jp.wasabeef.glide.transformations.RoundedCornersTransformation


/**
 * Glide加载图片。
 * @param url 图片地址
 */
fun ImageView.load(
    url: String
) {
    GlideApp.with(this.context).load(url)
        .into(this)
}


/**
 * Glide加载图片.可设置圆角
 * @param url 图片地址
 * @param round 圆角
 * @param cornerType 裁剪方式
 */
fun ImageView.load(
    url: String,
    round: Float = 0f,
    cornerType: RoundedCornersTransformation.CornerType = RoundedCornersTransformation.CornerType.ALL,
) {
    val option = RequestOptions.bitmapTransform(
        RoundedCornersTransformation(
            dp2px(round),
            0,
            cornerType
        )
    )
    GlideApp.with(this.context).load(url).apply(option)
        .into(this)
}

/**
 * Glide加载图片.可设置圆角
 * @param url 图片地址
 * @param round 圆角
 * @param cornerType 裁剪方式
 * @param options 配置参数
 */
fun ImageView.load(
    url: String,
    round: Float = 0f,
    cornerType: RoundedCornersTransformation.CornerType = RoundedCornersTransformation.CornerType.ALL,
    options: RequestOptions.() -> RequestOptions
) {
    val option = RequestOptions.bitmapTransform(
        RoundedCornersTransformation(
            dp2px(round),
            0,
            cornerType
        )
    )
    GlideApp.with(this.context).load(url).apply(option).apply(RequestOptions().options())
        .into(this)
}

/**
 * Glide加载图片，可以定义配置参数。
 * @param id 资源图片地址
 * @param round 圆角
 * @param cornerType 裁剪方式
 * @param options 配置参数
 */
fun ImageView.load(
    id: Int,
    round: Float = 0f,
    cornerType: RoundedCornersTransformation.CornerType = RoundedCornersTransformation.CornerType.ALL,
    options: RequestOptions.() -> RequestOptions
) {
    val option = RequestOptions.bitmapTransform(
        RoundedCornersTransformation(
            dp2px(round),
            0,
            cornerType
        )
    )
    GlideApp.with(this.context).load(id).apply(option).apply(RequestOptions().options())
        .into(this)
}

/**
 * Glide加载图片，可以定义配置参数。
 *
 * @param id 资源图片地址
 * @param options 配置参数
 */
fun ImageView.load(id: Int, options: RequestOptions.() -> RequestOptions) {
    GlideApp.with(this.context).load(id).apply(RequestOptions().options()).into(this)
}

/**
 * Glide加载图片，可以定义配置参数。
 *
 * @param url 图片地址
 * @param options 配置参数
 */
fun ImageView.load(url: String, options: RequestOptions.() -> RequestOptions) {
    GlideApp.with(this.context).load(url).apply(RequestOptions().options()).into(this)
}

