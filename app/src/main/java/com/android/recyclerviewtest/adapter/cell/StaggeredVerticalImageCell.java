package com.android.recyclerviewtest.adapter.cell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.recyclerviewtest.R;
import com.android.recyclerviewtest.adapter.RecyclerAdapter;
import com.android.recyclerviewtest.adapter.RecyclerCell;
import com.android.recyclerviewtest.adapter.RecyclerViewHolder;
import com.android.recyclerviewtest.utils.ToastUtil;
import com.android.recyclerviewtest.utils.imageutil.GlideUtils;

import java.util.HashMap;
import java.util.List;
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
public class StaggeredVerticalImageCell extends RecyclerCell<String> {
    // 保存每一个 item 的高度，防止瀑布流图片闪烁问题
    private HashMap<String, Integer> saveHeight = new HashMap<>();

    private Random random = new Random();
    private GlideUtils glideUtils;

    public StaggeredVerticalImageCell(String itemData, GlideUtils glideUtils) {
        super(itemData);
        this.glideUtils = glideUtils;
    }

    @Override
    public int getRecyclerItemType() {
        return RecyclerCellType.S_VERTICAL_IMAGE_CELL;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(context, parent, R.layout.item_staggred_1);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, String itemData) {
        ImageView imageView = holder.getView(R.id.imageview);
        // 快速设置 item 的高度，而不是等待图片加载完成在设置高度
        Integer integer = saveHeight.get(itemData);
        if (integer == null || integer <= 0) {
            integer = (random.nextInt(4) + 6) * 100;
            saveHeight.put(itemData, integer);
        }

        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.height = integer;
        imageView.setLayoutParams(layoutParams);

        glideUtils.loadImage(itemData, imageView);
    }

    @Override
    public void onItemClick(@NonNull Context context, @NonNull RecyclerAdapter recyclerAdapter, @NonNull View itemView, int position, String itemData) {
        ToastUtil.showSingleToast(context, "删除位置：" + position);
        List cellList = recyclerAdapter.getCellList();
        cellList.remove(position);

        recyclerAdapter.remove(position);

        // 刷新全部，图片会发生跳动
        // adapter.notifyDataSetChanged();

        // 只刷新指定位置
        recyclerAdapter.notifyItemRemoved(position);  // 指定位置删除数据
        recyclerAdapter.notifyItemRangeChanged(position, cellList.size() - position); // 一定要重新排列位置
        // recyclerAdapter.notifyItemChanged(position);  // 指定位置改变数据
        // recyclerAdapter.notifyItemInserted(position); // 指定位置新增数据
    }

}
