package com.android.recyclerviewtest.draw;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2019-06-09   18:00
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class GridItemDecoration extends RecyclerItemDecoration {
    private int mVerticalDividerHeight = DEFAULT_DIVIDER_HEIGHT;   // 垂直方向上的宽度
    private int mVerticalDividerColor = DEFAULT_DIVIDER_COLOR;   // 垂直分割线颜色

    public GridItemDecoration(int orientation) {
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
        mHorizontalDividerHeight = dividerHeight;
        mVerticalDividerHeight = dividerHeight;
        setRowAndColHeight(dividerHeight, dividerHeight);
        return this;
    }

    /**
     * 指定分割线高度，单位 px ；默认 1px
     *
     * @param horizontalDividerHeight 水平方向上的宽度
     * @param verticalDividerHeight   垂直方向的宽度
     * @return
     */
    @Override
    public GridItemDecoration dividerHeight(int horizontalDividerHeight, int verticalDividerHeight) {
        mHorizontalDividerHeight = horizontalDividerHeight;
        mVerticalDividerHeight = verticalDividerHeight;
        setRowAndColHeight(horizontalDividerHeight, verticalDividerHeight);
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
        mHorizontalDividerColor = dividerColor;
        mVerticalDividerColor = dividerColor;
        mCrossPointColor = dividerColor;
        setRowAndColColor(dividerColor, dividerColor);
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
        mHorizontalDividerColor = horizontalDividerColor;
        mVerticalDividerColor = verticalDividerColor;
        mCrossPointColor = horizontalDividerColor;
        setRowAndColColor(horizontalDividerColor, verticalDividerColor);
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
    @Override
    public GridItemDecoration dividerColor(int horizontalDividerColor, int verticalDividerColor, int crossPointColor) {
        mHorizontalDividerColor = horizontalDividerColor;
        mVerticalDividerColor = verticalDividerColor;
        mCrossPointColor = crossPointColor;
        setRowAndColColor(horizontalDividerColor, verticalDividerColor);
        return this;
    }

    @Override
    protected void itemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

    }

    @Override
    protected void draw(Canvas c, RecyclerView parent, RecyclerView.State state) {

    }
}
