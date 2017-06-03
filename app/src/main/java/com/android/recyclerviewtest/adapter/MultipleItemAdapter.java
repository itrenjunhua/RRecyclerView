package com.android.recyclerviewtest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.recyclerviewtest.R;
import com.android.recyclerviewtest.activity.BaseActivity;
import com.android.recyclerviewtest.utils.ToastUtil;
import com.android.recyclerviewtest.utils.imageutil.GlideUtils;

import java.util.List;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2017-04-13    11:23
 * <p/>
 * 描述：MultipleItemActivity 类的Adapter
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */

public class MultipleItemAdapter extends MultipleTypeAdapter<String> {
    private static final int ITEM_TYPE_TEXT = 0;
    private static final int ITEM_TYPE_IMAGE = 1;

    private GlideUtils glideUtils;

    public MultipleItemAdapter(BaseActivity context, List<String> datas) {
        super(context, datas);
        glideUtils = context.glideUtils;
    }

    @Override
    public int itemViewType(int position) {
        if (mDatas == null || mDatas.size() <= 0) return ITEM_TYPE_TEXT;
        else return mDatas.get(position).length() > 1 ? ITEM_TYPE_IMAGE : ITEM_TYPE_TEXT;
    }

    @Override
    public CustomViewHolder itemViewHolder(Context context, ViewGroup parent, int viewType) {
        if (ITEM_TYPE_TEXT == viewType) {
            View textView = LayoutInflater.from(context)
                    .inflate(R.layout.item_recycler_view1, parent, false);
            return new TextViewHolder(textView);
        } else {
            View imageView = LayoutInflater.from(context)
                    .inflate(R.layout.item_multiple_img, parent, false);
            return new ImageViewHolder(imageView);
        }
    }

    @Override
    public void setData(CustomViewHolder holder, final String itemData, int position) {
        if (holder instanceof TextViewHolder) {
            holder.setText(R.id.textview, itemData);

            // 给控件添加点击事件
            holder.clickView(R.id.textview)
                    .setOnClickListener(new CustomViewHolder.OnClickListener() {
                        @Override
                        public void onClick(View view, int vId) {
                            ToastUtil.showSingleToast(context, "TextView内容 - " + itemData);
                        }
                    });
        } else {
            ImageView imageView = holder.getView(R.id.multiple_imageview);
            glideUtils.loadCornerImage(itemData, imageView, 4); // 加载圆角图片

            // 给控件添加点击事件
            holder.clickView(R.id.multiple_imageview)
                    .setOnClickListener(new CustomViewHolder.OnClickListener() {
                        @Override
                        public void onClick(View view, int vId) {
                            ToastUtil.showSingleToast(context, "图片链接 - " + itemData);
                        }
                    });
        }
    }

    /**
     * 显示文字的ViewHolder
     */
    static class TextViewHolder extends CustomViewHolder {
        public TextViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 显示图片的ViewHolder
     */
    static class ImageViewHolder extends CustomViewHolder {
        public ImageViewHolder(View itemView) {
            super(itemView);
        }
    }
}
