package com.android.recyclerviewtest.adapter;

import android.content.Context;

import com.android.recyclerviewtest.R;

import java.util.List;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2017-10-18   17:57
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class MyRefreshRecyclerAdapter extends SingleTypeAdapter<String> {

    public MyRefreshRecyclerAdapter(Context context, List<String> datas, int layoutID) {
        super(context, datas, layoutID);
    }

    @Override
    public void setData(CustomViewHolder holder, String itemData, int position) {
        holder.setText(R.id.tv_my_refresh, itemData);
    }
}
