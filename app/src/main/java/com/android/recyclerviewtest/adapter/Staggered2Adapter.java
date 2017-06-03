package com.android.recyclerviewtest.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.recyclerviewtest.R;
import com.android.recyclerviewtest.activity.BaseActivity;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.HashMap;
import java.util.List;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2017-04-12    17:35
 * <p/>
 * 描述：StaggeredGridLayoutManager 类型 StaggeredGridLayoutManager.HORIZONTAL 的Adapter
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */

public class Staggered2Adapter extends SingleTypeAdapter<String> {
    // 保存每一个 item 的高度，防止瀑布流图片闪烁问题
    private HashMap<String, Integer> saveHeight = new HashMap<>();
    private BaseActivity activity;

    public Staggered2Adapter(Context context, List datas, int layoutID) {
        super(context, datas, layoutID);
        activity = (BaseActivity) context;
    }

    @Override
    public void setData(CustomViewHolder holder, String itemData, int position) {
        ImageView imageView = holder.getView(R.id.imageview);
        // 快速设置 item 的宽度，而不是等待图片加载完成在设置宽度
        Integer integer = saveHeight.get(itemData);
        if (integer != null && integer > 0) {
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            layoutParams.width = integer;
            imageView.setLayoutParams(layoutParams);
        }

        // 给加载图片时设置监听，保存每一张图片的高度
        activity.glideUtils.loadImage(itemData, imageView, new RequestListener<String, GlideBitmapDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideBitmapDrawable resource, String model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                Integer integer = saveHeight.get(model);
                if (integer == null || integer <= 0)
                    saveHeight.put(model, resource.getIntrinsicHeight());
                return false;
            }
        });
    }
}
