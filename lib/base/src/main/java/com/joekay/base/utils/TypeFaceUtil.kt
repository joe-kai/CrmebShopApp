package com.joekay.base.utils

import android.graphics.Typeface
import com.joekay.base.ActivityManager

/**
 * @author:  JoeKai
 * @date:  2022/11/27
 * @explain：自定义字体工具类
 */
object TypeFaceUtil {

    const val FZLL_TYPEFACE = 1

    const val FZDB1_TYPEFACE = 2

    const val FUTURA_TYPEFACE = 3

    const val DIN_TYPEFACE = 4

    const val LOBSTER_TYPEFACE = 5

    private var fzlLTypeface: Typeface? = null

    private var fzdb1Typeface: Typeface? = null

    private var futuraTypeface: Typeface? = null

    private var dinTypeface: Typeface? = null

    private var lobsterTypeface: Typeface? = null

    fun getFzlLTypeface() = if (fzlLTypeface == null) {
        try {
            fzlLTypeface = Typeface.createFromAsset(
                ActivityManager.getInstance().getApplication().assets,
                "fonts/FZLanTingHeiS-L-GB-Regular.TTF"
            )
            fzlLTypeface
        } catch (e: RuntimeException) {
            Typeface.DEFAULT
        }
    } else {
        fzlLTypeface!!
    }

    fun getFzdb1Typeface() = if (fzdb1Typeface == null) {
        try {
            fzdb1Typeface = Typeface.createFromAsset(
                ActivityManager.getInstance().getApplication().assets,
                "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF"
            )
            fzdb1Typeface
        } catch (e: RuntimeException) {
            Typeface.DEFAULT
        }
    } else {
        fzdb1Typeface!!
    }

    fun getFuturaTypeface() = if (futuraTypeface == null) {
        try {
            futuraTypeface = Typeface.createFromAsset(
                ActivityManager.getInstance().getApplication().assets,
                "fonts/Futura-CondensedMedium.ttf"
            )
            futuraTypeface
        } catch (e: RuntimeException) {
            Typeface.DEFAULT
        }
    } else {
        futuraTypeface!!
    }

    fun getDinTypeface() = if (dinTypeface == null) {
        try {
            dinTypeface = Typeface.createFromAsset(
                ActivityManager.getInstance().getApplication().assets,
                "fonts/DIN-Condensed-Bold.ttf"
            )
            dinTypeface
        } catch (e: RuntimeException) {
            Typeface.DEFAULT
        }

    } else {
        dinTypeface!!
    }

    fun getLobsterTypeface() = if (lobsterTypeface == null) {
        try {
            lobsterTypeface = Typeface.createFromAsset(
                ActivityManager.getInstance().getApplication().assets,
                "fonts/Lobster-1.4.otf"
            )
            lobsterTypeface
        } catch (e: RuntimeException) {
            Typeface.DEFAULT
        }

    } else {
        lobsterTypeface!!
    }
}