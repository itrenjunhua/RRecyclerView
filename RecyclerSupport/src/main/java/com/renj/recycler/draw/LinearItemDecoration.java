package com.renj.recycler.draw;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2019-06-09   18:09
 * <p>
 * 描述：{@link LinearLayoutManager} 类型的分割线
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class LinearItemDecoration extends RecyclerItemDecoration {
    public LinearItemDecoration(@RecyclerView.Orientation int orientation) {
        super(orientation);
    }

    @Override
    protected void itemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (!(layoutManager instanceof LinearLayoutManager))
            throw new IllegalStateException("The RecyclerView.LayoutManager is not LinearLayoutManager.");

        int viewLayoutPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        int itemCount = parent.getAdapter().getItemCount();

        boolean firstCol = isFirstCol(viewLayoutPosition);
        boolean firstRow = isFirstRow(viewLayoutPosition);
        boolean lastCol = isLastCol(itemCount, viewLayoutPosition);
        boolean lastRow = isLastRow(itemCount, viewLayoutPosition);

        boolean isDrawFirstCol = firstCol && mIsDrawFirstCol;
        boolean isDrawFirstRow = firstRow && mIsDrawFirstRow;
        boolean isDrawLastRow = lastRow && mIsDrawLastRow;
        boolean isDrawLastCol = lastCol && mIsDrawLastCol;

        if (lastRow) {
            if (lastCol) {
                // 最后一行最后一列，判断是否绘制右边和底部
                outRect.set(isDrawFirstCol ? mLeftAndRightColHeight : 0, isDrawFirstRow ? mTopAndBottomRowHeight : 0,
                        isDrawLastCol ? mLeftAndRightColHeight : 0, isDrawLastRow ? mTopAndBottomRowHeight : 0);
            } else {
                // 最后一行但是不是最后一列，判断是否绘制底部，右边一定需要绘制
                outRect.set(isDrawFirstCol ? mLeftAndRightColHeight : 0, isDrawFirstRow ? mTopAndBottomRowHeight : 0,
                        mHorizontalDividerHeight, isDrawLastRow ? mTopAndBottomRowHeight : 0);
            }
        } else if (lastCol) {
            if (lastRow) {
                // 最后一列最后一行，判断是否绘制右边和底部
                outRect.set(isDrawFirstCol ? mLeftAndRightColHeight : 0, isDrawFirstRow ? mTopAndBottomRowHeight : 0,
                        isDrawLastCol ? mLeftAndRightColHeight : 0, isDrawLastRow ? mTopAndBottomRowHeight : 0);
            } else {
                // 最后一列但是不是最后一行，判断是否绘制右边，底部一定需要绘制
                outRect.set(isDrawFirstCol ? mLeftAndRightColHeight : 0, isDrawFirstRow ? mTopAndBottomRowHeight : 0,
                        isDrawLastCol ? mLeftAndRightColHeight : 0, mHorizontalDividerHeight);
            }
        } else {
            // 不是最后一行也不是最后一列，绘制右边和底部
            outRect.set(isDrawFirstCol ? mLeftAndRightColHeight : 0, isDrawFirstRow ? mTopAndBottomRowHeight : 0,
                    mHorizontalDividerHeight, mHorizontalDividerHeight);
        }
    }

    /**
     * 判断是否第一列
     */
    private boolean isFirstCol(int itemPosition) {
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            return true;
        } else {
            return itemPosition == 0;
        }
    }

    /**
     * 判断是否第一行
     */
    private boolean isFirstRow(int itemPosition) {
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            return itemPosition == 0;
        } else {
            return true;
        }
    }

    /**
     * 判断是否最后一列
     */
    private boolean isLastCol(int childCount, int itemPosition) {
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            return true;
        } else {
            return (itemPosition + 1) == childCount;
        }
    }

    /**
     * 判断是否最后一行
     */
    private boolean isLastRow(int childCount, int itemPosition) {
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            return (itemPosition + 1) == childCount;
        } else {
            return true;
        }
    }

    @Override
    protected void draw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCountTotal = parent.getAdapter().getItemCount();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = parent.getChildAt(i);
            int viewLayoutPosition = ((RecyclerView.LayoutParams) childAt.getLayoutParams()).getViewLayoutPosition();
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childAt.getLayoutParams();

            // --------------------【start】 绘制第一行和第一列之前的线 【start】--------------------//
            boolean firstRow = isFirstRow(viewLayoutPosition);
            boolean firstCol = isFirstCol(viewLayoutPosition);

            if (firstRow && mIsDrawFirstRow) {
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

            if (firstCol && mIsDrawFirstCol) {
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
            boolean lastRaw = isLastRow(childCountTotal, viewLayoutPosition);
            boolean lastCol = isLastCol(childCountTotal, viewLayoutPosition);

            if (lastRaw && mIsDrawLastRow) {
                // 是最后一行，并且需要绘制最后一行水平方向行的线
                int left = childAt.getLeft() - layoutParams.leftMargin;
                int right = childAt.getRight() + layoutParams.rightMargin;
                int top = childAt.getBottom() + layoutParams.bottomMargin;
                int bottom = top + mTopAndBottomRowHeight;

                if (mLastColDrawable != null) {
                    mLastColDrawable.setBounds(left, top, right, bottom);
                    mLastColDrawable.draw(c);
                } else {
                    mPaint.setColor(mLastRowColor);
                    c.drawRect(left, top, right, bottom, mPaint);
                }
            } else {
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
            }

            if (lastCol && mIsDrawLastCol) {
                // 是最后一列，并且需要绘制最后一列垂直方向行的线
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
            } else {
                // 不是最后一列，画竖直方向的线
                int left = childAt.getRight() + layoutParams.rightMargin;
                int right = left + mHorizontalDividerHeight;
                int top = childAt.getTop() - layoutParams.topMargin;
                int bottom = childAt.getBottom() + layoutParams.bottomMargin;

                if (mHorizontalDividerDrawable != null) {
                    mHorizontalDividerDrawable.setBounds(left, top, right, bottom);
                    mHorizontalDividerDrawable.draw(c);
                } else {
                    mPaint.setColor(mHorizontalDividerColor);
                    c.drawRect(left, top, right, bottom, mPaint);
                }
            }
            // -------------【end】 绘制中间分割线以及最后一行和最后一列之后的线 【end】--------------//


            // -----------------【start】 绘制水平方向和垂直方向交叉点的线 【start】-----------------//

            // 画中间位置水平方向和竖直方向的线的交叉点的背景
            if (!lastRaw && !lastCol) {
                int left = childAt.getRight() + layoutParams.rightMargin;
                int right = left + mHorizontalDividerHeight;
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
