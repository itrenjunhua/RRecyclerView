package com.renj.recycler.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;


/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-06-05   9:57
 * <p>
 * 描述：{@link android.support.v7.widget.RecyclerView} 不同类型 item 接口类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public interface IRecyclerCell<T> {

    void onAttachedToWindow(@NonNull RecyclerViewHolder holder);

    /**
     * 返回 item 类型，子类必须实现。<b>并且添加到同一个 {@link RecyclerAdapter} 的不同
     * {@link IRecyclerCell} 的 {@link #getRecyclerItemType()} 返回值必须不同</b><br/>
     * <b>作用：用于在 {@link RecyclerAdapter} 中区分不同的 item 类型。</b>
     *
     * @return item 类型值，用于区分不同的 item 类型
     */
    int getRecyclerItemType();

    T getItemData();

    /**
     * 创建 ViewHolder
     *
     * @param viewType 正常情况下该参数的值和 {@link #getRecyclerItemType()} 方法的返回值相等
     */
    @NonNull
    RecyclerViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup parent, int viewType);

    /**
     * 绑定 holder，{@link RecyclerCell#itemData} 为 item 数据
     */
    void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, T itemData);

    void onDetachedFromWindow(@NonNull RecyclerViewHolder holder);

    void onItemClick(@NonNull Context context, @NonNull RecyclerAdapter recyclerAdapter, @NonNull View itemView, int position, T itemData);

    boolean onItemLongClick(@NonNull Context context, @NonNull RecyclerAdapter recyclerAdapter, @NonNull View itemView, int position, T itemData);
}
