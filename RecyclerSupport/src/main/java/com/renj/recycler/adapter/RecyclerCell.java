package com.renj.recycler.adapter;

import android.content.Context;
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
public abstract class RecyclerCell<T> implements IRecyclerCell<T> {
    /**
     * item 数据
     */
    protected T itemData;

    public RecyclerCell(T itemData) {
        this.itemData = itemData;
    }

    @Override
    public T getItemData() {
        return itemData;
    }

    @Override
    public void onAttachedToWindow(@NonNull RecyclerViewHolder holder) {

    }

    /**
     * 如有必要，释放资源
     *
     * @param holder
     */
    @Override
    public void onDetachedFromWindow(@NonNull RecyclerViewHolder holder) {

    }

    @Override
    public void onItemClick(@NonNull Context context, @NonNull RecyclerAdapter recyclerAdapter, @NonNull View itemView, int position, T itemData) {

    }

    @Override
    public boolean onItemLongClick(@NonNull Context context, @NonNull RecyclerAdapter recyclerAdapter, @NonNull View itemView, int position, T itemData) {
        return false;
    }
}
