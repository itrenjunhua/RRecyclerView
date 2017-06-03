package com.android.recyclerviewtest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2017-04-06    11:11
 * <p/>
 * 描述：只有一种 item 类型时的RecyclerView.Adapter
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
public abstract class SingleTypeAdapter<T> extends RecyclerView.Adapter<CustomViewHolder> {
    protected Context context;
    protected List<T> mDatas;

    private int layoutID;
    private OnItemClickListener<T> mOnItemClickListener;
    private OnItemLongClickListener<T> mOnItemLongClickListener;

    protected SingleTypeAdapter(Context context) {
        this.context = context;
    }

    protected SingleTypeAdapter(Context context, List<T> datas) {
        this(context);
        this.mDatas = datas;
    }

    public SingleTypeAdapter(Context context, int layoutID) {
        this(context);
        this.layoutID = layoutID;
    }

    public SingleTypeAdapter(Context context, List<T> datas, int layoutID) {
        this(context, layoutID);
        this.mDatas = datas;
    }

    /**
     * 设置单击监听
     *
     * @param onItemClickListener {@link SingleTypeAdapter.OnItemClickListener} 对象
     */
    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    /**
     * 设置长按监听
     *
     * @param onItemLongClickListener {@link SingleTypeAdapter.OnItemLongClickListener} 对象
     */
    public void setOnItemLongClickListener(OnItemLongClickListener<T> onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    /**
     * 重新设置 List 集合数据
     *
     * @param datas
     */
    public void resetDatas(List<T> datas) {
        if (this.mDatas != null) {
            this.mDatas.clear();
        } else {
            this.mDatas = new ArrayList<T>();
        }
        if (datas != null) {
            this.mDatas.addAll(datas);
            notifyDataSetChanged();
        }
    }

    /**
     * 在 List 集合指定位置新增数据
     *
     * @param datas
     */
    public void addDatas(int index, List<T> datas) {
        if (this.mDatas == null) {
            this.mDatas = new ArrayList<T>();
        }
        if (datas != null) {
            this.mDatas.addAll(index, datas);
            notifyDataSetChanged();
        }
    }

    /**
     * 在 List 集合最后追加数据
     *
     * @param datas
     */
    public void addDatas(List<T> datas) {
        if (this.mDatas == null) {
            this.mDatas = new ArrayList<T>();
        }
        if (datas != null) {
            this.mDatas.addAll(datas);
            notifyDataSetChanged();
        }
    }

    /**
     * 获取 Adapter 中的数据
     *
     * @return
     */
    public List<T> getDatas() {
        return this.mDatas;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(layoutID, parent, false);
        return new CustomViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final T itemData = mDatas.get(position);
        final int temp = position;
        holder.setOnItemViewClickListener(new CustomViewHolder.OnItemViewClickListener() {
            @Override
            public void onItemViewClick(View itemView) {
                if (null != mOnItemClickListener)
                    mOnItemClickListener.onItemClick(itemView, temp, mDatas, itemData);
            }
        });

        holder.setOnItemLongViewClickListener(new CustomViewHolder.OnItemLongViewClickListener() {
            @Override
            public boolean onItemLongViewClick(View itemView) {
                if (null != mOnItemLongClickListener)
                    return mOnItemLongClickListener.onItemLongClick(itemView, temp, mDatas, itemData);
                return false;
            }
        });
        setData(holder, itemData, position);
    }

    /**
     * 设置 itemView 中的数据
     *
     * @param holder   当前 item 的 ViewHolder 对象
     * @param itemData 当前 item 的数据
     * @param position
     */
    public abstract void setData(CustomViewHolder holder, T itemData, int position);

    /**
     * item 单击监听
     */
    public interface OnItemClickListener<T> {
        /**
         * @param itemView 单击的item View
         * @param position 单击的位置
         * @param datas    所有Adapter中的数据
         * @param itemData 单击位置的数据
         */
        void onItemClick(View itemView, int position, List<T> datas, T itemData);
    }


    /**
     * item 长按监听
     */
    public interface OnItemLongClickListener<T> {
        /**
         * @param itemView 长按的item View
         * @param position 长按的位置
         * @param datas    所有Adapter中的数据
         * @param itemData 长按位置的数据
         * @return true表示处理长按事件，false表示不处理长按事件
         */
        boolean onItemLongClick(View itemView, int position, List<T> datas, T itemData);
    }
}
