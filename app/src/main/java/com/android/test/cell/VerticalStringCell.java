package com.android.test.cell;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.android.test.R;
import com.renj.recycler.adapter.BaseRecyclerCell;
import com.renj.recycler.adapter.RecyclerViewHolder;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2019-06-05   15:48
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class VerticalStringCell extends BaseRecyclerCell<String> {

    private RecyclerView recyclerView;
    private ItemTouchHelper itemTouchHelper;

    public VerticalStringCell() {
        super(R.layout.item_recycler_view1);
    }

    public VerticalStringCell(RecyclerView recyclerView, ItemTouchHelper itemTouchHelper) {
        super(R.layout.item_recycler_view1);
        this.recyclerView = recyclerView;
        this.itemTouchHelper = itemTouchHelper;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, String itemData) {
        holder.setText(R.id.text_view, itemData);
    }
}
