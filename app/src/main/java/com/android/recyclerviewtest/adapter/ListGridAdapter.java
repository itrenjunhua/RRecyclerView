package com.android.recyclerviewtest.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.recyclerviewtest.R;

import java.util.List;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2017-04-05    14:55
 * <p/>
 * 描述：LinearLayoutManager 和 GridLayoutManager 共用的 Adapter
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
public class ListGridAdapter extends MultipleTypeAdapter<String> {
    private int mOrientation = LinearLayoutManager.VERTICAL;

    public ListGridAdapter(Context context, List<String> datas) {
        super(context, datas);
    }

    public ListGridAdapter(Context context, List<String> datas, int orientation) {
        this(context, datas);
        this.mOrientation = orientation;
    }

    @Override
    public int itemViewType(int position) {
        return 0;
    }

    @Override
    public CustomViewHolder itemViewHolder(Context context, ViewGroup parent, int viewType) {
        View view;
        if (mOrientation == LinearLayoutManager.VERTICAL) view = LayoutInflater.from(context)
                .inflate(R.layout.item_recycler_view1, parent, false);
        else view = LayoutInflater.from(context)
                .inflate(R.layout.item_recycler_view2, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void setData(final CustomViewHolder holder, final String itemData, final int position) {
        holder.setText(R.id.text_view, itemData);
    }
}
