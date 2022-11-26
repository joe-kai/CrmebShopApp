package com.joekay.base.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.joekay.base.R
import com.joekay.base.ktx.getStatusBarHeight

/**
 * @author:  JoeKai
 * @date:  2022/11/20
 * @explain：
 */
@Suppress("DEPRECATION")
class ActionBarView : RelativeLayout, View.OnClickListener {
    private var mTitleColor: Int = 0
    private var mTitleSize: Float = 0f
    private var mTitle: String? = ""

    private var mRightTextColor: Int? = 0
    private var mRightText: String? = ""
    private var mRightIconMargin: Float = 0f
    private var mRightIcon: Drawable? = null
    private var mRightIconWidth: Float = 0f
    private var mRightIconHeight: Float = 0f

    private var mLeftHint: Boolean? = false
    private var mLeftTextColor: Int? = 0
    private var mLefetText: String? = ""
    private var mLeftIconMargin: Float = 0f
    private var mLeftIcon: Drawable? = null
    private var mLeftIconWidth: Float = 0f
    private var mLeftIconHeight: Float = 0f

    private var mContext: Context? = null

    /** 监听器对象  */
    private var mListener: OnTitleBarListener? = null
    private lateinit var txvTitle: TextView
    private lateinit var txvRight: TextView
    private lateinit var txvLeft: TextView

    constructor(
        context: Context
    ) : super(context) {
        init(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        val ob =
            context.obtainStyledAttributes(attrs, R.styleable.ActionBarView)
        mTitle = ob.getString(R.styleable.ActionBarView_title)
        mTitleSize = ob.getDimension(R.styleable.ActionBarView_title_size, 0f)
        mTitleColor = ob.getColor(R.styleable.ActionBarView_title_color, 0)

        mRightIconWidth = ob.getDimension(R.styleable.ActionBarView_right_width, 0f)
        mRightIconHeight = ob.getDimension(R.styleable.ActionBarView_right_height, 0f)
        mRightText = ob.getString(R.styleable.ActionBarView_right_text)
        mRightTextColor = ob.getColor(R.styleable.ActionBarView_right_color, 0)
        mRightIcon = ob.getDrawable(R.styleable.ActionBarView_right_icon)
        mRightIconMargin = ob.getDimension(R.styleable.ActionBarView_right_icon_margin, 0f)

        mLeftIconWidth = ob.getDimension(R.styleable.ActionBarView_left_width, 0f)
        mLeftIconHeight = ob.getDimension(R.styleable.ActionBarView_left_height, 0f)
        mLefetText = ob.getString(R.styleable.ActionBarView_left_text)
        mLeftTextColor = ob.getColor(R.styleable.ActionBarView_left_color, 0)
        mLeftHint = ob.getBoolean(R.styleable.ActionBarView_left_hint, false)
        mLeftIcon = ob.getDrawable(R.styleable.ActionBarView_left_icon)
        mLeftIconMargin = ob.getDimension(R.styleable.ActionBarView_left_icon_margin, 0f)

        ob.recycle()

        init(context)
    }

    private fun init(context: Context) {
        mContext = context
        val view = View.inflate(context, R.layout.layout_action_bar, null)
        view.setPadding(0, getStatusBarHeight(), 0, 0)
        view.setBackgroundColor(Color.WHITE)
        initView(view)
        addView(view)
    }

    private fun initView(view: View) {
        txvTitle = view.findViewById(R.id.txv_title)
        txvLeft = view.findViewById(R.id.txv_left)
        txvRight = view.findViewById(R.id.txv_right)
        initView()

    }


    private fun initView() {
        txvTitle.apply {
            //设置title
            if (!TextUtils.isEmpty(mTitle)) {
                text = mTitle
            }

            //设置title大小
            if (mTitleSize != 0f) {
                textSize = mTitleSize
            }
            //设置title颜色
            if (mTitleColor != 0) {
                setTextColor(mTitleColor)
            }
        }
        actionBarRight()
        actionBarLeft()
    }

    private fun actionBarLeft() {
        if (mLeftHint == true) {
            txvLeft.visibility = View.GONE
        }
        //内容不为空，显示右侧内容
        if (!TextUtils.isEmpty(mLefetText)) {
            txvLeft.visibility = View.VISIBLE
            txvLeft.text = mLefetText
            txvLeft.setCompoundDrawables(null, null, null, null)
        } else {
            //右侧设置图片格式
            if (mLeftIcon != null) {
                txvLeft.visibility = View.VISIBLE
                mLeftIcon?.run {
                    setBounds(0, 0, this.minimumWidth, this.minimumHeight)
                }
                txvLeft.setCompoundDrawables(mLeftIcon, null, null, null)
            }
        }


        val layoutParams = txvLeft.layoutParams as RelativeLayout.LayoutParams
        //设置距离右边距离
        if (mLeftIconMargin != 0f) {
            layoutParams.rightMargin = mLeftIconMargin.toInt()
        }
        //设置宽
        if (mLeftIconWidth != 0f) {
            layoutParams.width = mLeftIconWidth.toInt()
        }
        //设置高
        if (mLeftIconHeight != 0f) {
            layoutParams.height = mLeftIconHeight.toInt()
        }
        txvLeft.layoutParams = layoutParams
        //设置右边的颜色
        if (mLeftTextColor != 0) {
            txvLeft.setTextColor(mLeftTextColor!!)
        }

    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:actionBar右侧内容
     */
    private fun actionBarRight() {
        //内容不为空，显示右侧内容
        if (!TextUtils.isEmpty(mRightText)) {
            txvRight.visibility = View.VISIBLE
            txvRight.text = mRightText
            txvRight.setCompoundDrawables(null, null, null, null)
        } else {
            //右侧设置图片格式
            if (mRightIcon != null) {
                txvRight.visibility = View.VISIBLE
                mRightIcon?.run {
                    setBounds(0, 0, this.minimumWidth, this.minimumHeight)
                }
                txvRight.setCompoundDrawables(null, null, mRightIcon, null)

            }
        }

        val layoutParams = txvRight.layoutParams as RelativeLayout.LayoutParams
        //设置距离右边距离
        if (mRightIconMargin != 0f) {
            layoutParams.rightMargin = mRightIconMargin.toInt()
        }
        //设置宽
        if (mRightIconWidth != 0f) {
            layoutParams.width = mRightIconWidth.toInt()
        }
        //设置高
        if (mRightIconHeight != 0f) {
            layoutParams.height = mRightIconHeight.toInt()
        }
        txvRight.layoutParams = layoutParams
        //设置右边的颜色
        if (mRightTextColor != 0) {
            txvRight.setTextColor(mRightTextColor!!)
        }

    }

    /**
     * 设置Title
     */
    fun setTitle(id: Int) = apply {
        setTitle(resources.getString(id));
    }

    /**
     * 设置Title
     */
    fun setTitle(title: CharSequence) = apply {
        mTitle = title.toString()

    }


    /**
     * 设置标题颜色
     */
    fun setTitleColor(color: Int) = apply {
        txvTitle.setTextColor(resources.getColor(color))
    }

    /**
     * 设置标题大小
     */
    fun setTitleSize(size: Float) = apply {
        setTitleSize(TypedValue.COMPLEX_UNIT_SP, size)
    }

    /**
     * 设置标题大小
     */
    private fun setTitleSize(unit: Int, size: Float) = apply {
        txvTitle.setTextSize(unit, size)
    }

    fun getTitle(): CharSequence {
        return txvTitle.text
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:只显示标题
     */
    fun hintLeftAndRight() = apply {
        txvRight.visibility = View.GONE
        txvLeft.visibility = View.GONE
    }

    /**
     * 设置左标题的文本
     */
    fun setLeftTitle(id: Int) = apply {
        setLeftTitle(resources.getString(id))
    }

    /**
     * 设置左标题的文本
     */
    fun setLeftTitle(text: CharSequence) = apply {
        mLefetText = text.toString()
        actionBarLeft()
    }

    fun setLeftTitleColor(color: Int) = apply {
        mLeftTextColor = resources.getColor(color)
        actionBarLeft()
    }

    /**
     * 设置左标题的字体大小
     */
    fun setLeftTitleSize(size: Float) = apply {
        setLeftTitleSize(TypedValue.COMPLEX_UNIT_SP, size)
    }

    private fun setLeftTitleSize(unit: Int, size: Float) = apply {
        txvLeft.setTextSize(unit, size)
    }

    fun setLeftIcon(id: Int) = apply {
        mLeftIcon = resources.getDrawable(id)
        actionBarLeft()
    }

    fun getLeftTitle(): CharSequence {
        return txvLeft.text
    }

    /**
     * 设置右标题的文本
     */
    fun setRightTitle(id: Int) = apply {
        setRightTitle(resources.getString(id))
    }

    fun setRightTitle(text: CharSequence) = apply {
        mRightText = text.toString()
        actionBarRight()
    }

    /**
     * 设置右标题的字体颜色
     */
    fun setRightTitleColor(color: Int) = apply {
        mRightTextColor = resources.getColor(color)
        actionBarRight()
    }

    fun getRightTitle(): CharSequence {
        return txvRight.text
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:显示右侧按钮
     */
    fun setRightTitle(title: String) = apply {
        mRightText = title
        actionBarRight()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setRightIcon(id: Int) = apply {
        mRightIcon = resources.getDrawable(id)
        actionBarRight()
    }


    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:获取title
     */
    fun getTitleView(): TextView {
        return txvTitle
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:隐藏左边的按钮
     */
    fun hintLeftBack() = apply {
        txvLeft.visibility = View.GONE
    }

    /**
     * 设置标题栏的点击监听器
     */
    fun setOnTitleBarListener(listener: OnTitleBarListener) = apply {
        mListener = listener
        // 设置监听
        txvRight.setOnClickListener(this)
        txvLeft.setOnClickListener(this)
        txvTitle.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (mListener == null) {
            return
        }
        if (v === txvLeft) {
            mListener!!.onLeftClick(v)
        } else if (v === txvRight) {
            mListener!!.onRightClick(v)
        } else if (v === txvTitle) {
            mListener!!.onTitleClick(v)
        }
    }
}