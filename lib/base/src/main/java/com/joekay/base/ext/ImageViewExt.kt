package com.joekay.base.ext

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.joekay.base.R
import com.joekay.base.utils.GlideUtils
import com.joekay.base.utils.GlobalUtil
import jp.wasabeef.glide.transformations.RoundedCornersTransformation


/**
 * Glide加载图片，可以指定圆角弧度。
 *
 * @param url 图片地址
 * @param round 圆角，单位dp
 * @param cornerType 圆角角度
 */
fun ImageView.load(
    url: String,
    round: Float = 0f,
    cornerType: RoundedCornersTransformation.CornerType = RoundedCornersTransformation.CornerType.ALL
) {
    if (round == 0f) {
        Glide.with(this.context).load(url).into(this)
    } else {
        val option = RequestOptions.bitmapTransform(
            RoundedCornersTransformation(
                dp2px(round),
                0,
                cornerType
            )
        ).placeholder(
            R.drawable.img_loading
        )
        Glide.with(this.context).load(url).apply(option).into(this)
    }
}

/**
 * Glide加载图片，可以定义配置参数。
 *
 * @param url 图片地址
 * @param options 配置参数
 */
fun ImageView.load(url: String, options: RequestOptions.() -> RequestOptions) {
    Glide.with(this.context).load(url).apply(RequestOptions().options()).into(this)
}
/*
    ImageView加载网络图片
 */
fun ImageView.loadUrl(url: String) {
    GlideUtils.loadUrlImage(context, url, this)
}

/*
    ImageView加载Drawable
 */
fun ImageView.loadDrawable(drawableId: Int) {
    GlideUtils.loadImage(context, GlobalUtil.getDrawable(drawableId)!!, this)
}

/**
 * id:加载失败图片
 */
fun ImageView.loadUrl(url: String, id: Int) {
    GlideUtils.loadImage(context, url, this, id)
}

fun ImageView.loadCircleImage(context: Context, url: String, id: Int) {
    GlideUtils.loadImage(context, url, this, id)
}

fun ImageView.loadRadiusImage(context: Context, url: String, radius: Int) {
    GlideUtils.loadRadiusImage(context, url, radius, this)

}
