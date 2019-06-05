package com.android.recyclerviewtest.adapter;

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
        return obj == null;
    }

    /* ======================== add or remove IRecyclerCell ======================== */

    public void setData(@NonNull List<T> dataList) {
        this.cellList.clear();
        if (!isEmpty(dataList))
            this.cellList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void addData(@NonNull List<T> dataList) {
        if (!isEmpty(dataList)) {
            this.cellList.addAll(dataList);
            // notifyItemRangeChanged(this.cellList.size() - dataList.size(), this.cellList.size());
            notifyItemRangeInserted(this.cellList.size(), dataList.size());
        }
    }

    public void addData(int index, @NonNull List<T> dataList) {
        if (!isEmpty(dataList)) {
            this.cellList.addAll(index, dataList);
            // notifyItemRangeChanged(index, index + dataList.size());
            notifyItemRangeChanged(index, dataList.size());
        }
    }

    public void addData(@NonNull T data) {
        if (!isEmpty(data)) {
            this.cellList.add(data);
            // notifyItemChanged(this.cellList.indexOf(data));
            notifyItemInserted(this.cellList.indexOf(data));
        }
    }

    public void addData(int index, @NonNull T data) {
        if (!isEmpty(data)) {
            this.cellList.add(index, data);
            // notifyItemChanged(index);
            notifyItemInserted(index);
        }
    }

    public void remove(@NonNull T data) {
        if (!isEmpty(data))
            remove(this.cellList.indexOf(data));
    }

    public void remove(int index) {
        this.cellList.remove(index);
        // notifyItemChanged(index);
        notifyItemRemoved(index);
    }

    public void remove(int start, int count) {
        if ((start + count) > this.cellList.size()) {
            return;
        }
        this.cellList.subList(start, start + count).clear();
        // notifyItemRangeChanged(start,count);
        notifyItemRangeRemoved(start, count);
    }

    public void clear() {
        this.cellList.clear();
        notifyDataSetChanged();
    }
}
