package com.android.recyclerviewtest.adapter.cell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.android.recyclerviewtest.R;
import com.android.recyclerviewtest.adapter.RecyclerAdapter;
import com.android.recyclerviewtest.adapter.RecyclerCell;
import com.android.recyclerviewtest.adapter.RecyclerViewHolder;
import com.android.recyclerviewtest.utils.ToastUtil;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-06-05   15:48
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class HorizontalTextCell extends RecyclerCell<String> {

    public HorizontalTextCell(String itemData) {
        super(itemData);
    }

    @Override
    public int getRecyclerItemType() {
        return RecyclerCellType.HORIZONTAL_TEXT_CELL;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(context, parent, R.layout.item_recycler_view2);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, String itemData) {
        holder.setText(R.id.text_view, itemData);
    }

    @Override
    public boolean onItemLongClick(@NonNull Context context, @NonNull RecyclerAdapter recyclerAdapter, @NonNull View itemView, int position, String itemData) {
        ToastUtil.showSingleToast(context, "长按 位置:" + position + "；数据:" + itemData);
        return false;
    }
}
