package com.renj.recycler.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-06-05   9:56
 * <p>
 * 描述：{@link android.support.v7.widget.RecyclerView} 不同类型 item 实现类超类，具体实现类继承该类<br/>
 * 泛型 T 表示需要用到的数据类型
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public abstract class BaseRecyclerCell<T> {
    public static final int ITEM_TYPE_DEFAULT = 0;

    /**
     * item 数据
     */
    protected T mItemData;
    /**
     * 当前item条目类型值，通过构造函数指定。<br/>
     * <b>一个Adapter中有多种条目时用到，并且添加到同一个 {@link RecyclerAdapter} 的不同
     * {@link BaseRecyclerCell} 的 {@link #mItemType} 值必须不同，
     * 否则发生 {@link ClassCastException}。</b><br/>
     * <b>作用：用于在 {@link RecyclerAdapter} 中区分不同的 item 类型。</b>
     */
    final int mItemType;
    /**
     * 当前item布局资源id
     */
    final int mItemLayoutResId;

    /**
     * 构造函数，一个Adapter中有多种条目时使用其他构造函数 {@link #BaseRecyclerCell(int, int)}、{@link #BaseRecyclerCell(int, int, Object)}
     *
     * @param itemLayoutResId 当前item布局资源id
     */
    public BaseRecyclerCell(@LayoutRes int itemLayoutResId) {
        this(itemLayoutResId, null);
    }

    /**
     * 构造函数
     *
     * @param itemType        当前item条目类型值，查看{@link #mItemType}
     * @param itemLayoutResId 当前item布局资源id
     */
    public BaseRecyclerCell(int itemType, @LayoutRes int itemLayoutResId) {
        this(itemType, itemLayoutResId, null);
    }

    /**
     * 构造函数，一个Adapter中有多种条目时使用其他构造函数 {@link #BaseRecyclerCell(int, int)}、{@link #BaseRecyclerCell(int, int, Object)}
     *
     * @param itemLayoutResId 当前item布局资源id
     * @param itemData        item 数据
     */
    public BaseRecyclerCell(@LayoutRes int itemLayoutResId, T itemData) {
        this(ITEM_TYPE_DEFAULT, itemLayoutResId, itemData);
    }

    /**
     * 构造函数
     *
     * @param itemType        当前item条目类型值，查看{@link #mItemType}
     * @param itemLayoutResId 当前item布局资源id
     * @param itemData        item 数据
     */
    public BaseRecyclerCell(int itemType, @LayoutRes int itemLayoutResId, T itemData) {
        this.mItemType = itemType;
        this.mItemLayoutResId = itemLayoutResId;
        this.mItemData = itemData;
    }

    public T getItemData() {
        return mItemData;
    }

    /**
     * 绑定 holder，{@link BaseRecyclerCell#mItemData} 为 item 数据
     */
    public abstract void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, T itemData);

    public void onAttachedToWindow(@NonNull RecyclerViewHolder holder) {

    }

    /**
     * 如有必要，释放资源
     *
     * @param holder
     */
    public void onDetachedFromWindow(@NonNull RecyclerViewHolder holder) {

    }

    public void onItemClick(@NonNull Context context, @NonNull RecyclerAdapter recyclerAdapter,
                            @NonNull RecyclerViewHolder holder, @NonNull View itemView, int position, T itemData) {

    }

    public boolean onItemLongClick(@NonNull Context context, @NonNull RecyclerAdapter recyclerAdapter,
                                   @NonNull RecyclerViewHolder holder, @NonNull View itemView, int position, T itemData) {
        return false;
    }
}
