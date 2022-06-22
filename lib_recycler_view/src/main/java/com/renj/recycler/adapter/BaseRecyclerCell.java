package com.renj.recycler.adapter;

import android.content.Context;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2019-06-05   9:56
 * <p>
 * 描述：{@link androidx.recyclerview.widget.RecyclerView} 不同类型 item 实现类超类，具体实现类继承该类<br/>
 * 泛型 T 表示需要用到的数据类型
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public abstract class BaseRecyclerCell<D> {
    protected Context mContext;
    /**
     * 当前item布局资源id
     */
    final int mItemLayoutResId;

    /**
     * 构造函数
     *
     * @param itemLayoutResId 当前item布局资源id
     */
    public BaseRecyclerCell(@LayoutRes int itemLayoutResId) {
        this.mItemLayoutResId = itemLayoutResId;
    }

    /**
     * 绑定 holder
     */
    public abstract void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, D itemData);

    @SuppressWarnings("unused")
    public void onAttachedToWindow(@NonNull RecyclerViewHolder holder) {

    }

    /**
     * 如有必要，释放资源
     */
    @SuppressWarnings("unused")
    public void onDetachedFromWindow(@NonNull RecyclerViewHolder holder) {

    }

    public void onItemClick(@NonNull Context context, @NonNull RecyclerAdapter recyclerAdapter,
                            @NonNull RecyclerViewHolder holder, @NonNull View itemView, int position, D itemData) {

    }

    public boolean onItemLongClick(@NonNull Context context, @NonNull RecyclerAdapter recyclerAdapter,
                                   @NonNull RecyclerViewHolder holder, @NonNull View itemView, int position, D itemData) {
        return false;
    }
}
