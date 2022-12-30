package com.joekay.base.widgets

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import com.joekay.base.R
import com.joekay.base.ext.dp2px
import com.joekay.base.ext.mLogD
import com.joekay.base.utils.TypeFaceUtil
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshKernel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.scwang.smart.refresh.layout.simple.SimpleComponent

/**
* @author:  JoeKai
* @date:  2022/11/27
* @explain： 
*/class NoStatusFooter : SimpleComponent, RefreshFooter {

    private var mTextNothing = "" //没有更多数据了

    private var mTitleText: TextView

    private var mFooterHeight = 0

    private var mBackgroundColor = 0

    private var mNoMoreData = false

    private var mRefreshKernel: RefreshKernel? = null

    constructor(context: Context) : this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        View.inflate(context, R.layout.layout_srl_classics_footer, this)
        mTitleText = findViewById(R.id.srl_classics_title)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.NoStatusFooter, 0, 0)
        typedArray.let {
            if (it.hasValue(R.styleable.NoStatusFooter_srlPrimaryColor)) {
                setPrimaryColor(it.getColor(R.styleable.NoStatusFooter_srlPrimaryColor, 0))
            }
            if (it.hasValue(R.styleable.NoStatusFooter_srlAccentColor)) {
                setAccentColor(it.getColor(R.styleable.NoStatusFooter_srlAccentColor, 0))
            }
            if (it.hasValue(R.styleable.NoStatusFooter_srlTextSizeTitle)) {
                mTitleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, it.getDimensionPixelSize(R.styleable.NoStatusFooter_srlTextSizeTitle, dp2px(16f)).toFloat())
            }
            mTextNothing = when {
                it.hasValue(R.styleable.NoStatusFooter_srlTextNothing) -> {
                    it.getString(R.styleable.NoStatusFooter_srlTextNothing) ?: context.getString(R.string.footer_not_more)
                }
                REFRESH_FOOTER_NOTHING != null -> {
                    REFRESH_FOOTER_NOTHING!!
                }
                else -> {
                    context.getString(R.string.footer_not_more)
                }
            }
            mTitleText.typeface = TypefaceTextView.getTypeface(it.getInt(R.styleable.NoStatusFooter_srlTextTitleTypeface, TypeFaceUtil.LOBSTER_TYPEFACE))
            typedArray.recycle()
        }
    }

    override fun onInitialized(@NonNull kernel: RefreshKernel, height: Int, maxDragHeight: Int) {
        mLogD( "onInitialized: height=${height},noMoreData=${mNoMoreData}")
        mRefreshKernel = kernel
        mRefreshKernel?.requestDrawBackgroundFor(this, mBackgroundColor)
        if (mFooterHeight == 0) mFooterHeight = height    //获取SmartRefreshLayout全局设置的Footer高度。
    }

    override fun onFinish(refreshLayout: RefreshLayout, success: Boolean): Int {
        mLogD("onFinish: $success")
        super.onFinish(refreshLayout, success)
        return 0
    }

    /**
     * 设置数据全部加载完成，将不能再次触发加载功能
     */
    override fun setNoMoreData(noMoreData: Boolean): Boolean {
        mLogD("setNoMoreData: $noMoreData")
        if (mNoMoreData != noMoreData) {
            mNoMoreData = noMoreData
            refreshFooterHeight()
            if (noMoreData) {
                mTitleText.text = mTextNothing
            }
        }
        return true
    }

    override fun onStateChanged(refreshLayout: RefreshLayout, oldState: RefreshState, newState: RefreshState) {
        super.onStateChanged(refreshLayout, oldState, newState)
        mLogD(TAG, "onStateChanged: newState=${newState},noMoreData=${mNoMoreData}")
        if (!mNoMoreData) {
            when (newState) {
                RefreshState.None -> {

                }
                RefreshState.PullUpToLoad -> {
                    refreshFooterHeight()
                }
                RefreshState.Loading, RefreshState.LoadReleased -> {

                }
                RefreshState.ReleaseToLoad -> {

                }
                RefreshState.Refreshing -> {

                }
                else -> {

                }
            }
        } else {
            refreshFooterHeight()
        }
    }

    private fun refreshFooterHeight() {
        mLogD(TAG, "refreshFooterHeight: noMoreData=${mNoMoreData}")
        if (mNoMoreData) {
            mRefreshKernel?.refreshLayout?.setFooterHeightPx(mFooterHeight)
        } else {
            mRefreshKernel?.refreshLayout?.setFooterHeight(0f)
        }
        mRefreshKernel?.requestRemeasureHeightFor(this)
    }

    fun setTextTitleSize(size: Float): NoStatusFooter {
        mTitleText.textSize = size
        mRefreshKernel?.requestRemeasureHeightFor(this)
        return this
    }

    fun setTextTitleTypeface(tf: Typeface): NoStatusFooter {
        mTitleText.typeface = tf
        mRefreshKernel?.requestRemeasureHeightFor(this)
        return this
    }

    fun setPrimaryColor(@ColorInt primaryColor: Int): NoStatusFooter {
        mBackgroundColor = primaryColor
        mRefreshKernel?.requestDrawBackgroundFor(this, primaryColor)
        return this
    }

    fun setAccentColor(@ColorInt accentColor: Int): NoStatusFooter {
        mTitleText.setTextColor(accentColor)
        return this
    }

    fun setAccentColorId(@ColorRes colorId: Int): NoStatusFooter {
        val thisView: View = this
        setAccentColor(ContextCompat.getColor(thisView.context, colorId))
        return this
    }

    companion object {
        const val TAG = "NoStatusFooter"
        var REFRESH_FOOTER_NOTHING: String? = null      //没有更多数据了
    }
}