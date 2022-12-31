package com.joekay.base.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * @author:  JoeKai
 * @date:  2022/12/31
 * @explain：
 */
class ScratchView : View {
    private var mPreX = 0f
    private var mPreY //刮刮乐手指定位的X,Y坐标
            = 0f
    private var dstBitmap: Bitmap? = null
    private var srcBitmap //目标图像（手指刮除图层），原图像
            : Bitmap? = null
    private var mPaint: Paint? = null
    private var mTextPaint //图像画笔工具，文字画笔工具
            : Paint? = null
    private var mPath //手指刮除路径
            : Path? = null
    private var textStr = "恭喜你中奖了" //刮除后显示的文字
    private val TEXT_SIZE = 56

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        mPaint = Paint()
        mPaint!!.color = Color.RED
        mPaint!!.strokeWidth = 45f
        mPaint!!.style = Paint.Style.STROKE
        mPath = Path()
        mTextPaint = Paint()
        mTextPaint!!.color = Color.BLACK
        mTextPaint!!.textSize = TEXT_SIZE.toFloat()
        val rect = Rect()
        mTextPaint!!.getTextBounds(textStr, 0, textStr.length, rect)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.RED)
        if (dstBitmap == null || srcBitmap == null) {
            srcBitmap = Bitmap.createBitmap(width, height / 4 * 3, Bitmap.Config.ARGB_8888)
            dstBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            srcBitmap?.eraseColor(Color.GRAY)
        }
        canvas.drawText(
            textStr,
            (width / 2 - TEXT_SIZE * textStr.length / 2).toFloat(),
            (height / 2 + TEXT_SIZE / 2).toFloat(),
            mTextPaint!!
        )
        val layerId =
            canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null, Canvas.ALL_SAVE_FLAG)
        val c = Canvas(dstBitmap!!)
        c.drawPath(mPath!!, mPaint!!)
        canvas.drawBitmap(dstBitmap!!, 0f, 0f, mPaint)
        mPaint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OUT)
        canvas.drawBitmap(
            srcBitmap!!,
            ((width - srcBitmap!!.width) / 2).toFloat(),
            ((height - srcBitmap!!.height) / 2).toFloat(),
            mPaint
        )
        mPaint!!.xfermode = null
        canvas.restoreToCount(layerId)
    }

    fun setText(str: String) {
        textStr = str
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mPath!!.moveTo(event.x, event.y)
                mPreX = event.x
                mPreY = event.y
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val endX = (mPreX + event.x) / 2
                val endY = (mPreY + event.y) / 2
                mPath!!.quadTo(mPreX, mPreY, endX, endY)
                mPreX = event.x
                mPreY = event.y
            }
            else -> {}
        }
        postInvalidate()
        return super.onTouchEvent(event)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }
}