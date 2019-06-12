package com.renj.recycler.draw;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2019-06-09   18:00
 * <p>
 * 描述：{@link GridLayoutManager} 类型的分割线
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class GridItemDecoration extends RecyclerItemDecoration {
    private int mVerticalDividerHeight = DEFAULT_DIVIDER_HEIGHT;   // 垂直方向上的宽度
    private int mVerticalDividerColor = DEFAULT_DIVIDER_COLOR;   // 垂直分割线颜色
    private Drawable mVerticalDividerDrawable;

    public GridItemDecoration(@RecyclerView.Orientation int orientation) {
        super(orientation);
    }

    /**
     * 指定分割线高度，单位 px ；默认 1px
     *
     * @param dividerHeight 水平方向和垂直方向上的宽度相同
     * @return
     */
    @Override
    public GridItemDecoration dividerHeight(int dividerHeight) {
        super.dividerHeight(dividerHeight);
        mVerticalDividerHeight = dividerHeight;
        return this;
    }

    /**
     * 指定分割线高度，单位 px ；默认 1px
     *
     * @param horizontalDividerHeight 水平方向上的宽度
     * @param verticalDividerHeight   垂直方向的宽度
     * @return
     */
    public GridItemDecoration dividerHeight(int horizontalDividerHeight, int verticalDividerHeight) {
        mHorizontalDividerHeight = horizontalDividerHeight;
        mVerticalDividerHeight = verticalDividerHeight;
        return this;
    }

    /**
     * 指定分割线显色，默认 0xFFDDDDDD
     *
     * @param dividerColor 水平方向、垂直方向上和交叉点的颜色相同  交叉点默认使用水平方向上的颜色
     * @return
     */
    @Override
    public GridItemDecoration dividerColor(int dividerColor) {
        super.dividerColor(dividerColor);
        mVerticalDividerColor = dividerColor;
        return this;
    }

    /**
     * 指定分割线显色
     *
     * @param dividerDrawable 水平方向、垂直方向上和交叉点的颜色相同  交叉点默认使用水平方向上的 Drawable
     * @return
     */
    @Override
    public GridItemDecoration dividerDrawable(Drawable dividerDrawable) {
        super.dividerDrawable(dividerDrawable);
        mVerticalDividerDrawable = dividerDrawable;
        return this;
    }

    /**
     * 分别指定水平方向上和垂直方向上的分割线显色，交叉点的分割线的颜色与水平方向上的颜色一致  默认 0xFFDDDDDD
     *
     * @param horizontalDividerColor 水平方向上分割线的颜色，交叉点的分割线的颜色与水平方向上的颜色一致
     * @param verticalDividerColor   垂直方向上分割线的颜色
     * @return
     */
    @Override
    public GridItemDecoration dividerColor(int horizontalDividerColor, int verticalDividerColor) {
        super.dividerColor(horizontalDividerColor, horizontalDividerColor);
        mVerticalDividerColor = verticalDividerColor;
        return this;
    }

    /**
     * 分别指定水平方向上和垂直方向上的分割线显 Drawable，交叉点的分割线的颜色与水平方向上的颜色一致
     *
     * @param horizontalDividerDrawable 水平方向上分割线的 Drawable，交叉点的分割线的颜色与水平方向上的颜色一致
     * @param verticalDividerDrawable   垂直方向上分割线的 Drawable
     * @return
     */
    @Override
    public GridItemDecoration dividerDrawable(Drawable horizontalDividerDrawable, Drawable verticalDividerDrawable) {
        super.dividerDrawable(horizontalDividerDrawable, horizontalDividerDrawable);
        mVerticalDividerDrawable = verticalDividerDrawable;
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
    public GridItemDecoration dividerColor(int horizontalDividerColor, int verticalDividerColor, int crossPointColor) {
        mHorizontalDividerColor = horizontalDividerColor;
        mVerticalDividerColor = verticalDividerColor;
        mCrossPointColor = crossPointColor;
        return this;
    }

    /**
     * 分别指定水平方向上、垂直方向上以及交叉点的分割线显色
     *
     * @param horizontalDividerDrawable 水平方向上分割线的 Drawable
     * @param verticalDividerDrawable   垂直方向上分割线的 Drawable
     * @param crossPointDrawable        交叉点的分割线的 Drawable
     * @return
     */
    public GridItemDecoration dividerDrawable(Drawable horizontalDividerDrawable, Drawable verticalDividerDrawable, Drawable crossPointDrawable) {
        mHorizontalDividerDrawable = horizontalDividerDrawable;
        mVerticalDividerDrawable = verticalDividerDrawable;
        mCrossPointDrawable = crossPointDrawable;
        return this;
    }

    @Override
    protected void itemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager1 = parent.getLayoutManager();
        if (!(layoutManager1 instanceof GridLayoutManager))
            throw new IllegalStateException("The RecyclerView.LayoutManager is not GridLayoutManager.");

        GridLayoutManager layoutManager = (GridLayoutManager) layoutManager1;

        GridLayoutManager.SpanSizeLookup spanSizeLookup = layoutManager.getSpanSizeLookup();
        int viewLayoutPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        int itemCount = parent.getAdapter().getItemCount();
        int spanCount = layoutManager.getSpanCount();
        int spanSize = spanSizeLookup.getSpanSize(viewLayoutPosition);
        int spanIndex = spanSizeLookup.getSpanIndex(viewLayoutPosition, spanCount);

        boolean firstCol = isFirstCol(layoutManager, viewLayoutPosition);
        boolean firstRow = isFirstRow(layoutManager, viewLayoutPosition);
        boolean lastCol = isLastCol(layoutManager, itemCount, viewLayoutPosition);
        boolean lastRow = isLastRow(layoutManager, itemCount, viewLayoutPosition);

        boolean isDrawFirstCol = firstCol && mIsDrawFirstCol;
        boolean isDrawFirstRow = firstRow && mIsDrawFirstRow;

        if (mOrientation == GridLayoutManager.VERTICAL) {
            if (mIsDrawFirstCol && mIsDrawLastCol) {
                // 第一列和最后一列都绘制
                int allDividerWidth = (spanCount - 1) * mVerticalDividerHeight + mLeftAndRightColHeight + mLeftAndRightColHeight;
                verticalOutRect(outRect, spanCount, spanSize, spanIndex, lastRow, isDrawFirstRow, allDividerWidth, lastCol);
            } else if (mIsDrawFirstCol || mIsDrawLastCol) {
                // 第一列绘制，或者最后一列绘制
                int allDividerWidth = (spanCount - 1) * mVerticalDividerHeight + mLeftAndRightColHeight;
                verticalOutRect(outRect, spanCount, spanSize, spanIndex, lastRow, isDrawFirstRow, allDividerWidth, lastCol);
            } else {
                // 第一列和最后一列都不绘制
                int allDividerWidth = (spanCount - 1) * mVerticalDividerHeight;
                verticalOutRect(outRect, spanCount, spanSize, spanIndex, lastRow, isDrawFirstRow, allDividerWidth, lastCol);
            }
        } else {
            if (mIsDrawFirstRow && mIsDrawLastRow) {
                // 第一列和最后一列都绘制
                int allDividerWidth = (spanCount - 1) * mHorizontalDividerHeight + mTopAndBottomRowHeight + mTopAndBottomRowHeight;
                horizontalOutRect(outRect, spanCount, spanSize, spanIndex, lastCol, lastRow, isDrawFirstCol, allDividerWidth);

            } else if (mIsDrawFirstRow || mIsDrawLastRow) {
                // 第一列绘制，或者最后一列绘制
                int allDividerWidth = (spanCount - 1) * mHorizontalDividerHeight + mTopAndBottomRowHeight;
                horizontalOutRect(outRect, spanCount, spanSize, spanIndex, lastCol, lastRow, isDrawFirstCol, allDividerWidth);
            } else {
                // 第一列和最后一列都不绘制
                int allDividerWidth = (spanCount - 1) * mHorizontalDividerHeight;
                horizontalOutRect(outRect, spanCount, spanSize, spanIndex, lastCol, lastRow, isDrawFirstCol, allDividerWidth);
            }
        }

    }

    private void horizontalOutRect(Rect outRect, int spanCount, int spanSize, int spanIndex, boolean lastCol, boolean lastRow, boolean isDrawFirstCol, int allDividerWidth) {
        // 计算每个item需要移动的宽度
        int itemDividerWidth = allDividerWidth / spanCount;
        int left = isDrawFirstCol ? mLeftAndRightColHeight : 0;
        int right = lastCol ? (mIsDrawLastCol ? mLeftAndRightColHeight : 0) : mVerticalDividerHeight;

        int top = spanIndex * (mHorizontalDividerHeight - itemDividerWidth) + (mIsDrawFirstRow ? mTopAndBottomRowHeight : 0);
        int bottom = itemDividerWidth - top;
        // 主要是对 调用了 GridLayoutManager#setSpanSizeLookup(SpanSizeLookup) 方法的 GridLayoutManager 进行处理
        if (spanSize != 1)
            bottom = itemDividerWidth - ((spanIndex + spanSize - 1) * (mHorizontalDividerHeight - itemDividerWidth) + (mIsDrawFirstRow ? mTopAndBottomRowHeight : 0));
        if (lastRow) {
            if (mIsDrawLastRow)
                bottom = mTopAndBottomRowHeight;
            else
                bottom = 0;
        }
        outRect.set(left, top, right, bottom);
    }

    private void verticalOutRect(Rect outRect, int spanCount, int spanSize, int spanIndex, boolean lastRow, boolean isDrawFirstRow, int allDividerWidth, boolean lastCol) {
        // 计算每个item需要移动的宽度
        int itemDividerWidth = allDividerWidth / spanCount;
        int left = spanIndex * (mVerticalDividerHeight - itemDividerWidth) + (mIsDrawFirstCol ? mLeftAndRightColHeight : 0);
        int right = itemDividerWidth - left;
        // 主要是对 调用了 GridLayoutManager#setSpanSizeLookup(SpanSizeLookup) 方法的 GridLayoutManager 进行处理
        if (spanSize != 1)
            right = itemDividerWidth - ((spanIndex + spanSize - 1) * (mVerticalDividerHeight - itemDividerWidth) + (mIsDrawFirstCol ? mLeftAndRightColHeight : 0));
        if (lastCol) {
            if (mIsDrawLastCol)
                right = mLeftAndRightColHeight;
            else
                right = 0;
        }
        int top = isDrawFirstRow ? mTopAndBottomRowHeight : 0;
        int bottom = lastRow ? (mIsDrawLastRow ? mTopAndBottomRowHeight : 0) : mHorizontalDividerHeight;
        outRect.set(left, top, right, bottom);
    }

    /**
     * 判断是否第一列
     */
    private boolean isFirstCol(GridLayoutManager layoutManager, int itemPosition) {
        int spanCount = layoutManager.getSpanCount();
        GridLayoutManager.SpanSizeLookup spanSizeLookup = layoutManager.getSpanSizeLookup();
        int spanIndex = spanSizeLookup.getSpanIndex(itemPosition, spanCount);
        int spanGroupIndex = spanSizeLookup.getSpanGroupIndex(itemPosition, spanCount);
        if (mOrientation == GridLayoutManager.VERTICAL) {
            return spanIndex == 0;
        } else {
            return spanGroupIndex == 0;
        }
    }

    /**
     * 判断是否第一行
     */
    private boolean isFirstRow(GridLayoutManager layoutManager, int itemPosition) {
        int spanCount = layoutManager.getSpanCount();
        GridLayoutManager.SpanSizeLookup spanSizeLookup = layoutManager.getSpanSizeLookup();
        int spanGroupIndex = layoutManager.getSpanSizeLookup().getSpanGroupIndex(itemPosition, spanCount);
        int spanIndex = spanSizeLookup.getSpanIndex(itemPosition, spanCount);

        if (mOrientation == GridLayoutManager.VERTICAL) {
            return spanGroupIndex == 0;
        } else {
            return spanIndex == 0;
        }
    }

    /**
     * 判断是否最后一列
     */
    private boolean isLastCol(GridLayoutManager layoutManager, int childCount, int itemPosition) {
        GridLayoutManager.SpanSizeLookup spanSizeLookup = layoutManager.getSpanSizeLookup();
        int spanCount = layoutManager.getSpanCount();
        int spanIndex = spanSizeLookup.getSpanIndex(itemPosition, spanCount);
        int spanSize = spanSizeLookup.getSpanSize(itemPosition);

        if (mOrientation == GridLayoutManager.VERTICAL) {
            return spanIndex + spanSize == spanCount;
        } else {
            return (childCount - itemPosition) / (spanCount * 1.0f) <= 1;
        }
    }

    /**
     * 判断是否最后一行
     */
    private boolean isLastRow(GridLayoutManager layoutManager, int childCount, int itemPosition) {
        GridLayoutManager.SpanSizeLookup spanSizeLookup = layoutManager.getSpanSizeLookup();
        int spanCount = layoutManager.getSpanCount();
        int spanIndex = spanSizeLookup.getSpanIndex(itemPosition, spanCount);
        int spanSize = spanSizeLookup.getSpanSize(itemPosition);

        if (mOrientation == GridLayoutManager.VERTICAL) {
            return (childCount - itemPosition) / (spanCount * 1.0f) <= 1;
        } else {
            return spanIndex + spanSize == spanCount;
        }
    }

    @Override
    protected void draw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        int childCountTotal = parent.getAdapter().getItemCount();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = parent.getChildAt(i);
            int viewLayoutPosition = ((RecyclerView.LayoutParams) childAt.getLayoutParams()).getViewLayoutPosition();
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childAt.getLayoutParams();

            // --------------------【start】 绘制第一行和第一列之前的线 【start】--------------------//
            boolean firstRow = isFirstRow(layoutManager, viewLayoutPosition);
            boolean firstCol = isFirstCol(layoutManager, viewLayoutPosition);

            if (firstRow) {
                // 第一行，并且需要绘制第一行之前的分割线
                int left = childAt.getLeft() - layoutParams.leftMargin;
                int right = childAt.getRight() + layoutParams.rightMargin;
                int top = childAt.getTop() - layoutParams.topMargin - mTopAndBottomRowHeight;
                int bottom = childAt.getTop() - layoutParams.topMargin;

                if (mFirstRowDrawable != null) {
                    mFirstRowDrawable.setBounds(left, top, right, bottom);
                    mFirstRowDrawable.draw(c);
                } else {
                    mPaint.setColor(mFirstRowColor);
                    c.drawRect(left, top, right, bottom, mPaint);
                }
            }

            if (firstCol) {
                // 第一列，并且需要绘制第一列之前的分割线
                int left = childAt.getLeft() - layoutParams.leftMargin - mLeftAndRightColHeight;
                int right = childAt.getLeft() - layoutParams.leftMargin;
                int top = childAt.getTop() - layoutParams.topMargin;
                int bottom = childAt.getBottom() + layoutParams.bottomMargin;

                if (mFirstColDrawable != null) {
                    mFirstColDrawable.setBounds(left, top, right, bottom);
                    mFirstColDrawable.draw(c);
                } else {
                    mPaint.setColor(mFirstColColor);
                    c.drawRect(left, top, right, bottom, mPaint);
                }
            }
            // ----------------------【end】 绘制第一行和第一列之前的线 【end】----------------------//


            // -----------【start】 绘制中间分割线以及最后一行和最后一列之后的线 【start】------------//
            /**
             * 在此处如果不在对最后一行或者最后一列进行判断的话，
             * 当在页面中给整个 RecyclerView 控件设置 padding 值时，依然会将最后一行的底部和最后一列的右边绘制出来
             */
            boolean lastRaw = isLastRow(layoutManager, childCountTotal, viewLayoutPosition);
            boolean lastCol = isLastCol(layoutManager, childCountTotal, viewLayoutPosition);

            if (!lastRaw) {
                // 不是最后一行，画水平方向的线
                int left = childAt.getLeft() - layoutParams.leftMargin;
                int right = childAt.getRight() + layoutParams.rightMargin;
                int top = childAt.getBottom() + layoutParams.bottomMargin;
                int bottom = top + mHorizontalDividerHeight;

                if (mHorizontalDividerDrawable != null) {
                    mHorizontalDividerDrawable.setBounds(left, top, right, bottom);
                    mHorizontalDividerDrawable.draw(c);
                } else {
                    mPaint.setColor(mHorizontalDividerColor);
                    c.drawRect(left, top, right, bottom, mPaint);
                }
            } else {
                // 是最后一行，判断是否需要绘制最后一行水平方向行的线
                if (mIsDrawLastRow) {
                    int left = childAt.getLeft() - layoutParams.leftMargin;
                    int right = childAt.getRight() + layoutParams.rightMargin;
                    int top = childAt.getBottom() + layoutParams.bottomMargin;
                    int bottom = top + mTopAndBottomRowHeight;

                    if (mLastRowDrawable != null) {
                        mLastRowDrawable.setBounds(left, top, right, bottom);
                        mLastRowDrawable.draw(c);
                    } else {
                        mPaint.setColor(mLastRowColor);
                        c.drawRect(left, top, right, bottom, mPaint);
                    }
                }
            }

            if (!lastCol) {
                // 不是最后一列，画竖直方向的线
                int left = childAt.getRight() + layoutParams.rightMargin;
                int right = left + mVerticalDividerHeight;
                int top = childAt.getTop() - layoutParams.topMargin;
                int bottom = childAt.getBottom() + layoutParams.bottomMargin;

                if (mVerticalDividerDrawable != null) {
                    mVerticalDividerDrawable.setBounds(left, top, right, bottom);
                    mVerticalDividerDrawable.draw(c);
                } else {
                    mPaint.setColor(mVerticalDividerColor);
                    c.drawRect(left, top, right, bottom, mPaint);
                }
            } else {
                // 是最后一列，判断是否需要绘制最后一列垂直方向行的线
                if (mIsDrawLastCol) {
                    int left = childAt.getRight() + layoutParams.rightMargin;
                    int right = left + mLeftAndRightColHeight;
                    int top = childAt.getTop() - layoutParams.topMargin;
                    int bottom = childAt.getBottom() + layoutParams.bottomMargin;

                    if (mLastColDrawable != null) {
                        mLastColDrawable.setBounds(left, top, right, bottom);
                        mLastColDrawable.draw(c);
                    } else {
                        mPaint.setColor(mLastColColor);
                        c.drawRect(left, top, right, bottom, mPaint);
                    }
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

                if (mCrossPointDrawable != null) {
                    mCrossPointDrawable.setBounds(left, top, right, bottom);
                    mCrossPointDrawable.draw(c);
                } else {
                    mPaint.setColor(mCrossPointColor);
                    c.drawRect(left, top, right, bottom, mPaint);
                }
            }

            // -----【start】 绘制除去四个角落(左上、左下、右上、右下)位置的交叉点 【start】--- //
            // 绘制第一行，但是不是最后一列，交叉点的位置需要绘制
            if (firstRow && !lastCol && mIsDrawFirstRow) {
                int left = childAt.getRight() + layoutParams.rightMargin;
                int right = left + mHorizontalDividerHeight;
                int top = childAt.getTop() - layoutParams.topMargin - mTopAndBottomRowHeight;
                int bottom = childAt.getTop() - layoutParams.topMargin;

                if (mBorderCrossPointDrawable != null) {
                    mBorderCrossPointDrawable.setBounds(left, top, right, bottom);
                    mBorderCrossPointDrawable.draw(c);
                } else if (DEFAULT_DIVIDER_COLOR != mBorderCrossPointColor) {
                    // 是否设置了边界交叉点位置颜色，没有设置就用第一行之前分割线的颜色
                    mPaint.setColor(mBorderCrossPointColor);
                    c.drawRect(left, top, right, bottom, mPaint);
                } else if (mFirstRowDrawable != null) {
                    mFirstRowDrawable.setBounds(left, top, right, bottom);
                    mFirstRowDrawable.draw(c);
                } else {
                    mPaint.setColor(mFirstRowColor);
                    c.drawRect(left, top, right, bottom, mPaint);
                }
            }

            // 绘制第一列，但是不是最后一行，交叉点的位置需要绘制
            if (firstCol && !lastRaw && mIsDrawFirstCol) {
                int left = childAt.getLeft() - layoutParams.rightMargin - mLeftAndRightColHeight;
                int right = childAt.getLeft() - layoutParams.rightMargin;
                int top = childAt.getBottom() + layoutParams.bottomMargin;
                int bottom = top + mHorizontalDividerHeight;

                if (mBorderCrossPointDrawable != null) {
                    mBorderCrossPointDrawable.setBounds(left, top, right, bottom);
                    mBorderCrossPointDrawable.draw(c);
                } else if (DEFAULT_DIVIDER_COLOR != mBorderCrossPointColor) {
                    mPaint.setColor(mBorderCrossPointColor);
                    c.drawRect(left, top, right, bottom, mPaint);
                } else if (mFirstColDrawable != null) {
                    mFirstColDrawable.setBounds(left, top, right, bottom);
                    mFirstColDrawable.draw(c);
                } else {
                    mPaint.setColor(mFirstColColor);
                    c.drawRect(left, top, right, bottom, mPaint);
                }
            }

            // 是最后一列，但不是最后一行，交叉点的位置需要绘制
            if (lastCol && !lastRaw && mIsDrawLastCol) {
                int left = childAt.getRight() + layoutParams.rightMargin;
                int right = left + mLeftAndRightColHeight;
                int top = childAt.getBottom() + layoutParams.bottomMargin;
                int bottom = top + mHorizontalDividerHeight;

                if (mBorderCrossPointDrawable != null) {
                    mBorderCrossPointDrawable.setBounds(left, top, right, bottom);
                    mBorderCrossPointDrawable.draw(c);
                } else if (DEFAULT_DIVIDER_COLOR != mBorderCrossPointColor) {
                    mPaint.setColor(mBorderCrossPointColor);
                    c.drawRect(left, top, right, bottom, mPaint);
                } else if (mLastColDrawable != null) {
                    mLastColDrawable.setBounds(left, top, right, bottom);
                    mLastColDrawable.draw(c);
                } else {
                    mPaint.setColor(mLastColColor);
                    c.drawRect(left, top, right, bottom, mPaint);
                }
            }

            // 是最后一行，但不是最后一列，交叉点的位置需要绘制
            if (lastRaw && !lastCol && mIsDrawLastRow) {
                int left = childAt.getRight() + layoutParams.rightMargin;
                int right = left + mHorizontalDividerHeight;
                int top = childAt.getBottom() + layoutParams.bottomMargin;
                int bottom = top + mTopAndBottomRowHeight;

                if (mBorderCrossPointDrawable != null) {
                    mBorderCrossPointDrawable.setBounds(left, top, right, bottom);
                    mBorderCrossPointDrawable.draw(c);
                } else if (DEFAULT_DIVIDER_COLOR != mBorderCrossPointColor) {
                    mPaint.setColor(mBorderCrossPointColor);
                    c.drawRect(left, top, right, bottom, mPaint);
                } else if (mLastRowDrawable != null) {
                    mLastRowDrawable.setBounds(left, top, right, bottom);
                    mLastRowDrawable.draw(c);
                } else {
                    mPaint.setColor(mLastRowColor);
                    c.drawRect(left, top, right, bottom, mPaint);
                }
            }

            // -----【end】 绘制除去四个角落(左上、左下、右上、右下)位置的交叉点 【end】--- //

            // -----【start】 绘制四个角落(左上、左下、右上、右下)位置的交叉点 【start】--- //

            // 左上角位置交叉点
            if (firstRow && firstCol && mIsDrawFirstRow && mIsDrawFirstCol) {
                int left = childAt.getLeft() - layoutParams.rightMargin - mLeftAndRightColHeight;
                int right = childAt.getLeft() - layoutParams.rightMargin;
                int top = childAt.getTop() - layoutParams.topMargin - mTopAndBottomRowHeight;
                int bottom = childAt.getTop() - layoutParams.topMargin;

                if (mBorderCrossPointDrawable != null) {
                    mBorderCrossPointDrawable.setBounds(left, top, right, bottom);
                    mBorderCrossPointDrawable.draw(c);
                } else if (DEFAULT_DIVIDER_COLOR != mBorderCrossPointColor) {
                    mPaint.setColor(mBorderCrossPointColor);
                    c.drawRect(left, top, right, bottom, mPaint);
                } else if (mFirstRowDrawable != null) {
                    mFirstRowDrawable.setBounds(left, top, right, bottom);
                    mFirstRowDrawable.draw(c);
                } else {
                    mPaint.setColor(mFirstRowColor);
                    c.drawRect(left, top, right, bottom, mPaint);
                }
            }

            // 右上角位置交叉点
            if (firstRow && lastCol && mIsDrawFirstRow && mIsDrawLastCol) {
                int left = childAt.getRight() + layoutParams.rightMargin;
                int right = left + mLeftAndRightColHeight;
                int top = childAt.getTop() - layoutParams.topMargin - mTopAndBottomRowHeight;
                int bottom = childAt.getTop() - layoutParams.topMargin;

                if (mBorderCrossPointDrawable != null) {
                    mBorderCrossPointDrawable.setBounds(left, top, right, bottom);
                    mBorderCrossPointDrawable.draw(c);
                } else if (DEFAULT_DIVIDER_COLOR != mBorderCrossPointColor) {
                    mPaint.setColor(mBorderCrossPointColor);
                    c.drawRect(left, top, right, bottom, mPaint);
                } else if (mFirstRowDrawable != null) {
                    mFirstRowDrawable.setBounds(left, top, right, bottom);
                    mFirstRowDrawable.draw(c);
                } else {
                    mPaint.setColor(mFirstRowColor);
                    c.drawRect(left, top, right, bottom, mPaint);
                }
            }

            // 右下角位置交叉点
            if (lastRaw && lastCol && mIsDrawLastRow && mIsDrawLastCol) {
                int left = childAt.getRight() + layoutParams.rightMargin;
                int right = left + mLeftAndRightColHeight;
                int top = childAt.getBottom() + layoutParams.bottomMargin;
                int bottom = top + mTopAndBottomRowHeight;

                if (mBorderCrossPointDrawable != null) {
                    mBorderCrossPointDrawable.setBounds(left, top, right, bottom);
                    mBorderCrossPointDrawable.draw(c);
                } else if (DEFAULT_DIVIDER_COLOR != mBorderCrossPointColor) {
                    mPaint.setColor(mBorderCrossPointColor);
                    c.drawRect(left, top, right, bottom, mPaint);
                } else if (mLastRowDrawable != null) {
                    mLastRowDrawable.setBounds(left, top, right, bottom);
                    mLastRowDrawable.draw(c);
                } else {
                    mPaint.setColor(mLastRowColor);
                    c.drawRect(left, top, right, bottom, mPaint);
                }
            }

            // 左下角位置交叉点
            if (lastRaw && firstCol && mIsDrawLastRow && mIsDrawFirstCol) {
                int left = childAt.getLeft() - layoutParams.rightMargin - mLeftAndRightColHeight;
                int right = childAt.getLeft() - layoutParams.rightMargin;
                int top = childAt.getBottom() + layoutParams.bottomMargin;
                int bottom = top + mTopAndBottomRowHeight;

                if (mBorderCrossPointDrawable != null) {
                    mBorderCrossPointDrawable.setBounds(left, top, right, bottom);
                    mBorderCrossPointDrawable.draw(c);
                } else if (DEFAULT_DIVIDER_COLOR != mBorderCrossPointColor) {
                    mPaint.setColor(mBorderCrossPointColor);
                    c.drawRect(left, top, right, bottom, mPaint);
                } else if (mLastRowDrawable != null) {
                    mLastRowDrawable.setBounds(left, top, right, bottom);
                    mLastRowDrawable.draw(c);
                } else {
                    mPaint.setColor(mLastRowColor);
                    c.drawRect(left, top, right, bottom, mPaint);
                }
            }

            // -----【end】 绘制四个角落(左上、左下、右上、右下)位置的交叉点 【end】--- //

            // --------------------【end】绘制水平方向和垂直方向交叉点的线 【end】-------------------//
        }
    }
}
