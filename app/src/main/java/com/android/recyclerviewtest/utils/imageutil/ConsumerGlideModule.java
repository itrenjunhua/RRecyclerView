package com.android.recyclerviewtest.utils.imageutil;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;

import java.io.File;

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
public class ConsumerGlideModule implements GlideModule {
    private final int MAX_MEMORY_CACHE = 1024 * 1024 * 4;    // 4M
    private final int MAX_DISK_CACHE = 1024 * 1024 * 10;      // 12M
    private String packageName;
    private String versionName = "1.0.0";

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // 应用程序可使用的最大内存，在一般情况下，以最大内存的 1/8 作为图片的缓存内存
        long maxBitmapMemoryCache = Runtime.getRuntime().maxMemory() / 8;
        // long totalMemory = Runtime.getRuntime().totalMemory();// 应用程序已获得内存
        // long freeMemory = Runtime.getRuntime().freeMemory(); // 应用程序已获得内存中未使用内存

        // 指定内存缓存大小
        builder.setMemoryCache(new LruResourceCache(maxBitmapMemoryCache == 0 ? MAX_MEMORY_CACHE : (int) maxBitmapMemoryCache));

        // 获取应用程序包名和版本名
        packageName = context.getPackageName();
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        // 判断SDCard是否可用
        if (!isCanSdCard(MAX_DISK_CACHE)) {
            // 指定位置在packageName/cache/versionName/glide_cache,大小为MAX_DISK_CACHE的磁盘缓存
            builder.setDiskCache(new InternalCacheDiskCacheFactory(context, "glide_cache/" + versionName, MAX_DISK_CACHE));
        } else {
            // 自定义磁盘缓存目录
            builder.setDiskCache(new DiskCache.Factory() {
                @Override
                public DiskCache build() {
                    // 自定义磁盘缓存目录为外置储存卡根目录/包名/版本名/glide_cache，大小为MAX_DISK_CACHE
                    File directory = Environment.getExternalStorageDirectory();
                    File diskCacheFile = new File(directory, packageName +
                            "/glide_cache/" + versionName);
                    return DiskLruCacheWrapper.get(diskCacheFile, MAX_DISK_CACHE);
                }
            });
        }
        //全部的内存缓存用来作为图片缓存
        builder.setBitmapPool(new LruBitmapPool(MAX_MEMORY_CACHE));
        // 修改图片的质量为ARGB_8888，和Picasso一样
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }

    private boolean isCanSdCard(long needMemorySize) {
        String storageState = Environment.getExternalStorageState();
        // Log.i("ConsumerGlideModule", "SDCard storageState -- " + storageState);
        if (Environment.MEDIA_MOUNTED.equals(storageState)) { // 判断是否可用
            File storageDirectory = Environment.getExternalStorageDirectory();
            long usableSpace = storageDirectory.getUsableSpace();
            // Log.i("ConsumerGlideModule", "SDCard usableSpace -- " + usableSpace);
            // 判断SDCard可用空间大小
            if (usableSpace > needMemorySize) return true;
            return false;
        }
        return false;
    }
}
