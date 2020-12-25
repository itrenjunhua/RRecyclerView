package com.android.test.adapter.cell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.test.R;
import com.android.test.utils.ToastUtil;
import com.android.test.utils.imageutil.GlideUtils;
import com.renj.recycler.adapter.RecyclerAdapter;
import com.renj.recycler.adapter.BaseRecyclerCell;
import com.renj.recycler.adapter.RecyclerViewHolder;

import java.util.HashMap;
import java.util.Random;

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
public class StaggeredHorizontalImageCell extends BaseRecyclerCell<String> {
    // 保存每一个 item 的高度，防止瀑布流图片闪烁问题
    private HashMap<String, Integer> saveHeight = new HashMap<>();
    private Random random = new Random();
    private GlideUtils glideUtils;

    public StaggeredHorizontalImageCell(String itemData, GlideUtils glideUtils) {
        super(RecyclerCellType.S_HORIZONTAL_IMAGE_CELL, R.layout.item_staggred_2, itemData);
        this.glideUtils = glideUtils;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, String itemData) {
        ImageView imageView = holder.getView(R.id.imageview);
        // 快速设置 item 的宽度，而不是等待图片加载完成在设置宽度
        Integer integer = saveHeight.get(itemData);
        if (integer == null || integer <= 0) {
            integer = (random.nextInt(4) + 6) * 100;
            saveHeight.put(itemData, integer);
        }

        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.width = integer;
        imageView.setLayoutParams(layoutParams);

        glideUtils.loadImage(itemData, imageView);
    }

    @Override
    public void onItemClick(@NonNull Context context, @NonNull RecyclerAdapter recyclerAdapter,
                            @NonNull RecyclerViewHolder holder, @NonNull View itemView, int position, String itemData) {
        ToastUtil.showSingleToast(context, "删除位置：" + position);
        recyclerAdapter.removeAndNotifyItem(position);
    }

}
