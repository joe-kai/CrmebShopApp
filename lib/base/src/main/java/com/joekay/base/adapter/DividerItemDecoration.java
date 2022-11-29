package com.joekay.base.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * @author: JoeKai
 * @date: 2022/11/29
 * @explain：
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private Paint mPaint;
    //取名mDivider似乎更恰当
    private Drawable mDrawable;
    //分割线高度，默认为1px
    private int mDividerHeight = 20;

    //列表的方向
    private int mOrientation;
    //系统自带的参数
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    //水平
    public static final int HORIZONTAL_LIST = RecyclerView.HORIZONTAL;
    //垂直
    public static final int VERTICAL_LIST = RecyclerView.VERTICAL;
    //水平+垂直
    public static final int BOTH_SET = 2;


    /**
     * 默认分割线：高度为2px，颜色为灰色
     *
     * @param context 上下文
     */
    public DividerItemDecoration(Context context) {
        //获取xml配置的参数
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        //typedArray.getDrawable(attr)这句是说我们可以通过我们的资源获得资源，使用我们的资源名attr去获得资源id
        //看不懂就用自己写一个分割线的图片吧，方法：ContextCompat.getDrawable(context, drawableId);
        mDrawable = a.getDrawable(0);
        //官方的解释是：回收TypedArray，以便后面重用。在调用这个函数后，你就不能再使用这个TypedArray。
        //在TypedArray后调用recycle主要是为了缓存。
        a.recycle();
    }

    /**
     * 默认分割线：高度为2px，颜色为灰色
     *
     * @param context     上下文
     * @param orientation 列表方向
     */
    public DividerItemDecoration(Context context, int orientation) {
        this.setOrientation(orientation);
        //获取xml配置的参数
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        //typedArray.getDrawable(attr)这句是说我们可以通过我们的资源获得资源，使用我们的资源名attr去获得资源id
        //看不懂就用自己写一个分割线的图片吧，方法：ContextCompat.getDrawable(context, drawableId);
        mDrawable = a.getDrawable(0);
        //官方的解释是：回收TypedArray，以便后面重用。在调用这个函数后，你就不能再使用这个TypedArray。
        //在TypedArray后调用recycle主要是为了缓存。
        a.recycle();
    }

    /**
     * 自定义分割线
     *
     * @param context     上下文
     * @param orientation 列表方向
     * @param drawableId  分割线图片
     */
    public DividerItemDecoration(Context context, int orientation, int drawableId) {
        this.setOrientation(orientation);
        //旧的getDrawable方法弃用了，这个是新的
        mDrawable = ContextCompat.getDrawable(context, drawableId);
        mDividerHeight = mDrawable.getIntrinsicHeight();
    }

    /**
     * 自定义分割线
     *
     * @param context       上下文
     * @param orientation   列表方向
     * @param dividerHeight 分割线高度
     * @param dividerColor  分割线颜色
     */
    public DividerItemDecoration(Context context, int orientation,
                                 int dividerHeight, int dividerColor) {
        this.setOrientation(orientation);
        mDividerHeight = dividerHeight;
        Log.e("mDividerHeight", mDividerHeight + "===================");
        //抗锯齿画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(dividerColor);
        //填满颜色
        mPaint.setStyle(Paint.Style.FILL);
    }

    /**
     * 设置方向
     *
     * @param orientation
     */
    public void setOrientation(int orientation) {
        if (orientation < 0 || orientation > 2)
            throw new IllegalArgumentException("invalid orientation");
        mOrientation = orientation;
    }


    /**
     * 绘制分割线之后,需要留出一个外边框,就是说item之间的间距要换一下
     *
     * @param outRect outRect.set(0, 0, 0, 0);的四个参数理解成margin就好了
     * @param view    视图
     * @param parent  父级view
     * @param state
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //下面super...代码其实调用的就是那个过时的getItemOffsets,也就是说这个方法体内容也可以通通移到那个过时的getItemOffsets中
        super.getItemOffsets(outRect, view, parent, state);
        //获取layoutParams参数
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        //当前位置
        int itemPosition = layoutParams.getViewLayoutPosition();
        //ItemView数量
        int childCount = parent.getAdapter().getItemCount();

        //RecyclerView.LayoutManager manager = parent.getLayoutManager();
        //if (manager instanceof GridLayoutManager || manager instanceof StaggeredGridLayoutManager) {
        //    //获取Layout的相关参数
        //    int spanCount = this.getSpanCount(parent);
        //    if (isLastRaw(parent, itemPosition, spanCount, childCount)) {
        //        // 如果是最后一行，则不需要绘制底部
        //        outRect.set(0, 0, mDividerHeight, 0);
        //    } else if (isLastColum(parent, itemPosition, spanCount, childCount)) {
        //        // 如果是最后一列，则不需要绘制右边
        //        outRect.set(0, 0, 0, mDividerHeight);
        //    } else {
        //        outRect.set(0, 0, mDividerHeight, mDividerHeight);
        //    }
        //} else if (manager instanceof LinearLayoutManager) {
        //    LinearLayoutManager layoutManager = (LinearLayoutManager) manager;
        //    if (layoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL) {
        //        childCount -= 1;
        //        //垂直布局底部留边，最后一行不留
        //        outRect.set(0, 0, 0, (itemPosition != childCount) ? mDividerHeight : 0);
        //    } else {
        //        childCount -= 1;
        //        //水平布局右侧留Margin,如果是最后一列,就不要留Margin了
        //        outRect.set(0, 0, (itemPosition != childCount) ? mDividerHeight : 0, 0);
        //    }
        //}

        switch (mOrientation) {
            case BOTH_SET:
                //获取Layout的相关参数
                int spanCount = this.getSpanCount(parent);
                if (isLastRaw(parent, itemPosition, spanCount, childCount)) {
                    // 如果是最后一行，则不需要绘制底部
                    outRect.set(0, 0, mDividerHeight, 0);
                } else if (isLastColum(parent, itemPosition, spanCount, childCount)) {
                    // 如果是最后一列，则不需要绘制右边
                    outRect.set(0, 0, 0, mDividerHeight);
                } else {
                    outRect.set(0, 0, mDividerHeight, mDividerHeight);
                }
                break;
            case HORIZONTAL_LIST:
                //水平布局右侧留Margin,如果是最后一列,就不要留Margin了
                outRect.set(0, 0, (itemPosition != childCount - 1) ? mDividerHeight : 0, 0);
                break;
            case VERTICAL_LIST:
                //垂直布局底部留边，最后一行不留
                outRect.set(0, 0, 0, (itemPosition != childCount - 1) ? mDividerHeight : 0);
                break;
        }
    }

    /**
     * 绘制分割线
     *
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        //RecyclerView.LayoutManager manager = parent.getLayoutManager();
        //if (manager instanceof GridLayoutManager || manager instanceof StaggeredGridLayoutManager) {
        //    //drawHorizontal(c, parent);
        //    //drawVertical(c, parent);
        //    drawGird(c, parent);
        //} else if (manager instanceof LinearLayoutManager) {
        //    LinearLayoutManager layoutManager = (LinearLayoutManager) manager;
        //    if (layoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL) {
        //        drawHorizontal(c, parent);
        //    } else {
        //        drawVertical(c, parent);
        //    }
        //}

        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else if (mOrientation == HORIZONTAL_LIST) {
            drawHorizontal(c, parent);
        } else {
            drawHorizontal(c, parent);
            drawVertical(c, parent);
            //    drawGird(c, parent);

        }
    }

    //绘制item分割线
    private void drawGird(Canvas canvas, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            boolean isLastRow = isLastRaw(parent, i, getSpanCount(parent), childCount);
            //画水平分隔线
            int left = child.getLeft();
            int right = child.getRight();
            int top = child.getBottom() + layoutParams.bottomMargin;
            int bottom = top + mDividerHeight;
            if (isLastRow) bottom = top;
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
            //画垂直分割线
            top = child.getTop();
            bottom = child.getBottom() + mDividerHeight;
            left = child.getRight() + layoutParams.rightMargin;
            right = left + mDividerHeight;
            if (isLastRow) bottom = child.getBottom();
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    /**
     * 绘制横向 item 分割线
     *
     * @param canvas 画布
     * @param parent 父容器
     */
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        //getChildCount()(ViewGroup.getChildCount) 返回的是显示层面上的“所包含的子 View 个数”。
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams =
                    (RecyclerView.LayoutParams) child.getLayoutParams();
            //item底部的Y轴坐标+margin值
            final int top = child.getBottom() + layoutParams.bottomMargin;
            final int bottom = top + mDividerHeight;
            Log.e("height", bottom + "===================");
            if (mDrawable != null) {
                //setBounds(x,y,width,height); x:组件在容器X轴上的起点 y:组件在容器Y轴上的起点
                // width:组件的长度 height:组件的高度
                mDrawable.setBounds(left, top, right, bottom);
                mDrawable.draw(canvas);
            }
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    /**
     * 绘制纵向 item 分割线
     *
     * @param canvas
     * @param parent
     */
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;
            final int right = left + mDividerHeight;
            if (mDrawable != null) {
                mDrawable.setBounds(left, top, right, bottom);
                mDrawable.draw(canvas);
            }
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }


    /**
     * 获取列数
     *
     * @param parent
     * @return
     */
    private int getSpanCount(RecyclerView parent) {
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager)
                    .getSpanCount();
        }
        return spanCount;
    }


    private boolean isLastColum(RecyclerView parent, int pos, int spanCount,
                                int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int orientation = ((GridLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                // 如果是最后一列，则不需要绘制右边
                if ((pos + 1) % spanCount == 0)
                    return true;
            } else {
                childCount = childCount - childCount % spanCount;
                // 如果是最后一列，则不需要绘制右边
                if (pos >= childCount)
                    return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                // 如果是最后一列，则不需要绘制右边
                if ((pos + 1) % spanCount == 0)
                    return true;
            } else {
                childCount = childCount - childCount % spanCount;
                // 如果是最后一列，则不需要绘制右边
                if (pos >= childCount)
                    return true;
            }
        } else {
            int orientation = ((LinearLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                // 如果是最后一列，则不需要绘制下边
                if ((pos + 1) % spanCount == 0)
                    return true;
            } else {
                // 如果是最后一列，则不需要绘制右边
                if (pos >= childCount)
                    return true;
            }

        }
        return false;
    }

    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount,
                              int childCount) {
        int orientation;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            childCount = childCount - childCount % spanCount;
            orientation = ((GridLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                // 如果是最后一行，则不需要绘制底部
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount)
                    return true;
            } else {// StaggeredGridLayoutManager 横向滚动
                // 如果是最后一行，则不需要绘制底部
                if ((pos + 1) % spanCount == 0)
                    return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                // 如果是最后一行，则不需要绘制底部
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount)
                    return true;
            } else {// StaggeredGridLayoutManager 横向滚动
                // 如果是最后一行，则不需要绘制底部
                if ((pos + 1) % spanCount == 0)
                    return true;
            }
        }
        return false;
    }
}

