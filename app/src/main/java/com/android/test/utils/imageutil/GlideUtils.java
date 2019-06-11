package com.android.test.utils.imageutil;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.view.animation.Animation;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.GifRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2017-02-16    20:14
 * <p/>
 * 描述：Glid加载工具类<br/><br/>
 * <b>更多关于Glide方法的使用可以查看博客：</b><br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="http://blog.csdn.net/itrenj/article/details/54143889">图片框架Glide的使用(一)<a/><br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="http://blog.csdn.net/itrenj/article/details/54297920">图片框架Glide的使用(二)<a/><br/>
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
public class GlideUtils {
    private int ERROR_IMG_ID = -1; // 加载错误图片
    private int LOADING_IMG_ID = -1; // 正在加载图片
    private RequestManager glideWith;
    private Context context;

    private GlideUtils() {
    }

    /**
     * 常量
     */
    static class Contants {
        public static final int BLUR_VALUE = 20; // 模糊
        public static final int CORNER_RADIUS = 20; // 圆角
        public static final float THUMB_SIZE = 0.3f; // 0-1之间  30%原图的大小
    }

    /****
     * 初始化RequestManager并获取GlidUtils实例
     *****/
    public static GlideUtils with(Context context) {
        GlideUtils glideUtils = new GlideUtils();
        glideUtils.get(context);
        return glideUtils;
    }

    public static GlideUtils with(Activity activity) {
        GlideUtils glideUtils = new GlideUtils();
        glideUtils.get(activity);
        return glideUtils;
    }

    public static GlideUtils with(android.app.Fragment fragment) {
        GlideUtils glideUtils = new GlideUtils();
        glideUtils.get(fragment);
        return glideUtils;
    }

    public static GlideUtils with(android.support.v4.app.Fragment fragment) {
        GlideUtils glideUtils = new GlideUtils();
        glideUtils.get(fragment);
        return glideUtils;
    }

    public static GlideUtils with(FragmentActivity fragmentActivity) {
        GlideUtils glideUtils = new GlideUtils();
        glideUtils.get(fragmentActivity);
        return glideUtils;
    }

    /************
     * 初始化RequestManager
     ***************/
    private void get(Context context) {
        this.context = context;
        glideWith = Glide.with(context);
    }

    private void get(Activity activity) {
        this.context = activity;
        glideWith = Glide.with(activity);
    }

    private void get(android.app.Fragment fragment) {
        this.context = fragment.getActivity();
        glideWith = Glide.with(fragment);
    }

    private void get(android.support.v4.app.Fragment fragment) {
        this.context = fragment.getActivity();
        glideWith = Glide.with(fragment);
    }

    private void get(FragmentActivity activity) {
        this.context = activity;
        glideWith = Glide.with(activity);
    }

    /**
     * 设置加载错误时显示IDE图片
     *
     * @param resId 图片资源id
     * @return
     */
    public GlideUtils error(int resId) {
        this.ERROR_IMG_ID = resId;
        return this;
    }

    /**
     * 设置正在加载时显示的图片
     *
     * @param resId 图片资源id
     * @return
     */
    public GlideUtils loading(int resId) {
        this.LOADING_IMG_ID = resId;
        return this;
    }

    /**
     * 常规加载图片
     *
     * @param imgUrl    图片地址
     * @param imageView 图片容器
     * @param size      宽和高
     */
    public void loadImage(String imgUrl, ImageView imageView, int size) {
        loadImage(imgUrl, imageView, size, size);
    }

    /**
     * 常规加载图片
     *
     * @param imgUrl    图片地址
     * @param imageView 图片容器
     * @param width     宽
     * @param height    高
     */
    public void loadImage(String imgUrl, ImageView imageView, int width, int height) {
        DrawableRequestBuilder<String> requestBuilder = glideWith.load(imgUrl)
                .override(width, height)
                .crossFade()
                .priority(Priority.NORMAL);
        if (!(ERROR_IMG_ID <= 0)) requestBuilder.error(ERROR_IMG_ID);
        if (!(LOADING_IMG_ID <= 0)) requestBuilder.placeholder(LOADING_IMG_ID);
        requestBuilder.into(imageView);
    }

    /**
     * 常规加载图片
     *
     * @param imgUrl    图片地址
     * @param imageView 图片容器
     */
    public void loadImage(String imgUrl, ImageView imageView) {
        DrawableTypeRequest<String> typeRequest = glideWith.load(imgUrl);
        if (!(ERROR_IMG_ID <= 0)) typeRequest.error(ERROR_IMG_ID);
        if (!(LOADING_IMG_ID <= 0)) typeRequest.placeholder(LOADING_IMG_ID);
        typeRequest.into(imageView);
    }

    /**
     * 常规加载图片
     *
     * @param imgUrl    图片地址
     * @param imageView 图片容器
     * @param listener  监听
     */
    public void loadImage(String imgUrl, ImageView imageView, RequestListener listener) {
        DrawableTypeRequest<String> typeRequest = glideWith.load(imgUrl);
        if (!(ERROR_IMG_ID <= 0)) typeRequest.error(ERROR_IMG_ID);
        if (!(LOADING_IMG_ID <= 0)) typeRequest.placeholder(LOADING_IMG_ID);
        if (listener != null) typeRequest.listener(listener);
        typeRequest.into(imageView);
    }

    /**
     * 使用加载图片动画
     *
     * @param imgUrl    图片地址
     * @param imageView 图片容器
     * @param animation 加载动画
     */
    public void loadAnimImage(String imgUrl, ImageView imageView, Animation animation) {
        loadAnimImage(imgUrl, imageView, animation, null);
    }

    /**
     * 使用加载图片动画
     *
     * @param imgUrl    图片地址
     * @param imageView 图片容器
     * @param animId    加载动画资源ID
     */
    public void loadAnimImage(String imgUrl, ImageView imageView, int animId) {
        loadAnimImage(imgUrl, imageView, animId, null);
    }

    /**
     * 使用加载图片动画
     *
     * @param imgUrl    图片地址
     * @param imageView 图片容器
     * @param animation 加载动画
     * @param listener  监听
     */
    public void loadAnimImage(String imgUrl, ImageView imageView, Animation animation, RequestListener listener) {
        DrawableRequestBuilder<String> requestBuilder = glideWith.load(imgUrl)
                .crossFade()
                .priority(Priority.NORMAL); //下载的优先级
        if (animation != null) requestBuilder.animate(animation);
        if (!(ERROR_IMG_ID <= 0)) requestBuilder.error(ERROR_IMG_ID);
        if (!(LOADING_IMG_ID <= 0)) requestBuilder.placeholder(LOADING_IMG_ID);
        if (listener != null) requestBuilder.listener(listener);
        requestBuilder.into(imageView);
    }

    /**
     * 使用加载图片动画
     *
     * @param imgUrl    图片地址
     * @param imageView 图片容器
     * @param animId    加载动画资源ID
     * @param listener  监听
     */
    public void loadAnimImage(String imgUrl, ImageView imageView, int animId, RequestListener listener) {
        DrawableRequestBuilder<String> requestBuilder = glideWith.load(imgUrl)
                .crossFade()
                .priority(Priority.NORMAL); //下载的优先级
        if (!(animId <= 0)) requestBuilder.animate(animId);
        if (!(ERROR_IMG_ID <= 0)) requestBuilder.error(ERROR_IMG_ID);
        if (!(LOADING_IMG_ID <= 0)) requestBuilder.placeholder(LOADING_IMG_ID);
        if (listener != null) requestBuilder.listener(listener);
        requestBuilder.into(imageView);
    }

    /**
     * 从文件中加载图片
     *
     * @param file      图片File对象
     * @param imageView 图片容器
     */
    public void loadImageFormFile(File file, ImageView imageView) {
        DrawableRequestBuilder<File> requestBuilder = glideWith.load(file).crossFade();
        if (!(ERROR_IMG_ID <= 0)) requestBuilder.error(ERROR_IMG_ID);
        if (!(LOADING_IMG_ID <= 0)) requestBuilder.placeholder(LOADING_IMG_ID);
        requestBuilder.into(imageView);
    }

    /**
     * 从文件中加载图片并指定宽和高
     *
     * @param file      图片File对象
     * @param imageView 图片容器
     * @param size      宽和高
     */
    public void loadImageFormFile(File file, ImageView imageView, int size) {
        loadImageFormFile(file, imageView, size, size);
    }

    /**
     * 从文件中加载图片并指定宽和高
     *
     * @param file      图片File对象
     * @param imageView 图片容器
     * @param width     宽
     * @param height    高
     */
    public void loadImageFormFile(File file, ImageView imageView, int width, int height) {
        DrawableRequestBuilder<File> requestBuilder = glideWith.load(file)
                .crossFade()
                .override(width, height);
        if (!(ERROR_IMG_ID <= 0)) requestBuilder.error(ERROR_IMG_ID);
        if (!(LOADING_IMG_ID <= 0)) requestBuilder.placeholder(LOADING_IMG_ID);
        requestBuilder.into(imageView);
    }

    /**
     * 从文件中加载图片
     *
     * @param filePath  图片路径
     * @param imageView 图片容器
     */
    public void loadImageFormFile(String filePath, ImageView imageView) {
        File file = new File(filePath);
        DrawableRequestBuilder<File> requestBuilder = glideWith.load(file).crossFade();
        if (!(ERROR_IMG_ID <= 0)) requestBuilder.error(ERROR_IMG_ID);
        if (!(LOADING_IMG_ID <= 0)) requestBuilder.placeholder(LOADING_IMG_ID);
        requestBuilder.into(imageView);
    }

    /**
     * 从文件中加载图片并指定宽和高
     *
     * @param filePath  图片路径
     * @param imageView 图片容器
     * @param size      宽和高
     */
    public void loadImageFormFile(String filePath, ImageView imageView, int size) {
        loadImageFormFile(filePath, imageView, size, size);
    }

    /**
     * 从文件中加载图片并指定宽和高
     *
     * @param filePath  图片路径
     * @param imageView 图片容器
     * @param width     宽
     * @param height    高
     */
    public void loadImageFormFile(String filePath, ImageView imageView, int width, int height) {
        File file = new File(filePath);
        loadImageFormFile(file, imageView, width, height);
    }

    /**
     * 从Uri加载图片
     *
     * @param uri       图片URI
     * @param imageView 图片容器
     */
    public void loadImageFormUri(Uri uri, ImageView imageView) {
        DrawableRequestBuilder<Uri> requestBuilder = glideWith.load(uri).crossFade();
        if (!(ERROR_IMG_ID <= 0)) requestBuilder.error(ERROR_IMG_ID);
        if (!(LOADING_IMG_ID <= 0)) requestBuilder.placeholder(LOADING_IMG_ID);
        requestBuilder.into(imageView);
    }

    /**
     * 从Uri加载图片并指定宽和高
     *
     * @param uri       图片URI
     * @param imageView 图片容器
     * @param size      宽和高
     */
    public void loadImageFormUri(Uri uri, ImageView imageView, int size) {
        loadImageFormUri(uri, imageView, size, size);
    }

    /**
     * 从Uri加载图片并指定宽和高
     *
     * @param uri       图片URI
     * @param imageView 图片容器
     * @param width     宽
     * @param height    高
     */
    public void loadImageFormUri(Uri uri, ImageView imageView, int width, int height) {
        DrawableRequestBuilder<Uri> requestBuilder = glideWith.load(uri)
                .crossFade()
                .override(width, height);
        if (!(ERROR_IMG_ID <= 0)) requestBuilder.error(ERROR_IMG_ID);
        if (!(LOADING_IMG_ID <= 0)) requestBuilder.placeholder(LOADING_IMG_ID);
        requestBuilder.into(imageView);
    }

    /**
     * 加载缩略图
     *
     * @param imgUrl    图片地址
     * @param imageView 图片容器
     */
    public void loadThumbnailImage(String imgUrl, ImageView imageView) {
        DrawableRequestBuilder<String> requestBuilder = glideWith.load(imgUrl)
                .crossFade().priority(Priority.NORMAL) //下载的优先级
                .thumbnail(Contants.THUMB_SIZE);
        if (!(ERROR_IMG_ID <= 0)) requestBuilder.error(ERROR_IMG_ID);
        if (!(LOADING_IMG_ID <= 0)) requestBuilder.placeholder(LOADING_IMG_ID);
        requestBuilder.into(imageView);
    }

    /**
     * 加载图片并设置为指定大小
     *
     * @param imgUrl
     * @param imageView
     * @param withSize
     * @param heightSize
     */
    public void loadOverrideImage(String imgUrl, ImageView imageView, int withSize, int heightSize) {
        DrawableRequestBuilder<String> requestBuilder = glideWith.load(imgUrl)
                .crossFade().priority(Priority.NORMAL) //下载的优先级
                .override(withSize, heightSize);
        if (!(ERROR_IMG_ID <= 0)) requestBuilder.error(ERROR_IMG_ID);
        if (!(LOADING_IMG_ID <= 0)) requestBuilder.placeholder(LOADING_IMG_ID);
        requestBuilder.into(imageView);
    }

    /**
     * 加载图片并对其进行模糊处理
     *
     * @param imgUrl
     * @param imageView
     */
    public void loadBlurImage(String imgUrl, ImageView imageView) {
        DrawableRequestBuilder<String> requestBuilder = Glide.with(context)
                .load(imgUrl)
                .crossFade().priority(Priority.NORMAL) //下载的优先级
                .bitmapTransform(new BlurTransformation(context, Contants.BLUR_VALUE));
        if (!(ERROR_IMG_ID <= 0)) requestBuilder.error(ERROR_IMG_ID);
        if (!(LOADING_IMG_ID <= 0)) requestBuilder.placeholder(LOADING_IMG_ID);
        requestBuilder.into(imageView);
    }

    /**
     * 加载圆图
     *
     * @param imgUrl
     * @param imageView
     */
    public void loadCircleImage(String imgUrl, ImageView imageView) {
        DrawableRequestBuilder<String> requestBuilder = Glide.with(context)
                .load(imgUrl)
                .crossFade().priority(Priority.NORMAL) //下载的优先级
                .bitmapTransform(new CropCircleTransformation(context));
        if (!(ERROR_IMG_ID <= 0)) requestBuilder.error(ERROR_IMG_ID);
        if (!(LOADING_IMG_ID <= 0)) requestBuilder.placeholder(LOADING_IMG_ID);
        requestBuilder.into(imageView);
    }

    /**
     * 加载模糊的圆角的图片
     *
     * @param imgUrl
     * @param imageView
     */
    public void loadBlurCircleImage(String imgUrl, ImageView imageView) {
        DrawableRequestBuilder<String> requestBuilder = Glide.with(context)
                .load(imgUrl)
                .crossFade().priority(Priority.NORMAL) //下载的优先级
                .bitmapTransform(new BlurTransformation(context, Contants.BLUR_VALUE), new CropCircleTransformation(context));
        if (!(ERROR_IMG_ID <= 0)) requestBuilder.error(ERROR_IMG_ID);
        if (!(LOADING_IMG_ID <= 0)) requestBuilder.placeholder(LOADING_IMG_ID);
        requestBuilder.into(imageView);
    }

    /**
     * 加载圆角图片(指定圆角大小)
     *
     * @param imgUrl
     * @param imageView
     * @param size      圆角大小
     */
    public void loadCornerImage(String imgUrl, ImageView imageView, int size) {
        DrawableRequestBuilder<String> requestBuilder = Glide.with(context)
                .load(imgUrl)
                .crossFade().priority(Priority.NORMAL) //下载的优先级
                .bitmapTransform(new RoundedCornersTransformation(context, size, 0));
        if (!(ERROR_IMG_ID <= 0)) requestBuilder.error(ERROR_IMG_ID);
        if (!(LOADING_IMG_ID <= 0)) requestBuilder.placeholder(LOADING_IMG_ID);
        requestBuilder.into(imageView);
    }

    /**
     * 加载圆角图片
     *
     * @param imgUrl
     * @param imageView
     */
    public void loadCornerImage(String imgUrl, ImageView imageView) {
        loadCornerImage(imgUrl, imageView, Contants.CORNER_RADIUS);
    }

    /**
     * 加载模糊的圆角图片
     *
     * @param imgUrl
     * @param imageView
     */
    public void loadBlurCornerImage(String imgUrl, ImageView imageView) {
        DrawableRequestBuilder<String> requestBuilder = Glide.with(context)
                .load(imgUrl)
                .crossFade().priority(Priority.NORMAL) //下载的优先级
                .bitmapTransform(new BlurTransformation(context, Contants.BLUR_VALUE), new RoundedCornersTransformation(context, Contants.CORNER_RADIUS, Contants.CORNER_RADIUS));
        if (!(ERROR_IMG_ID <= 0)) requestBuilder.error(ERROR_IMG_ID);
        if (!(LOADING_IMG_ID <= 0)) requestBuilder.placeholder(LOADING_IMG_ID);
        requestBuilder.into(imageView);
    }

    /**
     * 同步加载图片
     *
     * @param imgUrl
     * @param target
     */
    public void loadBitmapSync(String imgUrl, SimpleTarget<Bitmap> target) {
        BitmapRequestBuilder<String, Bitmap> requestBuilder = glideWith.load(imgUrl)
                .asBitmap()
                .priority(Priority.NORMAL);//下载的优先级
        if (!(ERROR_IMG_ID <= 0)) requestBuilder.error(ERROR_IMG_ID);
        if (!(LOADING_IMG_ID <= 0)) requestBuilder.placeholder(LOADING_IMG_ID);
        requestBuilder.into(target);
    }

    /**
     * 加载gif
     *
     * @param imgUrl
     * @param imageView
     */
    public void loadGifImage(String imgUrl, ImageView imageView) {
        GifRequestBuilder<String> gifRequestBuilder = glideWith.load(imgUrl)
                .asGif()
                .crossFade()
                .priority(Priority.NORMAL); //下载的优先级
        if (!(ERROR_IMG_ID <= 0)) gifRequestBuilder.error(ERROR_IMG_ID);
        gifRequestBuilder.into(imageView);
    }

    /**
     * 加载gif的缩略图
     *
     * @param imgUrl
     * @param imageView
     */
    public void loadGifThumbnailImage(String imgUrl, ImageView imageView) {
        GifRequestBuilder<String> gifRequestBuilder = glideWith.load(imgUrl)
                .asGif()
                .crossFade()
                .priority(Priority.NORMAL);//下载的优先级
        if (!(ERROR_IMG_ID <= 0)) gifRequestBuilder.error(ERROR_IMG_ID);
        gifRequestBuilder.thumbnail(Contants.THUMB_SIZE).into(imageView);
    }

    /**
     * 恢复请求，一般在停止滚动的时候
     */
    public void resumeRequests() {
        glideWith.resumeRequests();
    }

    /**
     * 暂停请求 正在滚动的时候
     */
    public void pauseRequests() {
        glideWith.pauseRequests();
    }

    /**
     * 清除磁盘缓存
     *
     * @param context
     */
    public void clearDiskCache(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();//清理磁盘缓存 需要在子线程中执行
            }
        }).start();
    }

    /**
     * 清除内存缓存
     *
     * @param context
     */
    public void clearMemory(Context context) {
        Glide.get(context).clearMemory();//清理内存缓存  可以在UI主线程中进行
    }

    /**
     * 设置AbsListView滚动时不加载图片
     *
     * @param absListView
     */
    public void setAbsListView(AbsListView absListView) {
        // 设置滑动监听
        absListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                    // 停止滑动或者手指滑动listview时加载图片
                    resumeRequests();
                } else if (scrollState == SCROLL_STATE_FLING) {
                    // 手指离开根据惯性快速滑动时不加载图片
                    pauseRequests();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }
}
