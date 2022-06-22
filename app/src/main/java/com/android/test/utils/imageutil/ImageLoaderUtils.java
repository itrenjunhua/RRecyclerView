package com.android.test.utils.imageutil;

import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2022-06-22   10:48
 * <p>
 * 描述：图片加载
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ImageLoaderUtils {
    public static void loadImage(ImageView imageView,String url){
        GlideApp.with(imageView).load(url)
                .into(imageView);
    }

    public static void loadImage(ImageView imageView,String url,int radius){
        GlideApp.with(imageView).load(url)
                .transform(new RoundedCorners(4))
                .into(imageView);
    }
}
