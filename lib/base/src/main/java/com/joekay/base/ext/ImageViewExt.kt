package com.joekay.base.ext

import android.widget.ImageView
import com.bumptech.glide.request.RequestOptions
import com.joekay.base.R
import com.joekay.base.gilde.GlideApp
import com.joekay.base.gilde.GlideRequest
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

private val defaultPlaceholderGif = R.drawable.gif_glide_loading

///占位图加载gif
private fun ImageView.glideGif(url: Any, gif: Int = -1): GlideRequest<*> {
    this.tag = url
    val gifUrl = when (gif) {
        -1 -> defaultPlaceholderGif
        else -> gif
    }
    return when (gifUrl) {
        -1 -> GlideApp.with(this).load(url)
        else -> GlideApp.with(this).load(url)
            .thumbnail(
                GlideApp.with(this).load(gifUrl)
            )
    }
}


/**
 * Glide加载图片.可设置圆角
 * @param url 图片地址
 * @param round 圆角
 * @param cornerType 裁剪方式
 */
fun ImageView.load(
    url: Any,
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
    GlideApp.with(this.context)
        .load(url)
        .apply(option)
        .into(this)
}

fun ImageView.load(
    url: Any,
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
    GlideApp.with(this.context)
        .load(url)
        .apply(option)
        .apply(RequestOptions().options())
        .into(this)
}

/**
 * Glide加载图片.可设置圆角
 * @param url 图片地址
 * @param round 圆角
 * @param placeholder 占位图
 * @param cornerType 裁剪方式
 */
fun ImageView.loadGif(
    url: Any,
    round: Float = 0f,
    placeholder: Int = -1,
    cornerType: RoundedCornersTransformation.CornerType = RoundedCornersTransformation.CornerType.ALL,
) {
    val option = RequestOptions.bitmapTransform(
        RoundedCornersTransformation(
            dp2px(round),
            0,
            cornerType
        )
    )
    this.glideGif(url, placeholder)
        .apply(option)
        .into(this)
}

fun ImageView.loadGif(
    url: Any,
    round: Float = 0f,
    placeholder: Int = -1,
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
    this.glideGif(url, placeholder)
        .apply(option).apply(RequestOptions().options())
        .into(this)
}
///**
// * Glide加载图片。
// * @param url 图片地址
// * @param placeholder 占位图
// */
//fun ImageView.load(
//    url: String, placeholder: Int = -1
//) {
//    //GlideApp.with(this.context).load(url)
//    this.glideGif(url, placeholder)
//        .into(this)
//}
//
//
///**
// * Glide加载图片.可设置圆角
// * @param url 图片地址
// * @param round 圆角
// * @param placeholder 占位图
// * @param cornerType 裁剪方式
// */
//fun ImageView.load(
//    url: String,
//    round: Float = 0f,
//    placeholder: Int = -1,
//    cornerType: RoundedCornersTransformation.CornerType = RoundedCornersTransformation.CornerType.ALL,
//) {
//    val option = RequestOptions.bitmapTransform(
//        RoundedCornersTransformation(
//            dp2px(round),
//            0,
//            cornerType
//        )
//    )
//    //GlideApp.with(this.context).load(url)
//    this.glideGif(url, placeholder)
//        .apply(option)
//        .into(this)
//}


///**
// * Glide加载图片，可以定义配置参数。
// * @param id 资源图片地址
// * @param round 圆角
// * @param placeholder 占位图
// * @param cornerType 裁剪方式
// * @param options 配置参数
// */
//fun ImageView.load(
//    id: Int,
//    round: Float = 0f,
//    placeholder: Int = -1,
//    cornerType: RoundedCornersTransformation.CornerType = RoundedCornersTransformation.CornerType.ALL,
//    options: RequestOptions.() -> RequestOptions
//) {
//    val option = RequestOptions.bitmapTransform(
//        RoundedCornersTransformation(
//            dp2px(round),
//            0,
//            cornerType
//        )
//    )
//    //GlideApp.with(this.context).load(id)
//    this.glideGif(id, placeholder)
//        .apply(option).apply(RequestOptions().options())
//        .into(this)
//}

///**
// * Glide加载图片，可以定义配置参数。
// *
// * @param id 资源图片地址
// * @param placeholder 占位图
// * @param options 配置参数
// */
//fun ImageView.load(id: Int, placeholder: Int = -1, options: RequestOptions.() -> RequestOptions) {
//    //GlideApp.with(this.context).load(id)
//    this.glideGif(id, placeholder).apply(RequestOptions().options()).into(this)
//}

///**
// * Glide加载图片，可以定义配置参数。
// *
// * @param url 图片地址
// * @param placeholder 占位图
// * @param options 配置参数
// */
//fun ImageView.load(
//    url: String,
//    placeholder: Int = -1,
//    options: RequestOptions.() -> RequestOptions
//) {
//    this.glideGif(url, placeholder).apply(RequestOptions().options()).into(this)
////GlideApp.with(this.context).load(url)
//    //.apply(RequestOptions().options()).into(this)
//}

/////占位图加载gif
//private fun ImageView.glideGif(url: String, gif: Int = -1): GlideRequest<*> {
//    this.tag = url
//    val gifUrl = when (gif) {
//        -1 -> defaultPlaceholderGif
//        else -> gif
//    }
//    return GlideApp.with(this.context).load(url)
//        .thumbnail(
//            GlideApp.with(this.context).load(gifUrl)
//        )
//}



