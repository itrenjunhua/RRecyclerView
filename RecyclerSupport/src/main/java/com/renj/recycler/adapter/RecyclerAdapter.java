package com.renj.recycler.adapter;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2019-06-05   9:52
 * <p>
 * 描述：单一类型条目 {@link RecyclerView} 适配器封装，多种条目类型请使用 {@link MultiItemAdapter}
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public abstract class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {
    static final int ITEM_TYPE_DEFAULT = -0xFFFF;
    static final int ITEM_TYPE_NO_FOUNT = -404;
    private int mItemTypeValue;
    protected List<T> mDataList;

    /**
     * 单一条目类型，使用默认类型值 {@link #ITEM_TYPE_DEFAULT}
     */
    public RecyclerAdapter() {
        this.mItemTypeValue = ITEM_TYPE_DEFAULT;
        this.mDataList = new ArrayList<>();
    }

    /**
     * 单一条目类型，指定条目类型值
     *
     * @param itemTypeValue 当前条目类型值
     */
    public RecyclerAdapter(int itemTypeValue) {
        this.mItemTypeValue = itemTypeValue;
        this.mDataList = new ArrayList<>();
    }

    /**
     * 单一条目类型，使用默认类型值 {@link #ITEM_TYPE_DEFAULT}
     *
     * @param dataList 数据列表
     */
    public RecyclerAdapter(List<T> dataList) {
        this.mItemTypeValue = ITEM_TYPE_DEFAULT;
        this.mDataList = new ArrayList<>();
        if (dataList != null)
            this.mDataList.addAll(dataList);
    }

    /**
     * 单一条目类型，指定条目类型值和数据列表
     *
     * @param itemTypeValue 当前条目类型值
     * @param dataList      数据列表
     */
    public RecyclerAdapter(int itemTypeValue, List<T> dataList) {
        this.mItemTypeValue = itemTypeValue;
        this.mDataList = new ArrayList<>();
        if (dataList != null)
            this.mDataList.addAll(dataList);
    }

    /**
     * 获取数据列表
     *
     * @return 数据列表
     */
    @SuppressWarnings("unused")
    public List<T> getDataList() {
        return mDataList;
    }

    /**
     * 某一个位置数据
     *
     * @param position 指定位置
     * @return 指定位置的数据
     */
    @Nullable
    @SuppressWarnings("unused")
    public T getItem(@IntRange(from = 0) int position) {
        if (position >= 0 && position < mDataList.size())
            return mDataList.get(position);
        else
            return null;
    }

    @Override
    public int getItemViewType(int position) {
        return mItemTypeValue;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BaseRecyclerCell recyclerCell = getRecyclerCell(viewType);
        Objects.requireNonNull(recyclerCell);
        return new RecyclerViewHolder<>(parent, recyclerCell);
    }

    /**
     * 返回 {@link BaseRecyclerCell} 子类对象
     *
     * @param itemTypeValue 当前条目的类型，多种条目适配器 {@link MultiItemAdapter} 实例时用到，
     *                      默认值为 {@link #ITEM_TYPE_DEFAULT}
     * @return 根据 itemTypeValue 值返回对应的 {@link BaseRecyclerCell} 子类对象
     */
    @NonNull
    protected abstract <C extends BaseRecyclerCell<T>> C getRecyclerCell(int itemTypeValue);

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        final int tmpPosition = position;
        final RecyclerViewHolder finalHolder = holder;
        holder.onBindViewHolder(position, mDataList.get(position));
        holder.setOnItemViewClickListener(new RecyclerViewHolder.OnItemViewClickListener() {
            @Override
            public void onItemViewClick(View itemView) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(itemView.getContext(),
                            RecyclerAdapter.this, finalHolder, itemView,
                            tmpPosition, mDataList.get(tmpPosition));
                } else {
                    finalHolder.mItemCell.onItemClick(itemView.getContext(),
                            RecyclerAdapter.this, finalHolder, itemView,
                            tmpPosition, mDataList.get(tmpPosition));
                }
            }
        });
        holder.setOnItemViewLongClickListener(new RecyclerViewHolder.OnItemViewLongClickListener() {
            @Override
            public boolean onItemLongViewClick(View itemView) {
                if (mOnItemLongClickListener != null) {
                    return mOnItemLongClickListener.onItemLongClick(itemView.getContext(),
                            RecyclerAdapter.this, finalHolder, itemView,
                            tmpPosition, mDataList.get(tmpPosition));
                } else {
                    return finalHolder.mItemCell.onItemLongClick(itemView.getContext(),
                            RecyclerAdapter.this, finalHolder, itemView,
                            tmpPosition, mDataList.get(tmpPosition));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerViewHolder holder) {
        int adapterPosition = holder.getAdapterPosition();
        if (adapterPosition < 0 || adapterPosition >= mDataList.size())
            return;

        holder.onAttachedToWindow();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerViewHolder holder) {
        int adapterPosition = holder.getAdapterPosition();
        if (adapterPosition < 0 || adapterPosition >= mDataList.size())
            return;

        holder.onDetachedFromWindow();
    }


    private <H> boolean notEmptyList(List<H> obj) {
        return obj != null && !obj.isEmpty();
    }

    private <H> boolean isNullObject(H obj) {
        return obj == null;
    }

    /* ======================== set/add/modify/remove Data ======================== */

    /* -------------------------  set Data ------------------------- */

    /**
     * 设置数据，将原来的数据完全替换并刷新列表
     */
    @SuppressWarnings("unused")
    public void setData(@NonNull List<T> dataList) {
        this.mDataList.clear();
        if (notEmptyList(dataList))
            this.mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    /* -------------------------  add Data ------------------------- */

    /**
     * 增加数据，并调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表
     */
    @SuppressWarnings("unused")
    public void addAndNotifyAll(@NonNull List<T> dataList) {
        add(dataList, true);
    }

    /**
     * 增加数据，并调用 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表
     */
    @SuppressWarnings("unused")
    public void addAndNotifyItem(@NonNull List<T> dataList) {
        add(dataList, false);
    }

    /**
     * 增加数据
     *
     * @param refreshAllItem true：调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表<br/>
     *                       false：调用 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表
     */
    @SuppressWarnings("unused")
    public void add(@NonNull List<T> dataList, boolean refreshAllItem) {
        if (notEmptyList(dataList)) {
            if (refreshAllItem) {
                this.mDataList.addAll(dataList);
                notifyDataSetChanged();
            } else {
                int size = this.mDataList.size();
                this.mDataList.addAll(dataList);
                notifyItemRangeInserted(size, dataList.size());
            }
        }
    }

    /**
     * 增加数据，并调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表
     */
    @SuppressWarnings("unused")
    public void addAndNotifyAll(@IntRange(from = 0) int index, @NonNull List<T> dataList) {
        add(index, dataList, true);
    }

    /**
     * 增加数据，并调用 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表
     */
    @SuppressWarnings("unused")
    public void addAndNotifyItem(@IntRange(from = 0) int index, @NonNull List<T> dataList) {
        add(index, dataList, false);
    }

    /**
     * 增加数据
     *
     * @param refreshAllItem true：调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表<br/>
     *                       false：调用 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表
     */
    @SuppressWarnings("unused")
    public void add(@IntRange(from = 0) int index, @NonNull List<T> dataList, boolean refreshAllItem) {
        if (notEmptyList(dataList)) {
            if (refreshAllItem) {
                this.mDataList.addAll(index, dataList);
                notifyDataSetChanged();
            } else {
                this.mDataList.addAll(index, dataList);
                notifyItemRangeInserted(index, dataList.size());
            }
        }
    }

    /**
     * 增加数据，并调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表
     */
    @SuppressWarnings("unused")
    public void addAndNotifyAll(@NonNull T data) {
        add(data, true);
    }

    /**
     * 增加数据，并调用 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表
     */
    @SuppressWarnings("unused")
    public void addAndNotifyItem(@NonNull T data) {
        add(data, false);
    }

    /**
     * 增加数据
     *
     * @param refreshAllItem true：调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表<br/>
     *                       false：调用 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表
     */
    @SuppressWarnings("unused")
    public void add(T data, boolean refreshAllItem) {
        if (!isNullObject(data)) {
            if (refreshAllItem) {
                this.mDataList.add(data);
                notifyDataSetChanged();
            } else {
                this.mDataList.add(data);
                notifyItemInserted(this.mDataList.indexOf(data));
            }
        }
    }

    /**
     * 增加数据，并调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表
     */
    @SuppressWarnings("unused")
    public void addAndNotifyAll(@IntRange(from = 0) int index, @NonNull T data) {
        add(index, data, true);
    }

    /**
     * 增加数据，并调用 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表
     */
    @SuppressWarnings("unused")
    public void addAndNotifyItem(@IntRange(from = 0) int index, @NonNull T data) {
        add(index, data, false);
    }

    /**
     * 增加数据
     *
     * @param refreshAllItem true：调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表<br/>
     *                       false：调用 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表
     */
    @SuppressWarnings("unused")
    public void add(@IntRange(from = 0) int index, T data, boolean refreshAllItem) {
        if (index < 0) index = 0;
        if (index > mDataList.size()) index = mDataList.size();
        if (!isNullObject(data)) {
            if (refreshAllItem) {
                this.mDataList.add(index, data);
                notifyDataSetChanged();
            } else {
                this.mDataList.add(index, data);
                notifyItemInserted(index);
            }
        }
    }

    /* -------------------------  modify Data ------------------------- */

    @SuppressWarnings("unused")
    public void modifyAndNotifyAll(@IntRange(from = 0) int index, @NonNull T data) {
        modify(index, data, true);
    }

    @SuppressWarnings("unused")
    public void modifyAndNotifyItem(@IntRange(from = 0) int index, @NonNull T data) {
        modify(index, data, false);
    }

    /**
     * 修改数据
     *
     * @param refreshAllItem true：调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表<br/>
     *                       false：调用 {@link RecyclerAdapter#notifyItemChanged(int)} 方法刷新列表
     */
    @SuppressWarnings("unused")
    public void modify(@IntRange(from = 0) int index, T data, boolean refreshAllItem) {
        if (index < 0 || index >= this.mDataList.size())
            return;
        if (isNullObject(data))
            return;

        this.mDataList.set(index, data);
        if (refreshAllItem)
            notifyDataSetChanged();
        else
            notifyItemChanged(index);
    }

    /* -------------------------  remove Data ------------------------- */

    /**
     * 移除数据，调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表
     */
    @SuppressWarnings("unused")
    public void removeAndNotifyAll(@NonNull T data) {
        remove(data, true);
    }

    /**
     * 移除数据，调用 {@link RecyclerAdapter#notifyItemRemoved(int)}
     * 和 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表
     */
    @SuppressWarnings("unused")
    public void removeAndNotifyItem(@NonNull T data) {
        remove(data, false);
    }

    /**
     * 移除数据
     *
     * @param refreshAllItem true：调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表<br/>
     *                       false：调用 {@link RecyclerAdapter#notifyItemRemoved(int)}
     *                       和 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表
     */
    @SuppressWarnings("unused")
    public void remove(T data, boolean refreshAllItem) {
        if (!isNullObject(data))
            remove(this.mDataList.indexOf(data), refreshAllItem);
    }

    /**
     * 移除数据，调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表
     */
    @SuppressWarnings("unused")
    public void removeAndNotifyAll(@IntRange(from = 0) int index) {
        remove(index, true);
    }

    /**
     * 移除数据，调用 {@link RecyclerAdapter#notifyItemRemoved(int)}
     * 和 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表
     */
    @SuppressWarnings("unused")
    public void removeAndNotifyItem(@IntRange(from = 0) int index) {
        remove(index, false);
    }

    /**
     * 移除数据
     *
     * @param refreshAllItem true：调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表<br/>
     *                       false：调用 {@link RecyclerAdapter#notifyItemRemoved(int)}
     *                       和 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表
     */
    @SuppressWarnings("unused")
    public void remove(@IntRange(from = 0) int index, boolean refreshAllItem) {
        if (index < 0 || index >= this.mDataList.size())
            return;

        if (refreshAllItem) {
            this.mDataList.remove(index);
            notifyDataSetChanged();
        } else {
            this.mDataList.remove(index);
            notifyItemRemoved(index);
            // 重新排列位置，防止删除错乱 和 IndexOutOfIndexException 等问题
            notifyItemRangeChanged(index, this.mDataList.size() - index);
        }
    }

    /**
     * 移除数据，调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表
     */
    @SuppressWarnings("unused")
    public void removeAndNotifyAll(@IntRange(from = 0) int start, int count) {
        remove(start, count, true);
    }

    /**
     * 移除数据，调用 {@link RecyclerAdapter#notifyItemRangeRemoved(int, int)}
     * 和 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表
     */
    @SuppressWarnings("unused")
    public void removeAndNotifyItem(@IntRange(from = 0) int start, int count) {
        remove(start, count, false);
    }

    /**
     * 移除数据
     *
     * @param refreshAllItem true：调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表<br/>
     *                       false：调用 {@link RecyclerAdapter#notifyItemRangeRemoved(int, int)}
     *                       和 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表
     */
    @SuppressWarnings("unused")
    public void remove(@IntRange(from = 0) int start, @IntRange(from = 0) int count, boolean refreshAllItem) {
        if (count <= 0) return;
        if (start < 0) start = 0;
        if ((start + count) > this.mDataList.size()) {
            return;
        }
        if (refreshAllItem) {
            this.mDataList.subList(start, start + count).clear();
            notifyDataSetChanged();
        } else {
            this.mDataList.subList(start, start + count).clear();
            notifyItemRangeRemoved(start, count);
            // 重新排列位置，防止删除错乱 和 IndexOutOfIndexException 等问题
            notifyItemRangeChanged(start, this.mDataList.size() - start);
        }
    }

    /**
     * 清空数据并刷新列表
     */
    @SuppressWarnings("unused")
    public void clear() {
        this.mDataList.clear();
        notifyDataSetChanged();
    }


    /* ====================== item click listener event ======================= */
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    /**
     * 设置单击监听，优先级高于 {@link BaseRecyclerCell#onItemClick(Context, RecyclerAdapter, RecyclerViewHolder, View, int, Object)}
     *
     * @param onItemClickListener {@link OnItemClickListener} 对象
     */
    @SuppressWarnings("unused")
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    /**
     * 设置长按监听，优先级高于 {@link BaseRecyclerCell#onItemLongClick(Context, RecyclerAdapter, RecyclerViewHolder, View, int, Object)}
     *
     * @param onItemLongClickListener {@link OnItemLongClickListener} 对象
     */
    @SuppressWarnings("unused")
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    /**
     * item 单击监听接口
     */
    public interface OnItemClickListener {
        void onItemClick(@NonNull Context context, @NonNull RecyclerAdapter recyclerAdapter,
                         @NonNull RecyclerViewHolder holder, @NonNull View itemView, int position, Object itemData);
    }

    /**
     * item 长按监听接口
     */
    public interface OnItemLongClickListener {
        boolean onItemLongClick(@NonNull Context context, @NonNull RecyclerAdapter recyclerAdapter,
                                @NonNull RecyclerViewHolder holder, @NonNull View itemView, int position, Object itemData);
    }
}
