package com.android.test.cell;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.android.test.R;
import com.android.test.utils.ToastUtil;
import com.android.test.utils.imageutil.ImageLoaderUtils;
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
 * 创建时间：2019-06-05   15:48
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class VerticalImageCell extends BaseRecyclerCell<SimpleMultiItemEntity<String>> {

    public VerticalImageCell() {
        super(R.layout.item_multiple_img);
    }

    public VerticalImageCell(int layoutId) {
        super(layoutId);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, SimpleMultiItemEntity<String> itemData) {
        ImageView imageView = holder.getImageView(R.id.multiple_imageview);
        ImageLoaderUtils.loadImage(imageView, itemData.getData(), 4);
    }

    @Override
    public void onItemClick(@NonNull Context context, @NonNull RecyclerAdapter recyclerAdapter,
                            @NonNull RecyclerViewHolder holder, @NonNull View itemView, int position, SimpleMultiItemEntity<String> itemData) {
        ToastUtil.showSingleToast(context, "图片链接 - " + itemData.getData());
    }
}
