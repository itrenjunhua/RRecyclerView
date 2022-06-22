package com.android.test.cell;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

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
public class GridTextCell extends BaseRecyclerCell<SimpleMultiItemEntity<String>> {
    public GridTextCell() {
        super(R.layout.item_grid);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, SimpleMultiItemEntity<String> itemData) {
        holder.setText(R.id.text_grid, itemData.getData());
    }

    @Override
    public void onItemClick(@NonNull Context context, @NonNull RecyclerAdapter recyclerAdapter, @NonNull RecyclerViewHolder holder,
                            @NonNull View itemView, int position, SimpleMultiItemEntity<String> itemData) {
        ToastUtil.showSingleToast(context, "点击TextView控件 - " + itemData.getData());
    }
}
