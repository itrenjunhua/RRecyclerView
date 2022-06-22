package com.android.test.cell;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.android.test.R;
import com.android.test.bean.ContentItem;
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
 * 描述：分组内容
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class GroupContentCell extends BaseRecyclerCell<MultiItemEntity> {

    public GroupContentCell() {
        super(R.layout.item_recycler_content);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, MultiItemEntity itemData) {
        holder.setText(R.id.text_view, ((ContentItem) (itemData)).content);
    }

    @Override
    public void onItemClick(@NonNull Context context, @NonNull RecyclerAdapter recyclerAdapter,
                            @NonNull RecyclerViewHolder holder, @NonNull View itemView, int position, MultiItemEntity itemData) {
        ToastUtil.showSingleToast(context, ((ContentItem) (itemData)).content);
    }
}
