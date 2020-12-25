package com.android.test.adapter.cell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;

import com.android.test.R;
import com.android.test.utils.ToastUtil;
import com.renj.recycler.adapter.RecyclerAdapter;
import com.renj.recycler.adapter.BaseRecyclerCell;
import com.renj.recycler.adapter.RecyclerViewHolder;

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
public class VerticalTextCell extends BaseRecyclerCell<String> {

    private RecyclerView recyclerView;
    private ItemTouchHelper itemTouchHelper;

    public VerticalTextCell(String itemData) {
        super(RecyclerCellType.VERTICAL_TEXT_CELL, R.layout.item_recycler_view1, itemData);
    }

    public VerticalTextCell(String itemData, RecyclerView recyclerView, ItemTouchHelper itemTouchHelper) {
        super(RecyclerCellType.VERTICAL_TEXT_CELL, R.layout.item_recycler_view1, itemData);
        this.recyclerView = recyclerView;
        this.itemTouchHelper = itemTouchHelper;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, String itemData) {
        holder.setText(R.id.text_view, itemData);
    }

    @Override
    public void onItemClick(@NonNull Context context, @NonNull RecyclerAdapter recyclerAdapter,
                            @NonNull RecyclerViewHolder holder, @NonNull View itemView, int position, String itemData) {
        ToastUtil.showSingleToast(context, "点击 位置:" + position + "；数据:" + itemData);
    }

    @Override
    public boolean onItemLongClick(@NonNull Context context, @NonNull RecyclerAdapter recyclerAdapter,
                                   @NonNull RecyclerViewHolder holder, @NonNull View itemView, int position, String itemData) {
        // 长按开始拖拽
        if (itemTouchHelper != null && recyclerView != null)
            itemTouchHelper.startDrag(recyclerView.getChildViewHolder(itemView));
        return false;
    }
}
