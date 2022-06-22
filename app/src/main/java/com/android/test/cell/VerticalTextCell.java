package com.android.test.cell;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.android.test.R;
import com.android.test.utils.ToastUtil;
import com.renj.recycler.adapter.BaseRecyclerCell;
import com.renj.recycler.adapter.RecyclerAdapter;
import com.renj.recycler.adapter.RecyclerViewHolder;
import com.renj.recycler.adapter.SimpleMultiItemEntity;

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
public class VerticalTextCell extends BaseRecyclerCell<SimpleMultiItemEntity<String>> {

    private RecyclerView recyclerView;
    private ItemTouchHelper itemTouchHelper;

    public VerticalTextCell() {
        super(R.layout.item_recycler_view1);
    }

    public VerticalTextCell(RecyclerView recyclerView, ItemTouchHelper itemTouchHelper) {
        super(R.layout.item_recycler_view1);
        this.recyclerView = recyclerView;
        this.itemTouchHelper = itemTouchHelper;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, SimpleMultiItemEntity<String> itemData) {
        holder.setText(R.id.text_view, itemData.getData());
    }

    @Override
    public void onItemClick(@NonNull Context context, @NonNull RecyclerAdapter recyclerAdapter,
                            @NonNull RecyclerViewHolder holder, @NonNull View itemView, int position, SimpleMultiItemEntity<String> itemData) {
        ToastUtil.showSingleToast(context, "点击 位置:" + position + "；数据:" + itemData.getData());
    }

    @Override
    public boolean onItemLongClick(@NonNull Context context, @NonNull RecyclerAdapter recyclerAdapter,
                                   @NonNull RecyclerViewHolder holder, @NonNull View itemView, int position, SimpleMultiItemEntity<String> itemData) {
        // 长按开始拖拽
        if (itemTouchHelper != null && recyclerView != null)
            itemTouchHelper.startDrag(recyclerView.getChildViewHolder(itemView));
        return false;
    }
}
