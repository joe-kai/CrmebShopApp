package com.joekay.base.widgets

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.joekay.base.R
import com.joekay.base.ktx.getStatusBarHeight
import com.joekay.base.utils.GlobalUtil

/**
 * @author:  JoeKai
 * @date:  2022/11/20
 * @explain：
 */
class ActionBarView : RelativeLayout {
    private var mBarRightTextColor: Int? = 0
    private var mBarLeftHint: Boolean? = false
    private var mBarRightText: String? = ""
    private var mBarLeftIconMargin: Float = 0f
    private var mBarLeftIcon: Drawable? = null
    private var mBarRightIconMargin: Float = 0f
    private var mBarRightIcon: Drawable? = null
    private var mBarTitleColor: Int = 0
    private var mBarTitleSize: Float = 0f
    private var mBarTitle: String? = ""
    private var mContext: Context? = null
    private var mBarLeftIconWidth: Float = 0f
    private var mBarLeftIconHeight: Float = 0f
    private var mBarRightIconWidth: Float = 0f
    private var mBarRightIconHeight: Float = 0f
    private lateinit var txvTitle: TextView
    private lateinit var imvBack: ImageView
    private lateinit var txvRight: TextView

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
        mBarLeftHint = ob.getBoolean(R.styleable.ActionBarView_bar_left_hint, false)
        mBarTitle = ob.getString(R.styleable.ActionBarView_bar_title)
        mBarRightText = ob.getString(R.styleable.ActionBarView_bar_right_text)
        mBarRightTextColor = ob.getColor(R.styleable.ActionBarView_bar_right_color, 0)
        mBarTitleSize = ob.getDimension(R.styleable.ActionBarView_bar_title_size, 0f)
        mBarTitleColor = ob.getColor(R.styleable.ActionBarView_bar_title_color, 0)
        mBarRightIcon = ob.getDrawable(R.styleable.ActionBarView_bar_right_icon)
        mBarRightIconMargin = ob.getDimension(R.styleable.ActionBarView_bar_right_icon_margin, 0f)
        mBarLeftIcon = ob.getDrawable(R.styleable.ActionBarView_bar_left_icon)
        mBarLeftIconMargin = ob.getDimension(R.styleable.ActionBarView_bar_left_icon_margin, 0f)
        mBarLeftIconWidth = ob.getDimension(R.styleable.ActionBarView_bar_left_width, 0f)
        mBarLeftIconHeight = ob.getDimension(R.styleable.ActionBarView_bar_left_height, 0f)
        mBarRightIconWidth = ob.getDimension(R.styleable.ActionBarView_bar_right_width, 0f)
        mBarRightIconHeight = ob.getDimension(R.styleable.ActionBarView_bar_right_height, 0f)

        ob.recycle()

        init(context)
    }

    private fun init(context: Context) {
        mContext = context
        val view = View.inflate(context, R.layout.layout_action_bar, null)
        view.setPadding(0, getStatusBarHeight(), 0, 0)
        initView(view)
        addView(view)
    }


    private fun initView(view: View) {
        txvTitle = view.findViewById(R.id.txv_title)
        imvBack = view.findViewById(R.id.imv_back)
        txvRight = view.findViewById(R.id.txv_right)
        initView()

    }


    private fun initView() {
        txvTitle.apply {
            //设置title
            if (!TextUtils.isEmpty(mBarTitle)) {
                text = mBarTitle
            }

            //设置title大小
            if (mBarTitleSize != 0f) {
                textSize = mBarTitleSize
            }
            //设置title颜色
            if (mBarTitleColor != 0) {
                setTextColor(mBarTitleColor)
            }
        }


        actionBarRight()


        //左侧按钮
        imvBack.apply {
            //是否显示
            visibility = if (mBarLeftHint!!) {
                GONE
            } else {
                VISIBLE
            }
            //通过属性设置
            if (mBarLeftIcon != null) {
                //设置左侧图标
                setImageDrawable(mBarLeftIcon)
                //设置距离左侧的边距
                val params = layoutParams as LinearLayout.LayoutParams
                if (mBarLeftIconMargin != 0f) {
                    params.leftMargin = mBarLeftIconMargin.toInt()
                }
                //设置宽高
                if (mBarLeftIconWidth != 0f) {
                    params.width = mBarLeftIconWidth.toInt()
                    params.height = mBarLeftIconHeight.toInt()
                }
                layoutParams = params
                //设置左侧点击的事件
                imvBack.setOnClickListener {
                    if (mOnLeftClickListener != null) {
                        mOnLeftClickListener!!.leftClick()
                    }
                }
            } else {
                imvBack.setOnClickListener {
                    //返回，销毁页面
                    (mContext as Activity).finish()
                }
            }
        }

        //设置右边的颜色
        if (mBarRightTextColor != 0) {
            txvRight.setTextColor(mBarRightTextColor!!)
        }

    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:actionBar右侧内容
     */
    private fun actionBarRight() {
        //内容不为空，显示右侧内容
        if (!TextUtils.isEmpty(mBarRightText)) {
            txvRight.visibility = View.VISIBLE
            txvRight.text = mBarRightText
        }

        //右侧设置图片格式
        if (mBarRightIcon != null) {
            txvRight.visibility = View.VISIBLE
            txvRight.background = mBarRightIcon
        }

        val layoutParams = txvRight.layoutParams as RelativeLayout.LayoutParams
        //设置距离右边距离
        if (mBarRightIconMargin != 0f) {
            layoutParams.rightMargin = mBarRightIconMargin.toInt()
        }
        //设置宽
        if (mBarRightIconWidth != 0f) {
            layoutParams.width = mBarRightIconWidth.toInt()
        }
        //设置高
        if (mBarRightIconHeight != 0f) {
            layoutParams.height = mBarRightIconHeight.toInt()
        }
        txvRight.layoutParams = layoutParams

        //右侧按钮点击事件
        txvRight.setOnClickListener {
            if (mOnRightClickListener != null) {
                mOnRightClickListener!!.rightClick()
            }
        }
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:设置Title
     */
    fun setBarTitle(title: String): TextView {
        txvTitle.text = title
        return txvTitle
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:设置标题颜色
     */
    fun setBarTitleColor(color: Int) {
        txvTitle.setTextColor(GlobalUtil.getColor(color))
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:设置标题大小
     */
    fun setBarTitleSize(size: Float) {
        txvTitle.textSize = size
    }


    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:只显示标题
     */
    fun hintLeftAndRight() {
        imvBack.visibility = View.GONE
        txvRight.visibility = View.GONE
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:显示右侧按钮
     */
    fun setRightMenu(menu: String): TextView {
        mBarRightText = menu
        actionBarRight()
        return txvRight
    }

    fun setRightMenu(menu: Int): TextView {
        mBarRightIcon = GlobalUtil.getDrawable(menu)
        actionBarRight()
        return txvRight
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:显示右侧按钮
     */
    fun getRightMenu(menu: Any): TextView {
        if (menu is String) {
            mBarRightText = menu
        } else if (menu is Drawable) {
            mBarRightIcon = menu
        }
        actionBarRight()
        return txvRight
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:获取title
     */
    fun getBarTitle(): TextView {
        return txvTitle
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:左侧
     */
    fun getLeftMenu(): ImageView {
        return imvBack
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:设置左边的图标及宽高距离左边的距离
     */
    fun setBarLeftIcon(icon: Int, l: Int = 0, w: Int = 0, h: Int = 0): ImageView {
        if (icon != View.NO_ID) {
            imvBack.setImageResource(icon)
            if (l != 0) {
                val layoutParams = imvBack.layoutParams as LinearLayout.LayoutParams
                layoutParams.apply {
                    width = w
                    height = h
                    leftMargin = l
                }
                imvBack.layoutParams = layoutParams
            }
            //imvBack.setOnClickListener {
            //    if (mOnLeftClickListener != null) {
            //        mOnLeftClickListener!!.leftClick()
            //    }
            //}
        }
        return imvBack
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:设置右边的宽高和距离
     */
    fun setBarRightParams(r: Int = 0, w: Int = 0, h: Int = 0): TextView {
        if (r != 0) {
            val layoutParams = txvRight.layoutParams as RelativeLayout.LayoutParams
            layoutParams.width = w
            layoutParams.height = h
            layoutParams.rightMargin = r
            txvRight.layoutParams = layoutParams
        }
        return txvRight
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:隐藏左边的按钮
     */
    fun hintLeftBack() {
        imvBack.visibility = View.GONE
    }

    fun setOnRightClickListener(action: () -> Unit) {
        mOnRightClickListener = object : OnRightClickListener {
            override fun rightClick() {
                action()
            }

        }
    }

    private var mOnRightClickListener: OnRightClickListener? = null

    interface OnRightClickListener {
        fun rightClick()
    }

    private var mOnLeftClickListener: OnLeftClickListener? = null

    interface OnLeftClickListener {
        fun leftClick()
    }

    fun setOnLeftClickListener(action: () -> Unit) {
        mOnLeftClickListener = object : OnLeftClickListener {
            override fun leftClick() {
                action()
            }
        }
    }
}