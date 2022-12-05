package com.joekay.base.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import com.joekay.base.R


/**
 * @author:  JoeKai
 * @date:  2022/12/2
 * @explain：自定义进度条Button
 */
@Suppress("DEPRECATION")
class ProgressButton : AppCompatButton {
    private var mCornerRadius = 0f
    private var mProgressMargin = 0f
    private var mFinish = false
    private var mProgress = 0
    private var mMaxProgress = 100
    private var mMinProgress = 0
    private var mDrawableButton: GradientDrawable? = null
    private var mDrawableProgressBackground: GradientDrawable? = null
    private var mDrawableProgress: GradientDrawable? = null

    @JvmOverloads
    constructor(context: Context) : super(context) {
        ProgressButton(context, null)
    }

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        ProgressButton(context, attrs, 0)
        initialize(context, attrs!!)

    }

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        initialize(context, attrs!!)
    }

    @SuppressLint("CustomViewStyleable")
    private fun initialize(context: Context, attrs: AttributeSet) {
        mDrawableProgressBackground = GradientDrawable()
        mDrawableProgress = GradientDrawable()
        mDrawableButton = GradientDrawable()
        val defaultButtonColor = Color.parseColor("#FFF4F4F4")
        val defaultProgressColor = Color.parseColor("#FF03A9F4")
        val defaultBackColor = Color.parseColor("#FFF4F4F4")
        val attr = context.obtainStyledAttributes(attrs, R.styleable.ProgressButton)
        try {
            mProgressMargin =
                attr.getDimension(R.styleable.ProgressButton_progressMargin, mProgressMargin)
            mCornerRadius =
                attr.getDimension(R.styleable.ProgressButton_cornerRadius, mCornerRadius)
            val buttonColor =
                attr.getColor(R.styleable.ProgressButton_buttonColor, defaultButtonColor)
            mDrawableButton?.setColor(buttonColor)
            val progressBackColor =
                attr.getColor(R.styleable.ProgressButton_progressBackColor, defaultBackColor)
            mDrawableProgressBackground?.setColor(progressBackColor)
            val progressColor =
                attr.getColor(R.styleable.ProgressButton_progressColor, defaultProgressColor)
            mDrawableProgress?.setColor(progressColor)

            mProgress = attr.getInteger(R.styleable.ProgressButton_progress, mProgress)
            mMinProgress = attr.getInteger(R.styleable.ProgressButton_minProgress, mMinProgress)
            mMaxProgress = attr.getInteger(R.styleable.ProgressButton_maxProgress, mMaxProgress)
        } finally {
            attr.recycle()
        }
        mDrawableButton?.cornerRadius = mCornerRadius
        mDrawableProgressBackground?.cornerRadius = mCornerRadius
        mDrawableProgress?.cornerRadius = mCornerRadius - mProgressMargin
        setBackgroundDrawable(mDrawableButton)
        mFinish = false
    }

    override fun onDraw(canvas: Canvas?) {
        if (mProgress in (mMinProgress + 1)..mMaxProgress && !mFinish) {
            var progressWidth =
                measuredWidth.toFloat() * ((mProgress - mMinProgress).toFloat() / mMaxProgress - mMinProgress)

            if (progressWidth < mCornerRadius * 2) {
                progressWidth = mCornerRadius * 2
            }
            mDrawableProgress!!.setBounds(
                mProgressMargin.toInt(),
                mProgressMargin.toInt(),
                (progressWidth - mProgressMargin).toInt(),
                measuredHeight - mProgressMargin.toInt()
            )

            mDrawableProgress!!.draw(canvas!!)
            if (mProgress == mMaxProgress) {
                mFinish = true
            }
        }
        super.onDraw(canvas)
    }

    /**
     * 设置当前进度
     */
    fun setProgress(progress: Int) {
        if (!mFinish) {
            mProgress = progress
            setBackgroundDrawable(mDrawableProgressBackground)
            invalidate()
        }
    }

    /**
     * 设置圆角
     */
    fun setCornerRadius(radius: Float) {
        mCornerRadius = radius
    }

    /**
     * 设置进度条Margin
     */
    fun setProgressMargin(margin: Float) {
        mProgressMargin = margin
    }

    /**
     * 设置Button背景色
     */
    fun setButtonColor(buttonColor: Int) {
        mDrawableButton?.setColor(buttonColor)

    }

    /**
     * 设置进度条背景色
     */
    fun setProgressBackColor(color: Int) {
        mDrawableProgressBackground?.setColor(color)
    }

    /**
     * 设置进度条颜色
     */
    fun setProgressColor(progressColor: Int) {
        mDrawableProgress?.setColor(progressColor)

    }

    /**
     * 设置最大值
     */
    fun setMaxProgress(maxProgress: Int) {
        mMaxProgress = maxProgress
    }

    /**
     * 设置最小值
     */
    fun setMinProgress(minProgress: Int) {
        mMinProgress = minProgress
    }


    /**
     * 完成
     */
    fun reset() {
        mFinish = false
        mProgress = mMinProgress
    }
}