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
import com.android.recyclerviewtest.utils.imageutil.GlideUtils;

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
public class VerticalImageCell extends RecyclerCell<String> {

    private GlideUtils glideUtils;

    public VerticalImageCell(String itemData, GlideUtils glideUtils) {
        super(itemData);
        this.glideUtils = glideUtils;
    }

    @Override
    public int getRecyclerItemType() {
        return RecyclerCellType.VERTICAL_IMAGE_CELL;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(context, parent, R.layout.item_multiple_img);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, String itemData) {
        glideUtils.loadCornerImage(itemData, holder.getImageView(R.id.multiple_imageview), 4); // 加载圆角图片
    }

    @Override
    public void onItemClick(@NonNull Context context, @NonNull RecyclerAdapter recyclerAdapter, @NonNull View itemView, int position, String itemData) {
        ToastUtil.showSingleToast(context, "图片链接 - " + itemData);
    }
}
