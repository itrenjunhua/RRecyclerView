package com.android.recyclerviewtest.adapter;

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
public abstract class RecyclerCell<T> implements IRecyclerCell {
    /**
     * item 数据
     */
    protected T itemData;

    public RecyclerCell(T itemData) {
        this.itemData = itemData;
    }

    @Override
    public void onAttachedToWindow(@NonNull RecyclerViewHolder holder) {

    }

    @Override
    public void addEventAndBindHolder(@NonNull RecyclerViewHolder holder, int position) {
        final int tmpPosition = position;
        holder.setOnItemViewClickListener(new RecyclerViewHolder.OnItemViewClickListener() {
            @Override
            public void onItemViewClick(View itemView) {
                if (onItemClickListener != null) {
                    onItemClickListener.<T>onItemClick(itemView, tmpPosition, itemData);
                }
            }
        });
        holder.setOnItemViewLongClickListener(new RecyclerViewHolder.OnItemViewLongClickListener() {
            @Override
            public boolean onItemLongViewClick(View itemView) {
                if (onItemLongClickListener != null) {
                    return onItemLongClickListener.<T>onItemLongClick(itemView, tmpPosition, itemData);
                }
                return false;
            }
        });
        onBindViewHolder(holder, position);
    }

    /**
     * 如有必要，释放资源
     *
     * @param holder
     */
    @Override
    public void onDetachedFromWindow(@NonNull RecyclerViewHolder holder) {

    }

    /* ====================== item click listener event ======================= */
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    @Override
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

}
