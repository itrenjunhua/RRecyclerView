package com.android.test.adapter.cell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.android.test.R;
import com.android.test.utils.ToastUtil;
import com.renj.recycler.adapter.RecyclerAdapter;
import com.renj.recycler.adapter.RecyclerCell;
import com.renj.recycler.adapter.RecyclerViewHolder;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-06-05   17:05
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class GridTextCell extends RecyclerCell<String> {
    public GridTextCell(String itemData) {
        super(itemData);
    }

    @Override
    public int getRecyclerItemType() {
        return RecyclerCellType.GRID_TEXT_CELL;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(context, parent, R.layout.item_grid);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, String itemData) {
        holder.setText(R.id.text_grid, itemData);
    }

    @Override
    public void onItemClick(@NonNull Context context, @NonNull RecyclerAdapter recyclerAdapter, @NonNull View itemView, int position, String itemData) {
        ToastUtil.showSingleToast(context, "点击TextView控件 - " + itemData);
    }
}
