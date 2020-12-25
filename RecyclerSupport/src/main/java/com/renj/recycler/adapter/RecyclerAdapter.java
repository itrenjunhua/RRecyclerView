package com.renj.recycler.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-06-05   9:52
 * <p>
 * 描述：{@link RecyclerView} 适配器封装，内部包含 {@link BaseRecyclerCell} 列表
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class RecyclerAdapter<T extends BaseRecyclerCell> extends RecyclerView.Adapter<RecyclerViewHolder> {
    public static final int TYPE_NOT_FOUND = -404;
    private List<T> mCellList;
    private SparseIntArray mItemLayoutResIds = new SparseIntArray();

    public RecyclerAdapter() {
        this.mCellList = new ArrayList<>();
    }

    public RecyclerAdapter(@NonNull List<T> cellList) {
        this.mCellList = new ArrayList<>();
        this.mCellList.addAll(cellList);
    }

    public List<T> getCellList() {
        return mCellList;
    }

    @Override
    public int getItemViewType(int position) {
        T itemCell = mCellList.get(position);
        if (mItemLayoutResIds.get(itemCell.mItemType, TYPE_NOT_FOUND) == TYPE_NOT_FOUND)
            mItemLayoutResIds.put(itemCell.mItemType, itemCell.mItemLayoutResId);
        return itemCell.mItemType;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(mItemLayoutResIds.get(viewType), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        final int tmpPosition = position;
        final T cell = this.mCellList.get(position);
        final RecyclerViewHolder viewHolder = holder;
        viewHolder.setOnItemViewClickListener(new RecyclerViewHolder.OnItemViewClickListener() {
            @Override
            public void onItemViewClick(View itemView) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(itemView.getContext(), RecyclerAdapter.this, viewHolder, itemView, tmpPosition, cell.getItemData());
                } else {
                    cell.onItemClick(itemView.getContext(), RecyclerAdapter.this, viewHolder, itemView, tmpPosition, cell.getItemData());
                }
            }
        });
        viewHolder.setOnItemViewLongClickListener(new RecyclerViewHolder.OnItemViewLongClickListener() {
            @Override
            public boolean onItemLongViewClick(View itemView) {
                if (mOnItemLongClickListener != null) {
                    return mOnItemLongClickListener.onItemLongClick(itemView.getContext(), RecyclerAdapter.this, viewHolder, itemView, tmpPosition, cell.getItemData());
                } else {
                    return cell.onItemLongClick(itemView.getContext(), RecyclerAdapter.this, viewHolder, itemView, tmpPosition, cell.getItemData());
                }
            }
        });
        mCellList.get(position).onBindViewHolder(viewHolder, position, cell.getItemData());
    }

    @Override
    public int getItemCount() {
        return mCellList.size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerViewHolder holder) {
        int adapterPosition = holder.getAdapterPosition();
        if (adapterPosition < 0 || adapterPosition >= mCellList.size())
            return;

        mCellList.get(adapterPosition).onAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerViewHolder holder) {
        int adapterPosition = holder.getAdapterPosition();
        if (adapterPosition < 0 || adapterPosition >= mCellList.size())
            return;

        mCellList.get(adapterPosition).onDetachedFromWindow(holder);
    }


    private <T> boolean isEmptyList(List<T> obj) {
        return obj == null || obj.isEmpty();
    }

    private <T> boolean isNullObject(T obj) {
        return obj == null;
    }

    /* ======================== set/add/modify/remove IRecyclerCell ======================== */

    /* -------------------------  set IRecyclerCell ------------------------- */

    /**
     * 设置数据，将原来的数据完全替换并刷新列表
     */
    public void setData(@NonNull List<T> dataList) {
        this.mCellList.clear();
        if (!isEmptyList(dataList))
            this.mCellList.addAll(dataList);
        notifyDataSetChanged();
    }

    /* -------------------------  add IRecyclerCell ------------------------- */

    /**
     * 增加数据，并调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表
     */
    public void addAndNotifyAll(@NonNull List<T> dataList) {
        add(dataList, true);
    }

    /**
     * 增加数据，并调用 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表
     */
    public void addAndNotifyItem(@NonNull List<T> dataList) {
        add(dataList, false);
    }

    /**
     * 增加数据
     *
     * @param refreshAllItem true：调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表<br/>
     *                       false：调用 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表
     */
    public void add(@NonNull List<T> dataList, boolean refreshAllItem) {
        if (!isEmptyList(dataList)) {
            if (refreshAllItem) {
                this.mCellList.addAll(dataList);
                notifyDataSetChanged();
            } else {
                int size = this.mCellList.size();
                this.mCellList.addAll(dataList);
                notifyItemRangeInserted(size, dataList.size());
            }
        }
    }

    /**
     * 增加数据，并调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表
     */
    public void addAndNotifyAll(int index, @NonNull List<T> dataList) {
        add(index, dataList, true);
    }

    /**
     * 增加数据，并调用 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表
     */
    public void addAndNotifyItem(int index, @NonNull List<T> dataList) {
        add(index, dataList, false);
    }

    /**
     * 增加数据
     *
     * @param refreshAllItem true：调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表<br/>
     *                       false：调用 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表
     */
    public void add(int index, @NonNull List<T> dataList, boolean refreshAllItem) {
        if (!isEmptyList(dataList)) {
            if (refreshAllItem) {
                this.mCellList.addAll(index, dataList);
                notifyDataSetChanged();
            } else {
                this.mCellList.addAll(index, dataList);
                notifyItemRangeInserted(index, dataList.size());
            }
        }
    }

    /**
     * 增加数据，并调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表
     */
    public void addAndNotifyAll(@NonNull T data) {
        add(data, true);
    }

    /**
     * 增加数据，并调用 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表
     */
    public void addAndNotifyItem(@NonNull T data) {
        add(data, false);
    }

    /**
     * 增加数据
     *
     * @param refreshAllItem true：调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表<br/>
     *                       false：调用 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表
     */
    public void add(@NonNull T data, boolean refreshAllItem) {
        if (!isNullObject(data)) {
            if (refreshAllItem) {
                this.mCellList.add(data);
                notifyDataSetChanged();
            } else {
                this.mCellList.add(data);
                notifyItemInserted(this.mCellList.indexOf(data));
            }
        }
    }

    /**
     * 增加数据，并调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表
     */
    public void addAndNotifyAll(int index, @NonNull T data) {
        add(index, data, true);
    }

    /**
     * 增加数据，并调用 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表
     */
    public void addAndNotifyItem(int index, @NonNull T data) {
        add(index, data, false);
    }

    /**
     * 增加数据
     *
     * @param refreshAllItem true：调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表<br/>
     *                       false：调用 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表
     */
    public void add(int index, @NonNull T data, boolean refreshAllItem) {
        if (!isNullObject(data)) {
            if (refreshAllItem) {
                this.mCellList.add(index, data);
                notifyDataSetChanged();
            } else {
                this.mCellList.add(index, data);
                notifyItemInserted(index);
            }
        }
    }

    /* -------------------------  modify IRecyclerCell ------------------------- */

    public void modifyAndNotifyAll(int index, @NonNull T data) {
        modify(index, data, true);
    }

    public void modifyAndNotifyItem(int index, @NonNull T data) {
        modify(index, data, false);
    }

    /**
     * 修改数据
     *
     * @param refreshAllItem true：调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表<br/>
     *                       false：调用 {@link RecyclerAdapter#notifyItemChanged(int)} 方法刷新列表
     */
    public void modify(int index, @NonNull T data, boolean refreshAllItem) {
        if (index < 0 || index >= this.mCellList.size())
            return;
        if (isNullObject(data))
            return;

        this.mCellList.set(index, data);
        if (refreshAllItem)
            notifyDataSetChanged();
        else
            notifyItemChanged(index);
    }

    /* -------------------------  remove IRecyclerCell ------------------------- */

    /**
     * 移除数据，调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表
     */
    public void removeAndNotifyAll(@NonNull T data) {
        remove(data, true);
    }

    /**
     * 移除数据，调用 {@link RecyclerAdapter#notifyItemRemoved(int)}
     * 和 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表
     */
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
    public void remove(@NonNull T data, boolean refreshAllItem) {
        if (!isNullObject(data))
            remove(this.mCellList.indexOf(data), refreshAllItem);
    }

    /**
     * 移除数据，调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表
     */
    public void removeAndNotifyAll(int index) {
        remove(index, true);
    }

    /**
     * 移除数据，调用 {@link RecyclerAdapter#notifyItemRemoved(int)}
     * 和 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表
     */
    public void removeAndNotifyItem(int index) {
        remove(index, false);
    }

    /**
     * 移除数据
     *
     * @param refreshAllItem true：调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表<br/>
     *                       false：调用 {@link RecyclerAdapter#notifyItemRemoved(int)}
     *                       和 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表
     */
    public void remove(int index, boolean refreshAllItem) {
        if (index < 0 || index >= this.mCellList.size())
            return;

        if (refreshAllItem) {
            this.mCellList.remove(index);
            notifyDataSetChanged();
        } else {
            this.mCellList.remove(index);
            notifyItemRemoved(index);
            // 重新排列位置，防止删除错乱 和 IndexOutOfIndexException 等问题
            notifyItemRangeChanged(index, this.mCellList.size() - index);
        }
    }

    /**
     * 移除数据，调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表
     */
    public void removeAndNotifyAll(int start, int count) {
        remove(start, count, true);
    }

    /**
     * 移除数据，调用 {@link RecyclerAdapter#notifyItemRangeRemoved(int, int)}
     * 和 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表
     */
    public void removeAndNotifyItem(int start, int count) {
        remove(start, count, false);
    }

    /**
     * 移除数据
     *
     * @param refreshAllItem true：调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表<br/>
     *                       false：调用 {@link RecyclerAdapter#notifyItemRangeRemoved(int, int)}
     *                       和 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表
     */
    public void remove(int start, int count, boolean refreshAllItem) {
        if ((start + count) > this.mCellList.size()) {
            return;
        }
        if (refreshAllItem) {
            this.mCellList.subList(start, start + count).clear();
            notifyDataSetChanged();
        } else {
            this.mCellList.subList(start, start + count).clear();
            notifyItemRangeRemoved(start, count);
            // 重新排列位置，防止删除错乱 和 IndexOutOfIndexException 等问题
            notifyItemRangeChanged(start, this.mCellList.size() - start);
        }
    }

    /**
     * 清空数据并刷新列表
     */
    public void clear() {
        this.mCellList.clear();
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
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    /**
     * 设置长按监听，优先级高于 {@link BaseRecyclerCell#onItemLongClick(Context, RecyclerAdapter, RecyclerViewHolder, View, int, Object)}
     *
     * @param onItemLongClickListener {@link OnItemLongClickListener} 对象
     */
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
