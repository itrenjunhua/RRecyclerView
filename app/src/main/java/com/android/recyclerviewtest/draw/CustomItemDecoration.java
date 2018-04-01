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
    private static final int DEFAULT_DIVIDER_HEIGHT = 1;
    private static final int DEFAULT_DIVIDER_COLOR = 0xFFDDDDDD;
    private Paint mPaint;
    private int mHorizontalDividerHeight = DEFAULT_DIVIDER_HEIGHT; // 水平方向上的宽度
    private int mVerticalDividerHeight = DEFAULT_DIVIDER_HEIGHT;   // 垂直方向上的宽度
    private boolean mIsDrawLastLow = false; // 最后一行是否绘制分割线
    private boolean mIsDrawLastCol = false; // 最后一列是否绘制分割线
    private boolean mIsDrawFastLow = false; // 第一列是否绘制分割线
    private boolean mIsDrawFastCol = false; // 第一列是否绘制分割线

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
        return this;
    }

    /**
     * 设置第一行和第一列是否需要绘制分割线
     *
     * @param isDrawFastLow 是否绘制第一行  默认 false
     * @param isDrawFastCol 是否绘制第一列  默认 false
     * @return
     */
    public CustomItemDecoration isDrawFastLowAndCol(boolean isDrawFastLow, boolean isDrawFastCol) {
        this.mIsDrawFastLow = isDrawFastLow;
        this.mIsDrawFastCol = isDrawFastCol;
        return this;
    }

    /**
     * 设置最后一行和最后一列是否需要绘制分割线
     *
     * @param isDrawLastLow 是否绘制最后一行  默认 false
     * @param isDrawLastCol 是否绘制最后一列  默认 false
     * @return
     */
    public CustomItemDecoration isDrawLastLowAndCol(boolean isDrawLastLow, boolean isDrawLastCol) {
        this.mIsDrawLastLow = isDrawLastLow;
        this.mIsDrawLastCol = isDrawLastCol;
        return this;
    }

    /**
     * 指定分割线显色，默认 0xFFDDDDDD
     *
     * @param color
     * @return
     */
    public CustomItemDecoration dividerColor(int color) {
        mPaint.setColor(color);
        return this;
    }

    // 设置偏移量
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int viewLayoutPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        int childCount = parent.getAdapter().getItemCount();

        boolean fastLow = isFastLow(layoutManager, viewLayoutPosition);
        boolean fastCol = isFastCol(layoutManager, viewLayoutPosition);

        boolean lastRaw = isLastRaw(layoutManager, childCount, viewLayoutPosition);
        boolean lastCol = isLastCol(layoutManager, childCount, viewLayoutPosition);

        boolean drawFastLow = fastLow && mIsDrawFastLow;
        boolean drawFastCol = fastCol && mIsDrawFastCol;
        if (lastRaw) {
            if (lastCol) {
                // 最后一行最后一列，判断是否绘制右边底部
                if (mIsDrawLastLow && mIsDrawLastCol) // 绘制右边和底部
                    outRect.set(drawFastCol ? mVerticalDividerHeight : 0, drawFastLow ? mHorizontalDividerHeight : 0, mVerticalDividerHeight, mHorizontalDividerHeight);
                else if (mIsDrawLastLow) // 绘制底部
                    outRect.set(drawFastCol ? mVerticalDividerHeight : 0, drawFastLow ? mHorizontalDividerHeight : 0, 0, mHorizontalDividerHeight);
                else if (mIsDrawLastCol) // 绘制右边
                    outRect.set(drawFastCol ? mVerticalDividerHeight : 0, drawFastLow ? mHorizontalDividerHeight : 0, mVerticalDividerHeight, 0);
                else
                    outRect.set(drawFastCol ? mVerticalDividerHeight : 0, drawFastLow ? mHorizontalDividerHeight : 0, 0, 0);
            } else {
                // 最后一行但是不是最后一列，判断是否绘制底部
                if (mIsDrawLastLow) // 绘制底部
                    outRect.set(drawFastCol ? mVerticalDividerHeight : 0, drawFastLow ? mHorizontalDividerHeight : 0, mVerticalDividerHeight, mHorizontalDividerHeight);
                else // 不绘制底部
                    outRect.set(drawFastCol ? mVerticalDividerHeight : 0, drawFastLow ? mHorizontalDividerHeight : 0, mVerticalDividerHeight, 0);
            }
        } else if (lastCol) {
            if (lastRaw) {
                // 最后一列最后一行，判断是否绘制右边底部
                if (mIsDrawLastLow && mIsDrawLastCol) // 绘制右边和底部
                    outRect.set(drawFastCol ? mVerticalDividerHeight : 0, drawFastLow ? mHorizontalDividerHeight : 0, mVerticalDividerHeight, mHorizontalDividerHeight);
                else if (mIsDrawLastLow) // 绘制底部
                    outRect.set(drawFastCol ? mVerticalDividerHeight : 0, drawFastLow ? mHorizontalDividerHeight : 0, 0, mHorizontalDividerHeight);
                else if (mIsDrawLastCol) // 绘制右边
                    outRect.set(drawFastCol ? mVerticalDividerHeight : 0, drawFastLow ? mHorizontalDividerHeight : 0, mVerticalDividerHeight, 0);
                else
                    outRect.set(drawFastCol ? mVerticalDividerHeight : 0, drawFastLow ? mHorizontalDividerHeight : 0, 0, 0);
            } else {
                // 最后一列但是不是最后一行，判断是否绘制右边
                if (mIsDrawLastCol) // 绘制右边
                    outRect.set(drawFastCol ? mVerticalDividerHeight : 0, drawFastLow ? mHorizontalDividerHeight : 0, mVerticalDividerHeight, mHorizontalDividerHeight);
                else // 不绘制右边
                    outRect.set(drawFastCol ? mVerticalDividerHeight : 0, drawFastLow ? mHorizontalDividerHeight : 0, 0, mHorizontalDividerHeight);
            }
        } else {
            // 不是最后一行也不是最后一列，绘制右边和底部
            outRect.set(drawFastCol ? mVerticalDividerHeight : 0, drawFastLow ? mHorizontalDividerHeight : 0, mVerticalDividerHeight, mHorizontalDividerHeight);
        }
    }

    /**
     * 判断是否第一列
     *
     * @param layoutManager
     * @param itemPosition
     * @return
     */
    private boolean isFastCol(RecyclerView.LayoutManager layoutManager, int itemPosition) {
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
    private boolean isFastLow(RecyclerView.LayoutManager layoutManager, int itemPosition) {
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

            boolean fastLow = isFastLow(layoutManager, viewLayoutPosition);
            boolean fastCol = isFastCol(layoutManager, viewLayoutPosition);

            if (fastLow) {
                // 第一行，并且需要绘制第一行之前的分割线
                int left = childAt.getLeft() - layoutParams.leftMargin;
                int right = childAt.getRight() + layoutParams.rightMargin;
                int top = childAt.getTop() - layoutParams.topMargin - mHorizontalDividerHeight;
                int bottom = childAt.getTop() - layoutParams.topMargin;
                c.drawRect(left, top, right, bottom, mPaint);
            }

            if (fastCol) {
                // 第一列，并且需要绘制第一列之前的分割线
                int left = childAt.getLeft() - layoutParams.leftMargin - mVerticalDividerHeight;
                int right = childAt.getLeft() - layoutParams.leftMargin;
                int top = childAt.getTop() - layoutParams.topMargin;
                int bottom = childAt.getBottom() + layoutParams.bottomMargin;
                c.drawRect(left, top, right, bottom, mPaint);
            }

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
                c.drawRect(left, top, right, bottom, mPaint);
            } else {
                // 是最后一行，判断是否需要绘制最后一行水平方向行的线
                if (mIsDrawLastLow) {
                    int left = childAt.getLeft() - layoutParams.leftMargin;
                    int right = childAt.getRight() + layoutParams.rightMargin;
                    int top = childAt.getBottom() + layoutParams.bottomMargin;
                    int bottom = top + mHorizontalDividerHeight;
                    c.drawRect(left, top, right, bottom, mPaint);
                }
            }

            if (!lastCol) {
                // 画竖直方向的线
                int left1 = childAt.getRight() + layoutParams.rightMargin;
                int right1 = left1 + mVerticalDividerHeight;
                int top1 = childAt.getTop() - layoutParams.topMargin;
                int bottom1 = childAt.getBottom() + layoutParams.bottomMargin;
                c.drawRect(left1, top1, right1, bottom1, mPaint);
            } else {
                // 是最后一列，判断是否需要绘制最后一列垂直方向行的线
                if (mIsDrawLastCol) {
                    int left1 = childAt.getRight() + layoutParams.rightMargin;
                    int right1 = left1 + mVerticalDividerHeight;
                    int top1 = childAt.getTop() - layoutParams.topMargin;
                    int bottom1 = childAt.getBottom() + layoutParams.bottomMargin;
                    c.drawRect(left1, top1, right1, bottom1, mPaint);
                }
            }

            if (!lastRaw && !lastCol) {
                // 画水平方向和竖直方向的线的交叉点的背景
                int left2 = childAt.getRight() + layoutParams.rightMargin;
                int right2 = left2 + mVerticalDividerHeight;
                int top2 = childAt.getBottom() + layoutParams.bottomMargin;
                int bottom2 = top2 + mHorizontalDividerHeight;
                c.drawRect(left2, top2, right2, bottom2, mPaint);
            }

            if (fastLow && mIsDrawFastLow) {
                // 绘制第一行时，交叉点的位置需要绘制
                int left2 = childAt.getRight() + layoutParams.rightMargin;
                int right2 = left2 + mVerticalDividerHeight;
                int top2 = childAt.getTop() - layoutParams.topMargin - mHorizontalDividerHeight;
                int bottom2 = childAt.getTop() - layoutParams.topMargin;
                c.drawRect(left2, top2, right2, bottom2, mPaint);
            }

            if (fastCol && mIsDrawFastCol) {
                // 绘制第一列时，交叉点的位置需要绘制
                int left2 = childAt.getLeft() - layoutParams.rightMargin - mVerticalDividerHeight;
                int right2 = childAt.getLeft() - layoutParams.rightMargin;
                int top2 = childAt.getBottom() + layoutParams.bottomMargin;
                int bottom2 = top2 + mHorizontalDividerHeight;
                c.drawRect(left2, top2, right2, bottom2, mPaint);
            }

            if (fastLow && fastCol && mIsDrawFastLow && mIsDrawFastCol) {
                // 解决第一行和第一列都需要绘制时，第一行第一列前面的位置不会绘制的问题
                int left2 = childAt.getLeft() - layoutParams.rightMargin - mVerticalDividerHeight;
                int right2 = childAt.getLeft() - layoutParams.rightMargin;
                int top2 = childAt.getTop() - layoutParams.topMargin - mHorizontalDividerHeight;
                int bottom2 = childAt.getTop() - layoutParams.topMargin;
                c.drawRect(left2, top2, right2, bottom2, mPaint);
            }

            if ((lastRaw && mIsDrawLastLow) || (lastCol && mIsDrawLastCol)) {
                // 只要最后一行和最后一列有一个需要绘制，那么就需要画水平方向和竖直方向的线的交叉点的背景
                int left2 = childAt.getRight() + layoutParams.rightMargin;
                int right2 = left2 + mVerticalDividerHeight;
                int top2 = childAt.getBottom() + layoutParams.bottomMargin;
                int bottom2 = top2 + mHorizontalDividerHeight;
                c.drawRect(left2, top2, right2, bottom2, mPaint);
            }
        }
    }

    // 在 item 绘制之后调用(就是绘制在 item 的上层) [和 onDraw() 方法二选一即可]
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }
}
