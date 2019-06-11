package com.renj.recycler.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
 * 描述：{@link RecyclerView} 适配器封装，内部包含 {@link IRecyclerCell} 列表
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class RecyclerAdapter<T extends IRecyclerCell> extends RecyclerView.Adapter<RecyclerViewHolder> {
    private List<T> cellList;

    public RecyclerAdapter() {
        this.cellList = new ArrayList<>();
    }

    public RecyclerAdapter(@NonNull List<T> cellList) {
        if (isEmpty(this.cellList))
            this.cellList = new ArrayList<>();
        else
            this.cellList.clear();

        if (!isEmpty(cellList))
            this.cellList.addAll(cellList);
    }

    public List<T> getCellList() {
        return cellList;
    }

    @Override
    public int getItemViewType(int position) {
        return cellList.get(position).getRecyclerItemType();
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        for (T cell : cellList) {
            if (viewType == cell.getRecyclerItemType()) {
                return cell.onCreateViewHolder(parent.getContext(), parent, viewType);
            }
        }
        throw new IllegalStateException("This itemViewType:" + viewType + " is not found.See if it is defined.");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        final int tmpPosition = position;
        final T cell = this.cellList.get(position);
        holder.setOnItemViewClickListener(new RecyclerViewHolder.OnItemViewClickListener() {
            @Override
            public void onItemViewClick(View itemView) {
                cell.onItemClick(itemView.getContext(), RecyclerAdapter.this, itemView, tmpPosition, cell.getItemData());
            }
        });
        holder.setOnItemViewLongClickListener(new RecyclerViewHolder.OnItemViewLongClickListener() {
            @Override
            public boolean onItemLongViewClick(View itemView) {
                return cell.onItemLongClick(itemView.getContext(), RecyclerAdapter.this, itemView, tmpPosition, cell.getItemData());
            }
        });
        cellList.get(position).onBindViewHolder(holder, position, cell.getItemData());
    }

    @Override
    public int getItemCount() {
        return cellList.size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerViewHolder holder) {
        int adapterPosition = holder.getAdapterPosition();
        if (adapterPosition < 0 || adapterPosition >= cellList.size())
            return;

        cellList.get(adapterPosition).onAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerViewHolder holder) {
        int adapterPosition = holder.getAdapterPosition();
        if (adapterPosition < 0 || adapterPosition >= cellList.size())
            return;

        cellList.get(adapterPosition).onDetachedFromWindow(holder);
    }


    private boolean isEmpty(Object obj) {
        if (obj == null)
            return true;
        if (obj instanceof List) {
            List list = (List) obj;
            return list.isEmpty();
        }
        return false;
    }

    /* ======================== set/add/modify/remove IRecyclerCell ======================== */

    /* -------------------------  set IRecyclerCell ------------------------- */

    /**
     * 设置数据，将原来的数据完全替换并刷新列表
     */
    public void setData(@NonNull List<T> dataList) {
        this.cellList.clear();
        if (!isEmpty(dataList))
            this.cellList.addAll(dataList);
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
        if (!isEmpty(dataList)) {
            if (refreshAllItem) {
                this.cellList.addAll(dataList);
                notifyDataSetChanged();
            } else {
                int size = this.cellList.size();
                this.cellList.addAll(dataList);
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
        if (!isEmpty(dataList)) {
            if (refreshAllItem) {
                this.cellList.addAll(index, dataList);
                notifyDataSetChanged();
            } else {
                this.cellList.addAll(index, dataList);
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
        if (!isEmpty(data)) {
            if (refreshAllItem) {
                this.cellList.add(data);
                notifyDataSetChanged();
            } else {
                this.cellList.add(data);
                notifyItemInserted(this.cellList.indexOf(data));
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
        if (!isEmpty(data)) {
            if (refreshAllItem) {
                this.cellList.add(index, data);
                notifyDataSetChanged();
            } else {
                this.cellList.add(index, data);
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
        if (index < 0 || index >= this.cellList.size())
            return;
        if (isEmpty(data))
            return;

        this.cellList.set(index, data);
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
        if (!isEmpty(data))
            remove(this.cellList.indexOf(data), refreshAllItem);
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
        if (index < 0 || index >= this.cellList.size())
            return;

        if (refreshAllItem) {
            this.cellList.remove(index);
            notifyDataSetChanged();
        } else {
            this.cellList.remove(index);
            notifyItemRemoved(index);
            // 重新排列位置，防止删除错乱 和 IndexOutOfIndexException 等问题
            notifyItemRangeChanged(index, this.cellList.size() - index);
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
        if ((start + count) > this.cellList.size()) {
            return;
        }
        if (refreshAllItem) {
            this.cellList.subList(start, start + count).clear();
            notifyDataSetChanged();
        } else {
            this.cellList.subList(start, start + count).clear();
            notifyItemRangeRemoved(start, count);
            // 重新排列位置，防止删除错乱 和 IndexOutOfIndexException 等问题
            notifyItemRangeChanged(start, this.cellList.size() - start);
        }
    }

    /**
     * 清空数据并刷新列表
     */
    public void clear() {
        this.cellList.clear();
        notifyDataSetChanged();
    }
}
