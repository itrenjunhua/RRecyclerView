package com.android.test.databinding;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.android.test.utils.imageutil.ImageLoaderUtils;

/**
 * 所在项目名: RRecyclerView
 * <p>
 * 所在包名: com.android.test.databinding
 * <p>
 * 当前类名: ImageViewAdapter
 * <p>
 * 作者：Renj
 * <p>
 * 描述:
 * <p>
 * 创建时间: 2021-09-07 15:56
 */
public class DBImageViewAdapter {
    @BindingAdapter(value = {"url"})
    public static void setImageUrl(ImageView imageView, String url) {
        ImageLoaderUtils.loadImage(imageView, url, 4);
    }
}
