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
 * 创建时间：2019-06-09   18:09
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class LinearItemDecoration extends RecyclerItemDecoration {
    public LinearItemDecoration(int orientation) {
        super(orientation);
    }

    @Override
    protected void itemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

    }

    @Override
    protected void draw(Canvas c, RecyclerView parent, RecyclerView.State state) {

    }
}
