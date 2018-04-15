package com.android.recyclerviewtest.draw;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：20017-04-10    22:06
 * <p/>
 * 描述：给 RecyclerView 绘制分割线<br/><br/>
 * 提示：在 support 包的版本是 25或以上 时，系统提供了一个默认绘制分割线的实现 DividerItemDecoration，但是该实现只针对 LinearLayoutManager 。<br/><br/>
 * <b>说明：</b><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * <b>该类主要兼容 LinearLayoutManager 和 GridLayoutManager 类型的水平和垂直方向的分割线，<br/>
 * 而对于 StaggeredGridLayoutManager，因为有多列时 item 的位置发生变化(按顺序是最后一列的和其他列交换了位置)导致绘制结果不对，需要改进。</b><br/>
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
public class CustomItemDecoration extends RecyclerView.ItemDecoration {
    private static final int DEFAULT_DIVIDER_HEIGHT = 1; // 默认宽度
    private static final int DEFAULT_DIVIDER_COLOR = 0xFFDDDDDD; // 默认分割线颜色
    private Paint mPaint; // 定义画笔

    private int mHorizontalDividerHeight = DEFAULT_DIVIDER_HEIGHT; // 水平方向上的宽度
    private int mVerticalDividerHeight = DEFAULT_DIVIDER_HEIGHT;   // 垂直方向上的宽度

    private int mHorizontalDividerColor = DEFAULT_DIVIDER_COLOR; // 水平分割线颜色
    private int mVerticalDividerColor = DEFAULT_DIVIDER_COLOR;   // 垂直分割线颜色
    private int mCrossPointColor = mHorizontalDividerColor;   // 交叉点的分割线颜色，默认使用水平分割线的颜色

    private boolean mIsDrawLastLow = false; // 最后一行之后是否绘制分割线
    private boolean mIsDrawLastCol = false; // 最后一列之后是否绘制分割线
    private boolean mIsDrawFirstLow = false; // 第一行之前是否绘制分割线
    private boolean mIsDrawFirstCol = false; // 第一列之前是否绘制分割线

    private int mFirstLowHeight = DEFAULT_DIVIDER_HEIGHT; // 第一行之前的分割线高度
    private int mFirstColHeight = DEFAULT_DIVIDER_HEIGHT;   // 第一列之前的分割线宽度
    private int mLastLowHeight = DEFAULT_DIVIDER_HEIGHT;  // 最后一行之后的分割线高度
    private int mLastColHeight = DEFAULT_DIVIDER_HEIGHT;    // 最后一列之后的分割线宽度

    private int mLastLowColor = DEFAULT_DIVIDER_COLOR; // 最后一行之后分割线颜色
    private int mLastColColor = DEFAULT_DIVIDER_COLOR; // 最后一列之后分割线颜色
    private int mFirstLowColor = DEFAULT_DIVIDER_COLOR; // 第一行之前分割线颜色
    private int mFirstColColor = DEFAULT_DIVIDER_COLOR; // 第一列之前分割线颜色
    private int mBorderCrossPointColor = DEFAULT_DIVIDER_COLOR; // 四周分隔线交叉点颜色


    public CustomItemDecoration() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(DEFAULT_DIVIDER_COLOR);
    }

    /**
     * 指定分割线高度，单位 px ；默认 1px
     *
     * @param dividerHeight 水平方向和垂直方向上的宽度相同
     * @return
     */
    public CustomItemDecoration dividerHeight(int dividerHeight) {
        this.mHorizontalDividerHeight = dividerHeight;
        this.mVerticalDividerHeight = dividerHeight;
        setLowAndColHeight(dividerHeight, dividerHeight);
        return this;
    }

    /**
     * 指定分割线高度，单位 px ；默认 1px
     *
     * @param horizontalDividerHeight 水平方向上的宽度
     * @param verticalDividerHeight   垂直方向的宽度
     * @return
     */
    public CustomItemDecoration dividerHeight(int horizontalDividerHeight, int verticalDividerHeight) {
        this.mHorizontalDividerHeight = horizontalDividerHeight;
        this.mVerticalDividerHeight = verticalDividerHeight;
        setLowAndColHeight(horizontalDividerHeight, verticalDividerHeight);
        return this;
    }

    /**
     * 这个方法主要用于在设置水平方向和垂直方向时对第一行之前和最后一行之后、第一列之前和最后一列之后的高度进行设置，<br/>
     * 但是在设置之前需要先判断有没有单独为这几个位置设置过高度，因为单独设置的优先级更高
     *
     * @param lowHeight 行高度
     * @param colHeight 列高度
     */
    private void setLowAndColHeight(int lowHeight, int colHeight) {
        if (DEFAULT_DIVIDER_HEIGHT == mFirstLowHeight) mFirstLowHeight = lowHeight;
        if (DEFAULT_DIVIDER_HEIGHT == mLastLowHeight) mLastLowHeight = lowHeight;
        if (DEFAULT_DIVIDER_HEIGHT == mFirstColHeight) mFirstColHeight = colHeight;
        if (DEFAULT_DIVIDER_HEIGHT == mLastColHeight) mLastColHeight = colHeight;
    }

    /**
     * 设置在第一行之前是否需要绘制分割线
     *
     * @param isDrawFirstLow 是否绘制第一行之前分割线  默认 false
     * @return
     */
    public CustomItemDecoration isDrawFirstLowBefore(boolean isDrawFirstLow) {
        this.mIsDrawFirstLow = isDrawFirstLow;
        return this;
    }

    /**
     * 设置在第一行之前是否需要绘制分割线，并指定颜色
     *
     * @param isDrawFirstLow 是否绘制第一行之前分割线  默认 false
     * @param firstLowColor  分割线颜色
     * @return
     */
    public CustomItemDecoration isDrawFirstLowBeforeColor(boolean isDrawFirstLow, int firstLowColor) {
        this.mIsDrawFirstLow = isDrawFirstLow;
        this.mFirstLowColor = firstLowColor;
        return this;
    }

    /**
     * 设置在第一行之前是否需要绘制分割线，并指定高度
     *
     * @param isDrawFirstLow 是否绘制第一行之前分割线  默认 false
     * @param firstLowHeight 分割线高度
     * @return
     */
    public CustomItemDecoration isDrawFirstLowBeforeHeight(boolean isDrawFirstLow, int firstLowHeight) {
        this.mIsDrawFirstLow = isDrawFirstLow;
        this.mFirstLowHeight = firstLowHeight;
        return this;
    }

    /**
     * 设置在第一行之前是否需要绘制分割线，并指定颜色和高度
     *
     * @param isDrawFirstLow 是否绘制第一行之前分割线  默认 false
     * @param firstLowColor  分割线颜色
     * @param firstLowHeight 分割线高度
     * @return
     */
    public CustomItemDecoration isDrawFirstLowBefore(boolean isDrawFirstLow, int firstLowColor, int firstLowHeight) {
        this.mIsDrawFirstLow = isDrawFirstLow;
        this.mFirstLowColor = firstLowColor;
        this.mFirstLowHeight = firstLowHeight;
        return this;
    }

    /**
     * 设置在第一列之前是否需要绘制分割线
     *
     * @param isDrawFirstCol 是否绘制第一列之前分割线  默认 false
     * @return
     */
    public CustomItemDecoration isDrawFirstColBefore(boolean isDrawFirstCol) {
        this.mIsDrawFirstCol = isDrawFirstCol;
        return this;
    }

    /**
     * 设置在第一列之前是否需要绘制分割线，并指定颜色
     *
     * @param isDrawFirstCol 是否绘制第一列之前分割线  默认 false
     * @param firstColColor  分割线颜色
     * @return
     */
    public CustomItemDecoration isDrawFirstColBeforeColor(boolean isDrawFirstCol, int firstColColor) {
        this.mIsDrawFirstCol = isDrawFirstCol;
        this.mFirstColColor = firstColColor;
        return this;
    }

    /**
     * 设置在第一列之前是否需要绘制分割线，并指定高度
     *
     * @param isDrawFirstCol 是否绘制第一列之前分割线  默认 false
     * @param firstColHeight 分割线高度
     * @return
     */
    public CustomItemDecoration isDrawFirstColBeforeHeight(boolean isDrawFirstCol, int firstColHeight) {
        this.mIsDrawFirstCol = isDrawFirstCol;
        this.mFirstColHeight = firstColHeight;
        return this;
    }

    /**
     * 设置在第一列之前是否需要绘制分割线，并指定颜色和高度
     *
     * @param isDrawFirstCol 是否绘制第一列之前分割线  默认 false
     * @param firstColColor  分割线颜色
     * @param firstColHeight 分割线高度
     * @return
     */
    public CustomItemDecoration isDrawFirstColBefore(boolean isDrawFirstCol, int firstColColor, int firstColHeight) {
        this.mIsDrawFirstCol = isDrawFirstCol;
        this.mFirstColColor = firstColColor;
        this.mFirstColHeight = firstColHeight;
        return this;
    }

    /**
     * 设置在最后一行之后是否需要绘制分割线
     *
     * @param isDrawLastLow 是否绘制最后一行之后分割线  默认 false
     * @return
     */
    public CustomItemDecoration isDrawLastLowAfter(boolean isDrawLastLow) {
        this.mIsDrawLastLow = isDrawLastLow;
        return this;
    }

    /**
     * 设置在最后一行之后是否需要绘制分割线，并指定颜色
     *
     * @param isDrawLastLow 是否绘制最后一行之后分割线  默认 false
     * @param lastLowColor  分割线颜色
     * @return
     */
    public CustomItemDecoration isDrawLastLowAfterColor(boolean isDrawLastLow, int lastLowColor) {
        this.mIsDrawLastLow = isDrawLastLow;
        this.mLastLowColor = lastLowColor;
        return this;
    }

    /**
     * 设置在最后一行之后是否需要绘制分割线，并指定高度
     *
     * @param isDrawLastLow 是否绘制最后一行之后分割线  默认 false
     * @param lastLowHeight 分割线高度
     * @return
     */
    public CustomItemDecoration isDrawLastLowAfterHeight(boolean isDrawLastLow, int lastLowHeight) {
        this.mIsDrawLastLow = isDrawLastLow;
        this.mLastLowHeight = lastLowHeight;
        return this;
    }

    /**
     * 设置在最后一行之后是否需要绘制分割线，并指定颜色和高度
     *
     * @param isDrawLastLow 是否绘制最后一行之后分割线  默认 false
     * @param lastLowColor  分割线颜色
     * @param lastLowHeight 分割线高度
     * @return
     */
    public CustomItemDecoration isDrawLastLowAfter(boolean isDrawLastLow, int lastLowColor, int lastLowHeight) {
        this.mIsDrawLastLow = isDrawLastLow;
        this.mLastLowColor = lastLowColor;
        this.mLastLowHeight = lastLowHeight;
        return this;
    }

    /**
     * 设置在最后一列之后是否需要绘制分割线
     *
     * @param isDrawLastCol 是否绘制最后一列之后分割线  默认 false
     * @return
     */
    public CustomItemDecoration isDrawLastColAfter(boolean isDrawLastCol) {
        this.mIsDrawLastCol = isDrawLastCol;
        return this;
    }

    /**
     * 设置在最后一列之后是否需要绘制分割线，并指定颜色
     *
     * @param isDrawLastCol 是否绘制最后一列之后分割线  默认 false
     * @param lastColColor  分割线颜色
     * @return
     */
    public CustomItemDecoration isDrawLastColAfterColor(boolean isDrawLastCol, int lastColColor) {
        this.mIsDrawLastCol = isDrawLastCol;
        this.mLastColColor = lastColColor;
        return this;
    }

    /**
     * 设置在最后一列之后是否需要绘制分割线，并指定颜色和高度
     *
     * @param isDrawLastCol 是否绘制最后一列之后分割线  默认 false
     * @param lastColHeight 分割线高度
     * @return
     */
    public CustomItemDecoration isDrawLastColAfterHeight(boolean isDrawLastCol, int lastColHeight) {
        this.mIsDrawLastCol = isDrawLastCol;
        this.mLastColHeight = lastColHeight;
        return this;
    }

    /**
     * 设置在最后一列之后是否需要绘制分割线，并指定颜色和高度
     *
     * @param isDrawLastCol 是否绘制最后一列之后分割线  默认 false
     * @param lastColColor  分割线颜色
     * @param lastColHeight 分割线高度
     * @return
     */
    public CustomItemDecoration isDrawLastColAfter(boolean isDrawLastCol, int lastColColor, int lastColHeight) {
        this.mIsDrawLastCol = isDrawLastCol;
        this.mLastColColor = lastColColor;
        this.mLastColHeight = lastColHeight;
        return this;
    }

    /**
     * 设置边界交叉点颜色(第一行之前、第一列之前、最后一行之后、最后一列之后组成的框的交叉点颜色)，<br/>
     * 如果没有指定，那么行的交叉点使用水平方向上的颜色，列的交叉点使用垂直方向上的颜色
     *
     * @param borderCrossPointColor 边界交叉点颜色
     * @return
     */
    public CustomItemDecoration borderCrossPointColor(int borderCrossPointColor) {
        this.mBorderCrossPointColor = borderCrossPointColor;
        return this;
    }

    /**
     * 指定分割线显色，默认 0xFFDDDDDD
     *
     * @param dividerColor 水平方向、垂直方向上和交叉点的颜色相同  交叉点默认使用水平方向上的颜色
     * @return
     */
    public CustomItemDecoration dividerColor(int dividerColor) {
        this.mHorizontalDividerColor = dividerColor;
        this.mVerticalDividerColor = dividerColor;
        this.mCrossPointColor = dividerColor;
        setLowAndColColor(dividerColor, dividerColor);
        return this;
    }

    /**
     * 分别指定水平方向上和垂直方向上的分割线显色，交叉点的分割线的颜色与水平方向上的颜色一致  默认 0xFFDDDDDD
     *
     * @param horizontalDividerColor 水平方向上分割线的颜色，交叉点的分割线的颜色与水平方向上的颜色一致
     * @param verticalDividerColor   垂直方向上分割线的颜色
     * @return
     */
    public CustomItemDecoration dividerColor(int horizontalDividerColor, int verticalDividerColor) {
        this.mHorizontalDividerColor = horizontalDividerColor;
        this.mVerticalDividerColor = verticalDividerColor;
        this.mCrossPointColor = horizontalDividerColor;
        setLowAndColColor(horizontalDividerColor, verticalDividerColor);
        return this;
    }

    /**
     * 分别指定水平方向上、垂直方向上以及交叉点的分割线显色，默认 0xFFDDDDDD
     *
     * @param horizontalDividerColor 水平方向上分割线的颜色
     * @param verticalDividerColor   垂直方向上分割线的颜色
     * @param crossPointColor        交叉点的分割线的颜色
     * @return
     */
    public CustomItemDecoration dividerColor(int horizontalDividerColor, int verticalDividerColor, int crossPointColor) {
        this.mHorizontalDividerColor = horizontalDividerColor;
        this.mVerticalDividerColor = verticalDividerColor;
        this.mCrossPointColor = crossPointColor;
        setLowAndColColor(horizontalDividerColor, verticalDividerColor);
        return this;
    }

    /**
     * 这个方法主要用于在设置水平方向和垂直方向时对第一行之前和最后一行之后、第一列之前和最后一列之后的颜色值进行设置，<br/>
     * 但是在设置之前需要先判断有没有单独为这几个位置设置过颜色，因为单独设置的优先级更高
     *
     * @param lowColor 行颜色
     * @param colColor 列颜色
     */
    private void setLowAndColColor(int lowColor, int colColor) {
        if (DEFAULT_DIVIDER_COLOR == mFirstLowColor) mFirstLowColor = lowColor;
        if (DEFAULT_DIVIDER_COLOR == mLastLowColor) mLastLowColor = lowColor;
        if (DEFAULT_DIVIDER_COLOR == mFirstColColor) mFirstColColor = colColor;
        if (DEFAULT_DIVIDER_COLOR == mLastColColor) mLastColColor = colColor;
    }

    // 设置偏移量
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int viewLayoutPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        int childCount = parent.getAdapter().getItemCount();

        // 判断是否为第一行和第一列
        boolean firstLow = isFirstLow(layoutManager, viewLayoutPosition);
        boolean firstCol = isFirstCol(layoutManager, viewLayoutPosition);
        // 判断是否最后一行和最后一列
        boolean lastRaw = isLastRaw(layoutManager, childCount, viewLayoutPosition);
        boolean lastCol = isLastCol(layoutManager, childCount, viewLayoutPosition);

        // 定义变量确定第一行和第一列之前是否需要绘制分割线
        boolean drawFirstLow = firstLow && mIsDrawFirstLow;
        boolean drawFirstCol = firstCol && mIsDrawFirstCol;

        if (lastRaw) {
            if (lastCol) {
                // 最后一行最后一列，判断是否绘制右边和底部
                outRect.set(drawFirstCol ? mFirstColHeight : 0, drawFirstLow ? mFirstLowHeight : 0,
                        mIsDrawLastCol ? mLastColHeight : 0, mIsDrawLastLow ? mLastLowHeight : 0);
            } else {
                // 最后一行但是不是最后一列，判断是否绘制底部，右边一定需要绘制
                outRect.set(drawFirstCol ? mFirstColHeight : 0, drawFirstLow ? mFirstLowHeight : 0,
                        mVerticalDividerHeight, mIsDrawLastLow ? mLastLowHeight : 0);
            }
        } else if (lastCol) {
            if (lastRaw) {
                // 最后一列最后一行，判断是否绘制右边和底部
                outRect.set(drawFirstCol ? mFirstColHeight : 0, drawFirstLow ? mFirstLowHeight : 0,
                        mIsDrawLastCol ? mLastColHeight : 0, mIsDrawLastLow ? mLastLowHeight : 0);
            } else {
                // 最后一列但是不是最后一行，判断是否绘制右边，底部一定需要绘制
                outRect.set(drawFirstCol ? mFirstColHeight : 0, drawFirstLow ? mFirstLowHeight : 0,
                        mIsDrawLastCol ? mLastColHeight : 0, mHorizontalDividerHeight);
            }
        } else {
            // 不是最后一行也不是最后一列，绘制右边和底部
            outRect.set(drawFirstCol ? mFirstColHeight : 0, drawFirstLow ? mFirstLowHeight : 0,
                    mVerticalDividerHeight, mHorizontalDividerHeight);
        }
    }

    /**
     * 判断是否第一列
     *
     * @param layoutManager
     * @param itemPosition
     * @return
     */
    private boolean isFirstCol(RecyclerView.LayoutManager layoutManager, int itemPosition) {
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            int spanCount = gridLayoutManager.getSpanCount();
            int orientation = gridLayoutManager.getOrientation();
            if (GridLayoutManager.VERTICAL == orientation) {
                return itemPosition % spanCount == 0;
            } else {
                return (itemPosition + 1) <= spanCount;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            int spanCount = staggeredGridLayoutManager.getSpanCount();
            int orientation = staggeredGridLayoutManager.getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                return itemPosition % spanCount == 0;
            } else {
                return (itemPosition + 1) <= spanCount;
            }
        } else {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            int orientation = linearLayoutManager.getOrientation();
            if (orientation == LinearLayoutManager.VERTICAL) {
                return itemPosition == 0;
            } else {
                return true;
            }
        }
    }

    /**
     * 判断是否第一行
     *
     * @param layoutManager
     * @param itemPosition
     * @return
     */
    private boolean isFirstLow(RecyclerView.LayoutManager layoutManager, int itemPosition) {
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            int spanCount = gridLayoutManager.getSpanCount();
            int orientation = gridLayoutManager.getOrientation();
            if (GridLayoutManager.VERTICAL == orientation) {
                return (itemPosition + 1) <= spanCount;
            } else {
                return itemPosition % spanCount == 0;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            int spanCount = staggeredGridLayoutManager.getSpanCount();
            int orientation = staggeredGridLayoutManager.getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                return (itemPosition + 1) <= spanCount;
            } else {
                return itemPosition % spanCount == 0;
            }
        } else {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            int orientation = linearLayoutManager.getOrientation();
            if (orientation == LinearLayoutManager.VERTICAL) {
                return itemPosition == 0;
            } else {
                return true;
            }
        }
    }

    /**
     * 判断是否最后一列
     *
     * @param layoutManager
     * @param childCount
     * @param itemPosition
     * @return
     */
    private boolean isLastCol(RecyclerView.LayoutManager layoutManager, int childCount, int itemPosition) {
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            int spanCount = gridLayoutManager.getSpanCount();
            int orientation = gridLayoutManager.getOrientation();
            if (GridLayoutManager.VERTICAL == orientation) {
                return (itemPosition + 1) % spanCount == 0;
            } else {
                int yu = childCount % spanCount;
                if (yu == 0) return itemPosition >= (childCount - spanCount);
                else return itemPosition >= (childCount - yu);
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            int spanCount = staggeredGridLayoutManager.getSpanCount();
            int orientation = staggeredGridLayoutManager.getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                return (itemPosition + 1) % spanCount == 0;
            } else {
                int yu = childCount % spanCount;
                if (yu == 0) return itemPosition >= (childCount - spanCount);
                else return itemPosition >= (childCount - yu);
            }
        } else {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            int orientation = linearLayoutManager.getOrientation();
            if (orientation == LinearLayoutManager.VERTICAL) {
                return true;
            } else {
                return (itemPosition + 1) == childCount;
            }
        }
    }

    /**
     * 判断是否最后一行
     *
     * @param layoutManager
     * @param childCount
     * @param itemPosition
     * @return
     */
    private boolean isLastRaw(RecyclerView.LayoutManager layoutManager, int childCount, int itemPosition) {
        // 注意：GridLayoutManager extends LinearLayoutManager 使用 instanceof 一定要先判断 GridLayoutManager
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            int orientation = gridLayoutManager.getOrientation();
            int spanCount = gridLayoutManager.getSpanCount();
            if (GridLayoutManager.VERTICAL == orientation) {
                int yu = childCount % spanCount;
                if (yu == 0) return itemPosition >= (childCount - spanCount);
                else return itemPosition >= (childCount - yu);
            } else {
                return (itemPosition + 1) % spanCount == 0;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            int orientation = staggeredGridLayoutManager.getOrientation();
            int spanCount = staggeredGridLayoutManager.getSpanCount();
            if (GridLayoutManager.VERTICAL == orientation) {
                int yu = childCount % spanCount;
                if (yu == 0) return itemPosition >= (childCount - spanCount);
                else return itemPosition >= (childCount - yu);
            } else {
                return (itemPosition + 1) % spanCount == 0;
            }
        } else {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            int orientation = linearLayoutManager.getOrientation();
            if (orientation == LinearLayoutManager.VERTICAL) {
                return (itemPosition + 1) == childCount;
            } else {
                return true;
            }
        }
    }

    // 在 item 绘制之前调用(就是绘制在 item 的底层) [和 onDrawOver() 方法二选一即可]
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int childCountTotal = parent.getAdapter().getItemCount();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = parent.getChildAt(i);
            int viewLayoutPosition = ((RecyclerView.LayoutParams) childAt.getLayoutParams()).getViewLayoutPosition();
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childAt.getLayoutParams();

            // --------------------【start】 绘制第一行和第一列之前的线 【start】--------------------//
            boolean firstLow = isFirstLow(layoutManager, viewLayoutPosition);
            boolean firstCol = isFirstCol(layoutManager, viewLayoutPosition);

            if (firstLow) {
                // 第一行，并且需要绘制第一行之前的分割线
                int left = childAt.getLeft() - layoutParams.leftMargin;
                int right = childAt.getRight() + layoutParams.rightMargin;
                int top = childAt.getTop() - layoutParams.topMargin - mFirstLowHeight;
                int bottom = childAt.getTop() - layoutParams.topMargin;
                mPaint.setColor(mFirstLowColor);
                c.drawRect(left, top, right, bottom, mPaint);
            }

            if (firstCol) {
                // 第一列，并且需要绘制第一列之前的分割线
                int left = childAt.getLeft() - layoutParams.leftMargin - mFirstColHeight;
                int right = childAt.getLeft() - layoutParams.leftMargin;
                int top = childAt.getTop() - layoutParams.topMargin;
                int bottom = childAt.getBottom() + layoutParams.bottomMargin;
                mPaint.setColor(mFirstColColor);
                c.drawRect(left, top, right, bottom, mPaint);
            }
            // ----------------------【end】 绘制第一行和第一列之前的线 【end】----------------------//


            // -----------【start】 绘制中间分割线以及最后一行和最后一列之后的线 【start】------------//
            /**
             * 在此处如果不在对最后一行或者最后一列进行判断的话，
             * 当在页面中给整个 RecyclerView 控件设置 padding 值时，依然会将最后一行的底部和最后一列的右边绘制出来
             */
            boolean lastRaw = isLastRaw(layoutManager, childCountTotal, viewLayoutPosition);
            boolean lastCol = isLastCol(layoutManager, childCountTotal, viewLayoutPosition);

            if (!lastRaw) {
                // 不是最后一行，画水平方向的线
                int left = childAt.getLeft() - layoutParams.leftMargin;
                int right = childAt.getRight() + layoutParams.rightMargin;
                int top = childAt.getBottom() + layoutParams.bottomMargin;
                int bottom = top + mHorizontalDividerHeight;
                mPaint.setColor(mHorizontalDividerColor);
                c.drawRect(left, top, right, bottom, mPaint);
            } else {
                // 是最后一行，判断是否需要绘制最后一行水平方向行的线
                if (mIsDrawLastLow) {
                    int left = childAt.getLeft() - layoutParams.leftMargin;
                    int right = childAt.getRight() + layoutParams.rightMargin;
                    int top = childAt.getBottom() + layoutParams.bottomMargin;
                    int bottom = top + mLastLowHeight;
                    mPaint.setColor(mLastLowColor);
                    c.drawRect(left, top, right, bottom, mPaint);
                }
            }

            if (!lastCol) {
                // 不是最后一列，画竖直方向的线
                int left = childAt.getRight() + layoutParams.rightMargin;
                int right = left + mVerticalDividerHeight;
                int top = childAt.getTop() - layoutParams.topMargin;
                int bottom = childAt.getBottom() + layoutParams.bottomMargin;
                mPaint.setColor(mVerticalDividerColor);
                c.drawRect(left, top, right, bottom, mPaint);
            } else {
                // 是最后一列，判断是否需要绘制最后一列垂直方向行的线
                if (mIsDrawLastCol) {
                    int left = childAt.getRight() + layoutParams.rightMargin;
                    int right = left + mLastColHeight;
                    int top = childAt.getTop() - layoutParams.topMargin;
                    int bottom = childAt.getBottom() + layoutParams.bottomMargin;
                    mPaint.setColor(mLastColColor);
                    c.drawRect(left, top, right, bottom, mPaint);
                }
            }
            // -------------【end】 绘制中间分割线以及最后一行和最后一列之后的线 【end】--------------//


            // -----------------【start】 绘制水平方向和垂直方向交叉点的线 【start】-----------------//

            // 画中间位置水平方向和竖直方向的线的交叉点的背景
            if (!lastRaw && !lastCol) {
                int left = childAt.getRight() + layoutParams.rightMargin;
                int right = left + mVerticalDividerHeight;
                int top = childAt.getBottom() + layoutParams.bottomMargin;
                int bottom = top + mHorizontalDividerHeight;
                mPaint.setColor(mCrossPointColor);
                c.drawRect(left, top, right, bottom, mPaint);
            }

            // -----【start】 绘制除去四个角落(左上、左下、右上、右下)位置的交叉点 【start】--- //
            // 绘制第一行，但是不是最后一列，交叉点的位置需要绘制
            if (firstLow && !lastCol && mIsDrawFirstLow) {
                int left = childAt.getRight() + layoutParams.rightMargin;
                int right = left + mVerticalDividerHeight;
                int top = childAt.getTop() - layoutParams.topMargin - mFirstLowHeight;
                int bottom = childAt.getTop() - layoutParams.topMargin;
                // 是否设置了边界交叉点位置颜色，没有设置就用第一行之前分割线的颜色
                mPaint.setColor(DEFAULT_DIVIDER_COLOR == mBorderCrossPointColor ? mFirstLowColor : mBorderCrossPointColor);
                c.drawRect(left, top, right, bottom, mPaint);
            }

            // 绘制第一列，但是不是最后一行，交叉点的位置需要绘制
            if (firstCol && !lastRaw && mIsDrawFirstCol) {
                int left = childAt.getLeft() - layoutParams.rightMargin - mFirstColHeight;
                int right = childAt.getLeft() - layoutParams.rightMargin;
                int top = childAt.getBottom() + layoutParams.bottomMargin;
                int bottom = top + mHorizontalDividerHeight;
                // 是否设置了边界交叉点位置颜色，没有设置就用第一列之前分割线的颜色
                mPaint.setColor(DEFAULT_DIVIDER_COLOR == mBorderCrossPointColor ? mFirstColColor : mBorderCrossPointColor);
                c.drawRect(left, top, right, bottom, mPaint);
            }

            // 是最后一列，但不是最后一行，交叉点的位置需要绘制
            if (lastCol && !lastRaw && mIsDrawLastCol) {
                int left = childAt.getRight() + layoutParams.rightMargin;
                int right = left + mLastColHeight;
                int top = childAt.getBottom() + layoutParams.bottomMargin;
                int bottom = top + mHorizontalDividerHeight;
                // 是否设置了边界交叉点位置颜色，没有设置就用最后一列之后分割线的颜色
                mPaint.setColor(DEFAULT_DIVIDER_COLOR == mBorderCrossPointColor ? mLastColColor : mBorderCrossPointColor);
                c.drawRect(left, top, right, bottom, mPaint);
            }

            // 是最后一行，但不是最后一列，交叉点的位置需要绘制
            if (lastRaw && !lastCol && mIsDrawLastLow) {
                int left = childAt.getRight() + layoutParams.rightMargin;
                int right = left + mVerticalDividerHeight;
                int top = childAt.getBottom() + layoutParams.bottomMargin;
                int bottom = top + mLastLowHeight;
                // 是否设置了边界交叉点位置颜色，没有设置就用最后一行之后分割线的颜色
                mPaint.setColor(DEFAULT_DIVIDER_COLOR == mBorderCrossPointColor ? mLastLowColor : mBorderCrossPointColor);
                c.drawRect(left, top, right, bottom, mPaint);
            }

            // -----【end】 绘制除去四个角落(左上、左下、右上、右下)位置的交叉点 【end】--- //

            // -----【start】 绘制四个角落(左上、左下、右上、右下)位置的交叉点 【start】--- //

            // 左上角位置交叉点
            if (firstLow && firstCol && mIsDrawFirstLow && mIsDrawFirstCol) {
                int left = childAt.getLeft() - layoutParams.rightMargin - mFirstColHeight;
                int right = childAt.getLeft() - layoutParams.rightMargin;
                int top = childAt.getTop() - layoutParams.topMargin - mFirstLowHeight;
                int bottom = childAt.getTop() - layoutParams.topMargin;
                // 是否设置了边界交叉点位置颜色，没有设置就用第一行之前分割线的颜色
                mPaint.setColor(DEFAULT_DIVIDER_COLOR == mBorderCrossPointColor ? mFirstLowColor : mBorderCrossPointColor);
                c.drawRect(left, top, right, bottom, mPaint);
            }

            // 右上角位置交叉点
            if (firstLow && lastCol && mIsDrawFirstLow && mIsDrawLastCol) {
                int left = childAt.getRight() + layoutParams.rightMargin;
                int right = left + mLastColHeight;
                int top = childAt.getTop() - layoutParams.topMargin - mFirstLowHeight;
                int bottom = childAt.getTop() - layoutParams.topMargin;
                // 是否设置了边界交叉点位置颜色，没有设置就用第一行之前分割线的颜色
                mPaint.setColor(DEFAULT_DIVIDER_COLOR == mBorderCrossPointColor ? mFirstLowColor : mBorderCrossPointColor);
                c.drawRect(left, top, right, bottom, mPaint);
            }

            // 右下角位置交叉点
            if (lastRaw && lastCol && mIsDrawLastLow && mIsDrawLastCol) {
                int left = childAt.getRight() + layoutParams.rightMargin;
                int right = left + mLastColHeight;
                int top = childAt.getBottom() + layoutParams.bottomMargin;
                int bottom = top + mLastLowHeight;
                // 是否设置了边界交叉点位置颜色，没有设置就用第一行之前分割线的颜色
                mPaint.setColor(DEFAULT_DIVIDER_COLOR == mBorderCrossPointColor ? mLastLowColor : mBorderCrossPointColor);
                c.drawRect(left, top, right, bottom, mPaint);
            }

            // 左下角位置交叉点
            if (lastRaw && firstCol && mIsDrawLastLow && mIsDrawFirstCol) {
                int left = childAt.getLeft() - layoutParams.rightMargin - mFirstColHeight;
                int right = childAt.getLeft() - layoutParams.rightMargin;
                int top = childAt.getBottom() + layoutParams.bottomMargin;
                int bottom = top + mLastLowHeight;
                // 是否设置了边界交叉点位置颜色，没有设置就用第一行之前分割线的颜色
                mPaint.setColor(DEFAULT_DIVIDER_COLOR == mBorderCrossPointColor ? mLastLowColor : mBorderCrossPointColor);
                c.drawRect(left, top, right, bottom, mPaint);
            }

            // -----【end】 绘制四个角落(左上、左下、右上、右下)位置的交叉点 【end】--- //

            // --------------------【end】绘制水平方向和垂直方向交叉点的线 【end】-------------------//
        }
    }

    // 在 item 绘制之后调用(就是绘制在 item 的上层) [和 onDraw() 方法二选一即可]
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }
}
