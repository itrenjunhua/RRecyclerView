package com.android.test.utils.imageutil;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2017-02-16    20:58
 * <p/>
 * 描述：Glide配置<br/>
 * <b>注意：GlideModule一定要在 AndroidManifest.xml 中进行配置</b>
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
@GlideModule
public class ConsumerGlideModule  extends AppGlideModule {
    // 磁盘缓存大小 50M
    private static final int DISK_CACHE_SIZE = 50 * 1024 * 1024;
    // 内存缓存
    private static final long MEMORY_CACHE_SIZE = (Runtime.getRuntime().maxMemory() / 8);
    // 缓存目录
    private static final String DISK_CACHE_NAME = "glide_cache";

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        RequestOptions options = RequestOptions
                .formatOf(DecodeFormat.PREFER_ARGB_8888);

        builder.setMemoryCache(new LruResourceCache(MEMORY_CACHE_SIZE))
                .setDiskCache(new InternalCacheDiskCacheFactory(context, DISK_CACHE_NAME, DISK_CACHE_SIZE))
                .setDefaultRequestOptions(options);
    }
}
