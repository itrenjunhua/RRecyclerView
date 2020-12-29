package com.android.test.cell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.android.test.R;
import com.android.test.utils.ToastUtil;
import com.android.test.utils.imageutil.GlideUtils;
import com.renj.recycler.adapter.SimpleMultiItemEntity;
import com.renj.recycler.adapter.BaseRecyclerCell;
import com.renj.recycler.adapter.RecyclerAdapter;
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
public class VerticalImageCell extends BaseRecyclerCell<SimpleMultiItemEntity<String>> {

    private GlideUtils glideUtils;

    public VerticalImageCell(GlideUtils glideUtils) {
        super(R.layout.item_multiple_img);
        this.glideUtils = glideUtils;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, SimpleMultiItemEntity<String> itemData) {
        glideUtils.loadCornerImage(itemData.getData(), holder.getImageView(R.id.multiple_imageview), 4); // 加载圆角图片
    }

    @Override
    public void onItemClick(@NonNull Context context, @NonNull RecyclerAdapter recyclerAdapter,
                            @NonNull RecyclerViewHolder holder, @NonNull View itemView, int position, SimpleMultiItemEntity<String> itemData) {
        ToastUtil.showSingleToast(context, "图片链接 - " + itemData.getData());
    }
}
