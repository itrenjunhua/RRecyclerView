package com.android.test.cell;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.android.test.R;
import com.android.test.bean.HeaderItem;
import com.android.test.utils.ToastUtil;
import com.renj.recycler.adapter.BaseRecyclerCell;
import com.renj.recycler.adapter.MultiItemEntity;
import com.renj.recycler.adapter.RecyclerAdapter;
import com.renj.recycler.adapter.RecyclerViewHolder;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2019-06-05   15:48
 * <p>
 * 描述：分组组头
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class GroupHeaderCell extends BaseRecyclerCell<MultiItemEntity> {

    public GroupHeaderCell() {
        super(R.layout.item_recycler_header);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder<?,?> holder, int position, MultiItemEntity itemData) {
        holder.setText(R.id.tv_group, ((HeaderItem) (itemData)).title);
        holder.setText(R.id.tv_message, ((HeaderItem) (itemData)).message);
    }

    @Override
    public void onItemClick(@NonNull Context context, @NonNull RecyclerAdapter<?> recyclerAdapter,
                            @NonNull RecyclerViewHolder<?,?> holder, @NonNull View itemView, int position, MultiItemEntity itemData) {
        ToastUtil.showSingleToast(context, ((HeaderItem) (itemData)).title);
    }
}
