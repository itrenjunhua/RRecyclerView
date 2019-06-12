package com.renj.recycler.draw;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2019-06-09   17:34
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public abstract class RecyclerItemDecoration extends RecyclerView.ItemDecoration {
    protected static final int DEFAULT_DIVIDER_HEIGHT = 1; // 默认宽度
    protected static final int DEFAULT_DIVIDER_COLOR = 0xFFDDDDDD; // 默认分割线颜色
    protected int mOrientation;
    protected Paint mPaint; // 定义画笔

    protected int mHorizontalDividerHeight = DEFAULT_DIVIDER_HEIGHT; // 水平方向上的宽度
    protected int mHorizontalDividerColor = DEFAULT_DIVIDER_COLOR; // 水平分割线颜色
    protected Drawable mHorizontalDividerDrawable; // 水平分割线 Drawable
    protected int mCrossPointColor = mHorizontalDividerColor;   // 交叉点的分割线颜色，默认使用水平分割线的颜色
    protected Drawable mCrossPointDrawable; // 交叉点的分割线 Drawable，默认使用水平分割线的 Drawable

    protected boolean mIsDrawLastRow = false; // 最后一行之后是否绘制分割线
    protected boolean mIsDrawLastCol = false; // 最后一列之后是否绘制分割线
    protected boolean mIsDrawFirstRow = false; // 第一行之前是否绘制分割线
    protected boolean mIsDrawFirstCol = false; // 第一列之前是否绘制分割线

    protected int mTopAndBottomRowHeight = DEFAULT_DIVIDER_HEIGHT; // 第一行之前和最后一列之后的分割线高度
    protected int mLeftAndRightColHeight = DEFAULT_DIVIDER_HEIGHT;   // 第一列之前和最后一行之后的分割线宽度

    protected int mLastRowColor = DEFAULT_DIVIDER_COLOR; // 最后一行之后分割线颜色
    protected Drawable mLastRowDrawable;
    protected int mLastColColor = DEFAULT_DIVIDER_COLOR; // 最后一列之后分割线颜色
    protected Drawable mLastColDrawable;
    protected int mFirstRowColor = DEFAULT_DIVIDER_COLOR; // 第一行之前分割线颜色
    protected Drawable mFirstRowDrawable;
    protected int mFirstColColor = DEFAULT_DIVIDER_COLOR; // 第一列之前分割线颜色
    protected Drawable mFirstColDrawable;
    protected int mBorderCrossPointColor = DEFAULT_DIVIDER_COLOR; // 四周分隔线交叉点颜色
    protected Drawable mBorderCrossPointDrawable;


    public RecyclerItemDecoration(@RecyclerView.Orientation int orientation) {
        this.mOrientation = orientation;
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
    public <T extends RecyclerItemDecoration> T dividerHeight(int dividerHeight) {
        this.mHorizontalDividerHeight = dividerHeight;
        return (T) this;
    }

    /**
     * 指定分割线显色，默认 0xFFDDDDDD
     *
     * @param dividerColor 水平方向、垂直方向上和交叉点的颜色相同  交叉点默认使用水平方向上的颜色
     * @return
     */
    public <T extends RecyclerItemDecoration> T dividerColor(int dividerColor) {
        this.mHorizontalDividerColor = dividerColor;
        this.mCrossPointColor = dividerColor;
        return (T) this;
    }

    /**
     * 指定分割线显色，默认 0xFFDDDDDD
     *
     * @param dividerDrawable 水平方向、垂直方向上和交叉点的颜色相同  交叉点默认使用水平方向上的 Drawable
     * @return
     */
    public <T extends RecyclerItemDecoration> T dividerDrawable(Drawable dividerDrawable) {
        this.mHorizontalDividerDrawable = dividerDrawable;
        this.mCrossPointDrawable = dividerDrawable;
        return (T) this;
    }

    /**
     * 分别指定水平方向上、垂直方向上以及交叉点的分割线显色，默认 0xFFDDDDDD
     *
     * @param horizontalDividerColor 水平方向上分割线的颜色
     * @param crossPointColor        交叉点的分割线的颜色
     * @return
     */
    public <T extends RecyclerItemDecoration> T dividerColor(int horizontalDividerColor, int crossPointColor) {
        this.mHorizontalDividerColor = horizontalDividerColor;
        this.mCrossPointColor = crossPointColor;
        return (T) this;
    }

    /**
     * 分别指定水平方向上、垂直方向上以及交叉点的分割线显色
     *
     * @param horizontalDividerDrawable 水平方向上分割线的 Drawable
     * @param crossPointDrawable        交叉点的分割线的 Drawable
     * @return
     */
    public <T extends RecyclerItemDecoration> T dividerDrawable(Drawable horizontalDividerDrawable, Drawable crossPointDrawable) {
        this.mHorizontalDividerDrawable = horizontalDividerDrawable;
        this.mCrossPointDrawable = crossPointDrawable;
        return (T) this;
    }

    /**
     * 这个方法主要用于在设置水平方向和垂直方向时对第一行之前和最后一行之后、第一列之前和最后一列之后的高度进行设置<br/>
     * 若要生效(绘制分割线) 需要调用
     * {@link #drawFirstRowBefore(boolean, int)}、
     * {@link #drawFirstColBefore(boolean, int)}、
     * {@link #drawLastRowAfter(boolean, int)}、
     * {@link #drawLastColAfter(boolean, int)}等方法，并指定参数为 true
     *
     * @param lowHeight 第一行之前和最后一列之后的分割线高度
     * @param colHeight 第一列之前和最后一行之后的分割线宽度
     */
    public <T extends RecyclerItemDecoration> T dividerRowAndColHeight(int lowHeight, int colHeight) {
        mTopAndBottomRowHeight = lowHeight;
        mLeftAndRightColHeight = colHeight;
        return (T) this;
    }

    /**
     * 设置在第一行之前是否需要绘制分割线，并指定颜色
     *
     * @param isDrawFirstRow 是否绘制第一行之前分割线  默认 false
     * @param firstRowColor  分割线颜色
     * @return
     */
    public <T extends RecyclerItemDecoration> T drawFirstRowBefore(boolean isDrawFirstRow, int firstRowColor) {
        this.mIsDrawFirstRow = isDrawFirstRow;
        this.mFirstRowColor = firstRowColor;
        return (T) this;
    }

    /**
     * 设置在第一行之前是否需要绘制分割线，并指定颜色
     *
     * @param isDrawFirstRow   是否绘制第一行之前分割线  默认 false
     * @param firstRowDrawable 分割线 Drawable
     * @return
     */
    public <T extends RecyclerItemDecoration> T drawFirstRowBefore(boolean isDrawFirstRow, Drawable firstRowDrawable) {
        this.mIsDrawFirstRow = isDrawFirstRow;
        this.mFirstRowDrawable = firstRowDrawable;
        return (T) this;
    }

    /**
     * 设置在第一列之前是否需要绘制分割线，并指定颜色
     *
     * @param isDrawFirstCol 是否绘制第一列之前分割线  默认 false
     * @param firstColColor  分割线颜色
     * @return
     */
    public <T extends RecyclerItemDecoration> T drawFirstColBefore(boolean isDrawFirstCol, int firstColColor) {
        this.mIsDrawFirstCol = isDrawFirstCol;
        this.mFirstColColor = firstColColor;
        return (T) this;
    }

    /**
     * 设置在第一列之前是否需要绘制分割线，并指定颜色
     *
     * @param isDrawFirstCol   是否绘制第一列之前分割线  默认 false
     * @param firstColDrawable 分割线 Drawable
     * @return
     */
    public <T extends RecyclerItemDecoration> T drawFirstColBefore(boolean isDrawFirstCol, Drawable firstColDrawable) {
        this.mIsDrawFirstCol = isDrawFirstCol;
        this.mFirstColDrawable = firstColDrawable;
        return (T) this;
    }

    /**
     * 设置在最后一行之后是否需要绘制分割线，并指定颜色
     *
     * @param isDrawLastRow 是否绘制最后一行之后分割线  默认 false
     * @param lastRowColor  分割线颜色
     * @return
     */
    public <T extends RecyclerItemDecoration> T drawLastRowAfter(boolean isDrawLastRow, int lastRowColor) {
        this.mIsDrawLastRow = isDrawLastRow;
        this.mLastRowColor = lastRowColor;
        return (T) this;
    }

    /**
     * 设置在最后一行之后是否需要绘制分割线，并指定颜色
     *
     * @param isDrawLastRow   是否绘制最后一行之后分割线  默认 false
     * @param lastRowDrawable 分割线 Drawable
     * @return
     */
    public <T extends RecyclerItemDecoration> T drawLastRowAfter(boolean isDrawLastRow, Drawable lastRowDrawable) {
        this.mIsDrawLastRow = isDrawLastRow;
        this.mLastRowDrawable = lastRowDrawable;
        return (T) this;
    }

    /**
     * 设置在最后一列之后是否需要绘制分割线，并指定颜色
     *
     * @param isDrawLastCol 是否绘制最后一列之后分割线  默认 false
     * @param lastColColor  分割线颜色
     * @return
     */
    public <T extends RecyclerItemDecoration> T drawLastColAfter(boolean isDrawLastCol, int lastColColor) {
        this.mIsDrawLastCol = isDrawLastCol;
        this.mLastColColor = lastColColor;
        return (T) this;
    }

    /**
     * 设置在最后一列之后是否需要绘制分割线，并指定颜色
     *
     * @param isDrawLastCol   是否绘制最后一列之后分割线  默认 false
     * @param lastColDrawable 分割线 Drawable
     * @return
     */
    public <T extends RecyclerItemDecoration> T drawLastColAfter(boolean isDrawLastCol, Drawable lastColDrawable) {
        this.mIsDrawLastCol = isDrawLastCol;
        this.mLastColDrawable = lastColDrawable;
        return (T) this;
    }

    /**
     * 设置边界交叉点颜色(第一行之前、第一列之前、最后一行之后、最后一列之后组成的框的交叉点颜色)，<br/>
     * 如果没有指定，那么行的交叉点使用水平方向上的颜色，列的交叉点使用垂直方向上的颜色
     *
     * @param borderCrossPointColor 边界交叉点颜色
     * @return
     */
    public <T extends RecyclerItemDecoration> T borderCrossPointColor(int borderCrossPointColor) {
        this.mBorderCrossPointColor = borderCrossPointColor;
        return (T) this;
    }

    /**
     * 设置边界交叉点颜色(第一行之前、第一列之前、最后一行之后、最后一列之后组成的框的交叉点颜色)，<br/>
     * 如果没有指定，那么行的交叉点使用水平方向上的颜色，列的交叉点使用垂直方向上的颜色
     *
     * @param borderCrossPointDrawable 边界交叉点 Drawable
     * @return
     */
    public <T extends RecyclerItemDecoration> T borderCrossPointDrawable(Drawable borderCrossPointDrawable) {
        this.mBorderCrossPointDrawable = borderCrossPointDrawable;
        return (T) this;
    }

    // 设置偏移量
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        itemOffsets(outRect, view, parent, state);
    }


    // 在 item 绘制之前调用(就是绘制在 item 的底层) [和 onDrawOver() 方法二选一即可]
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int save = c.save();
        draw(c, parent, state);
        c.restoreToCount(save);
    }

    // 在 item 绘制之后调用(就是绘制在 item 的上层) [和 onDraw() 方法二选一即可]
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    /**
     * 设置 item 偏移量
     */
    protected abstract void itemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state);

    /**
     * 在 item 绘制之前调用(就是绘制在 item 的底层)
     */
    protected abstract void draw(Canvas c, RecyclerView parent, RecyclerView.State state);
}
