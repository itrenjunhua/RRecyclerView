package com.android.test.adapter.cell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.android.test.R;
import com.android.test.utils.ToastUtil;
import com.android.test.utils.imageutil.GlideUtils;
import com.renj.recycler.adapter.RecyclerAdapter;
import com.renj.recycler.adapter.RecyclerCell;
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
