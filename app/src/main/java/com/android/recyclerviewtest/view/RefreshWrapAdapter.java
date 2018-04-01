package com.android.recyclerviewtest.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2017-10-18   17:29
 * <p>
 * 描述：包装的适配器：区分头、脚、正常的数据(默认为0)
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class RefreshWrapAdapter extends RecyclerView.Adapter {
    private final static int ITEM_TYPE_HEADER = 10068; // 头布局类型
    private final static int ITEM_TYPE_FOOTER = 10010; // 尾布局类型

    //头布局
    private View mHeaderView;
    //脚布局
    private View mFooterView;
    //正常的适配器
    private RecyclerView.Adapter mAdapter;

    public RefreshWrapAdapter(View mHeaderView, View mFooterView, RecyclerView.Adapter mAdapter) {
        this.mHeaderView = mHeaderView;
        this.mFooterView = mFooterView;
        this.mAdapter = mAdapter;
    }

    @Override
    public int getItemViewType(int position) {
        // 头布局
        if (position == 0)
            return ITEM_TYPE_HEADER;
        // 正常布局
        int tempPosition = position - 1;
        int normalCount = mAdapter.getItemCount();
        if (tempPosition < normalCount)
            return mAdapter.getItemViewType(tempPosition);
        // 尾布局
        return ITEM_TYPE_FOOTER;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEADER)
            return new HeaderViewHolder(mHeaderView);
        if (viewType == ITEM_TYPE_FOOTER)
            return new FooterViewHolder(mFooterView);
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // 头布局
        if (position == 0)
            return;

        // 正常布局
        int tempPosition = position - 1;
        int normalCount = mAdapter.getItemCount();
        if (tempPosition < normalCount)
            mAdapter.onBindViewHolder(holder, tempPosition);

    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount() + 2;
    }

    // 头布局ViewHolder
    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    // 尾布局ViewHolder
    static class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
