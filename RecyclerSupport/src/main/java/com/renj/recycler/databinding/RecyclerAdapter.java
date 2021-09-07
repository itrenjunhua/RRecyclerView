package com.renj.recycler.databinding;

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
 * 描述： {@link RecyclerView} 适配器封装
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public abstract class RecyclerAdapter<D> extends RecyclerView.Adapter<RecyclerViewHolder<D, ? extends BaseRecyclerCell>> {
    static final int ITEM_TYPE_DEFAULT = -0xFFFF;

    // 是否需要按块加载数据的列表
    private boolean mRecyclerBlockLoad = false;
    private int mItemTypeValue;
    protected List<D> mDataList;

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
    public RecyclerAdapter(List<D> dataList) {
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
    public RecyclerAdapter(int itemTypeValue, List<D> dataList) {
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
    public List<D> getDataList() {
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
    public D getItem(@IntRange(from = 0) int position) {
        if (position >= 0 && position < mDataList.size())
            return mDataList.get(position);
        else
            return null;
    }

    /**
     * 返回条目类型，如果是多条目类型且数据不是 {@link MultiItemEntity} 子类时需要子类重写
     *
     * @param position 当前条目所在位置
     * @return 条目类型值
     */
    @Override
    public int getItemViewType(int position) {
        D itemData = mDataList.get(position);
        if (itemData instanceof MultiItemEntity)
            return ((MultiItemEntity) itemData).getItemType();
        return mItemTypeValue;
    }

    @NonNull
    @Override
    public RecyclerViewHolder<D, ? extends BaseRecyclerCell> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BaseRecyclerCell recyclerCell = getRecyclerCell(viewType);
        Objects.requireNonNull(recyclerCell);
        return new RecyclerViewHolder(parent, recyclerCell);
    }

    /**
     * 返回 {@link BaseRecyclerCell} 子类对象
     *
     * @param itemTypeValue 当前条目的类型，{@link #getItemViewType(int)} 方法返回值，单一条目时使用默认值： {@link #ITEM_TYPE_DEFAULT}
     * @return 根据 itemTypeValue 值返回对应的 {@link BaseRecyclerCell} 子类对象
     */
    @NonNull
    protected abstract BaseRecyclerCell getRecyclerCell(int itemTypeValue);

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder<D, ? extends BaseRecyclerCell> holder, int position) {
        final int tmpPosition = position;
        final RecyclerViewHolder<D, ? extends BaseRecyclerCell> finalHolder = holder;
        holder.onBindViewHolder(position, mDataList.get(position));
        holder.setOnItemViewClickListener(new RecyclerViewHolder.OnItemViewClickListener() {
            @Override
            public void onItemViewClick(View itemView) {
                if (mOnItemClickListener != null) {
                    if (mOnItemClickListener.onItemClick(itemView.getContext(),
                            RecyclerAdapter.this, finalHolder, itemView,
                            tmpPosition, mDataList.get(tmpPosition))) {

                        finalHolder.mItemCell.onItemClick(itemView.getContext(),
                                RecyclerAdapter.this, finalHolder, finalHolder.viewDataBinding,
                                itemView, tmpPosition, mDataList.get(tmpPosition));
                    }
                } else {
                    finalHolder.mItemCell.onItemClick(itemView.getContext(),
                            RecyclerAdapter.this, finalHolder, finalHolder.viewDataBinding,
                            itemView, tmpPosition, mDataList.get(tmpPosition));
                }
            }
        });
        holder.setOnItemViewLongClickListener(new RecyclerViewHolder.OnItemViewLongClickListener() {
            @Override
            public boolean onItemLongViewClick(View itemView) {
                if (mOnItemLongClickListener != null) {
                    boolean itemLongClick = mOnItemLongClickListener.onItemLongClick(itemView.getContext(),
                            RecyclerAdapter.this, finalHolder, itemView,
                            tmpPosition, mDataList.get(tmpPosition));

                    if (itemLongClick) {
                        return finalHolder.mItemCell.onItemLongClick(itemView.getContext(),
                                RecyclerAdapter.this, finalHolder, finalHolder.viewDataBinding,
                                itemView, tmpPosition, mDataList.get(tmpPosition));
                    }
                    return false;
                } else {
                    return finalHolder.mItemCell.onItemLongClick(itemView.getContext(),
                            RecyclerAdapter.this, finalHolder, finalHolder.viewDataBinding,
                            itemView, tmpPosition, mDataList.get(tmpPosition));
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

    /**
     * 设置按块加载数据
     */
    public void setRecyclerBlockData(RecyclerBlockData<D> recyclerBlockData) {
        if (recyclerBlockData != null) {
            mRecyclerBlockLoad = true;
            recyclerBlockData.setRecyclerAdapter(this);
        }
    }

    /**
     * 清除按块加载的数据并刷新列表
     */
    public void clearRecyclerBlockData() {
        clearRecyclerBlockData(true);
    }

    /**
     * 清除按块加载的数据并指定是否需要刷新列表
     *
     * @param notifyAdapter 是否需要刷新列表  true：刷新  false：不刷新
     */
    public void clearRecyclerBlockData(boolean notifyAdapter) {
        mRecyclerBlockLoad = false;
        clear(notifyAdapter);
    }

    /* -------------------------  set Data ------------------------- */

    /**
     * 设置数据，将原来的数据完全替换并刷新列表<br/>
     * <b>注意：如果先调用了 {@link #setRecyclerBlockData(RecyclerBlockData)} 方法并传递的参数为非 {@code null}，那么该方法无效；
     * 如果想要生效，就需要调用 {@link #clearRecyclerBlockData()} 或者 {@link #clearRecyclerBlockData(boolean)} 方法清除分块加载的数据。</b>
     */
    @SuppressWarnings("unused")
    public void setData(@NonNull List<D> dataList) {
        setData(dataList, true);
    }

    void setData(@NonNull List<D> dataList, boolean filterRecyclerBlockData) {
        if (mRecyclerBlockLoad && filterRecyclerBlockData) return;

        this.mDataList.clear();
        if (notEmptyList(dataList))
            this.mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    /* -------------------------  add Data ------------------------- */

    /**
     * 增加数据，并调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表<br/>
     * <b>注意：如果先调用了 {@link #setRecyclerBlockData(RecyclerBlockData)} 方法并传递的参数为非 {@code null}，那么该方法无效；
     * 如果想要生效，就需要调用 {@link #clearRecyclerBlockData()} 或者 {@link #clearRecyclerBlockData(boolean)} 方法清除分块加载的数据。</b>
     */
    @SuppressWarnings("unused")
    public void addAndNotifyAll(@NonNull List<D> dataList) {
        add(dataList, true);
    }

    /**
     * 增加数据，并调用 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表<br/>
     * <b>注意：如果先调用了 {@link #setRecyclerBlockData(RecyclerBlockData)} 方法并传递的参数为非 {@code null}，那么该方法无效；
     * 如果想要生效，就需要调用 {@link #clearRecyclerBlockData()} 或者 {@link #clearRecyclerBlockData(boolean)} 方法清除分块加载的数据。</b>
     */
    @SuppressWarnings("unused")
    public void addAndNotifyItem(@NonNull List<D> dataList) {
        add(dataList, false);
    }

    /**
     * 增加数据<br/>
     * <b>注意：如果先调用了 {@link #setRecyclerBlockData(RecyclerBlockData)} 方法并传递的参数为非 {@code null}，那么该方法无效；
     * 如果想要生效，就需要调用 {@link #clearRecyclerBlockData()} 或者 {@link #clearRecyclerBlockData(boolean)} 方法清除分块加载的数据。</b>
     *
     * @param refreshAllItem true：调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表<br/>
     *                       false：调用 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表
     */
    @SuppressWarnings("unused")
    public void add(@NonNull List<D> dataList, boolean refreshAllItem) {
        add(dataList, refreshAllItem, true);
    }

    void add(@NonNull List<D> dataList, boolean refreshAllItem, boolean filterRecyclerBlockData) {
        if (mRecyclerBlockLoad && filterRecyclerBlockData) return;

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
     * 增加数据，并调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表<br/>
     * <b>注意：如果先调用了 {@link #setRecyclerBlockData(RecyclerBlockData)} 方法并传递的参数为非 {@code null}，那么该方法无效；
     * 如果想要生效，就需要调用 {@link #clearRecyclerBlockData()} 或者 {@link #clearRecyclerBlockData(boolean)} 方法清除分块加载的数据。</b>
     */
    @SuppressWarnings("unused")
    public void addAndNotifyAll(@IntRange(from = 0) int index, @NonNull List<D> dataList) {
        add(index, dataList, true);
    }

    /**
     * 增加数据，并调用 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表<br/>
     * <b>注意：如果先调用了 {@link #setRecyclerBlockData(RecyclerBlockData)} 方法并传递的参数为非 {@code null}，那么该方法无效；
     * 如果想要生效，就需要调用 {@link #clearRecyclerBlockData()} 或者 {@link #clearRecyclerBlockData(boolean)} 方法清除分块加载的数据。</b>
     */
    @SuppressWarnings("unused")
    public void addAndNotifyItem(@IntRange(from = 0) int index, @NonNull List<D> dataList) {
        add(index, dataList, false);
    }

    /**
     * 增加数据<br/>
     * <b>注意：如果先调用了 {@link #setRecyclerBlockData(RecyclerBlockData)} 方法并传递的参数为非 {@code null}，那么该方法无效；
     * 如果想要生效，就需要调用 {@link #clearRecyclerBlockData()} 或者 {@link #clearRecyclerBlockData(boolean)} 方法清除分块加载的数据。</b>
     *
     * @param refreshAllItem true：调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表<br/>
     *                       false：调用 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表
     */
    @SuppressWarnings("unused")
    public void add(@IntRange(from = 0) int index, @NonNull List<D> dataList, boolean refreshAllItem) {
        add(index, dataList, refreshAllItem, true);
    }

    void add(@IntRange(from = 0) int index, @NonNull List<D> dataList, boolean refreshAllItem, boolean filterRecyclerBlockData) {
        if (mRecyclerBlockLoad && filterRecyclerBlockData) return;

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
     * 增加数据，并调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表<br/>
     * <b>注意：如果先调用了 {@link #setRecyclerBlockData(RecyclerBlockData)} 方法并传递的参数为非 {@code null}，那么该方法无效；
     * 如果想要生效，就需要调用 {@link #clearRecyclerBlockData()} 或者 {@link #clearRecyclerBlockData(boolean)} 方法清除分块加载的数据。</b>
     */
    @SuppressWarnings("unused")
    public void addAndNotifyAll(@NonNull D data) {
        add(data, true);
    }

    /**
     * 增加数据，并调用 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表<br/>
     * <b>注意：如果先调用了 {@link #setRecyclerBlockData(RecyclerBlockData)} 方法并传递的参数为非 {@code null}，那么该方法无效；
     * 如果想要生效，就需要调用 {@link #clearRecyclerBlockData()} 或者 {@link #clearRecyclerBlockData(boolean)} 方法清除分块加载的数据。</b>
     */
    @SuppressWarnings("unused")
    public void addAndNotifyItem(@NonNull D data) {
        add(data, false);
    }

    /**
     * 增加数据<br/>
     * <b>注意：如果先调用了 {@link #setRecyclerBlockData(RecyclerBlockData)} 方法并传递的参数为非 {@code null}，那么该方法无效；
     * 如果想要生效，就需要调用 {@link #clearRecyclerBlockData()} 或者 {@link #clearRecyclerBlockData(boolean)} 方法清除分块加载的数据。</b>
     *
     * @param refreshAllItem true：调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表<br/>
     *                       false：调用 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表
     */
    @SuppressWarnings("unused")
    public void add(D data, boolean refreshAllItem) {
        add(data, refreshAllItem, true);
    }

    void add(D data, boolean refreshAllItem, boolean filterRecyclerBlockData) {
        if (mRecyclerBlockLoad && filterRecyclerBlockData) return;

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
     * 增加数据，并调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表<br/>
     * <b>注意：如果先调用了 {@link #setRecyclerBlockData(RecyclerBlockData)} 方法并传递的参数为非 {@code null}，那么该方法无效；
     * 如果想要生效，就需要调用 {@link #clearRecyclerBlockData()} 或者 {@link #clearRecyclerBlockData(boolean)} 方法清除分块加载的数据。</b>
     */
    @SuppressWarnings("unused")
    public void addAndNotifyAll(@IntRange(from = 0) int index, @NonNull D data) {
        add(index, data, true);
    }

    /**
     * 增加数据，并调用 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表<br/>
     * <b>注意：如果先调用了 {@link #setRecyclerBlockData(RecyclerBlockData)} 方法并传递的参数为非 {@code null}，那么该方法无效；
     * 如果想要生效，就需要调用 {@link #clearRecyclerBlockData()} 或者 {@link #clearRecyclerBlockData(boolean)} 方法清除分块加载的数据。</b>
     */
    @SuppressWarnings("unused")
    public void addAndNotifyItem(@IntRange(from = 0) int index, @NonNull D data) {
        add(index, data, false);
    }

    /**
     * 增加数据<br/>
     * <b>注意：如果先调用了 {@link #setRecyclerBlockData(RecyclerBlockData)} 方法并传递的参数为非 {@code null}，那么该方法无效；
     * 如果想要生效，就需要调用 {@link #clearRecyclerBlockData()} 或者 {@link #clearRecyclerBlockData(boolean)} 方法清除分块加载的数据。</b>
     *
     * @param refreshAllItem true：调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表<br/>
     *                       false：调用 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表
     */
    @SuppressWarnings("unused")
    public void add(@IntRange(from = 0) int index, D data, boolean refreshAllItem) {
        add(index, data, refreshAllItem, true);
    }

    void add(@IntRange(from = 0) int index, D data, boolean refreshAllItem, boolean filterRecyclerBlockData) {
        if (mRecyclerBlockLoad && filterRecyclerBlockData) return;

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

    /**
     * 修改数据，并调用{@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表<br/>
     * <b>注意：如果先调用了 {@link #setRecyclerBlockData(RecyclerBlockData)} 方法并传递的参数为非 {@code null}，那么该方法无效；
     * 如果想要生效，就需要调用 {@link #clearRecyclerBlockData()} 或者 {@link #clearRecyclerBlockData(boolean)} 方法清除分块加载的数据。</b>
     */
    @SuppressWarnings("unused")
    public void modifyAndNotifyAll(@IntRange(from = 0) int index, @NonNull D data) {
        modify(index, data, true);
    }

    /**
     * 修改数据并调用 {@link RecyclerAdapter#notifyItemChanged(int)} 方法刷新列表
     * <b>注意：如果先调用了 {@link #setRecyclerBlockData(RecyclerBlockData)} 方法并传递的参数为非 {@code null}，那么该方法无效；
     * 如果想要生效，就需要调用 {@link #clearRecyclerBlockData()} 或者 {@link #clearRecyclerBlockData(boolean)} 方法清除分块加载的数据。</b>
     */
    @SuppressWarnings("unused")
    public void modifyAndNotifyItem(@IntRange(from = 0) int index, @NonNull D data) {
        modify(index, data, false);
    }

    /**
     * 修改数据<br/>
     * <b>注意：如果先调用了 {@link #setRecyclerBlockData(RecyclerBlockData)} 方法并传递的参数为非 {@code null}，那么该方法无效；
     * 如果想要生效，就需要调用 {@link #clearRecyclerBlockData()} 或者 {@link #clearRecyclerBlockData(boolean)} 方法清除分块加载的数据。</b>
     *
     * @param refreshAllItem true：调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表<br/>
     *                       false：调用 {@link RecyclerAdapter#notifyItemChanged(int)} 方法刷新列表
     */
    @SuppressWarnings("unused")
    public void modify(@IntRange(from = 0) int index, D data, boolean refreshAllItem) {
        modify(index, data, refreshAllItem, true);
    }

    void modify(@IntRange(from = 0) int index, D data, boolean refreshAllItem, boolean filterRecyclerBlockData) {
        if (mRecyclerBlockLoad && filterRecyclerBlockData) return;

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
     * 移除数据，调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表<br/>
     * <b>注意：如果先调用了 {@link #setRecyclerBlockData(RecyclerBlockData)} 方法并传递的参数为非 {@code null}，那么该方法无效；
     * 如果想要生效，就需要调用 {@link #clearRecyclerBlockData()} 或者 {@link #clearRecyclerBlockData(boolean)} 方法清除分块加载的数据。</b>
     */
    @SuppressWarnings("unused")
    public void removeAndNotifyAll(@NonNull D data) {
        remove(data, true);
    }

    /**
     * 移除数据，调用 {@link RecyclerAdapter#notifyItemRemoved(int)}
     * 和 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表<br/>
     * <b>注意：如果先调用了 {@link #setRecyclerBlockData(RecyclerBlockData)} 方法并传递的参数为非 {@code null}，那么该方法无效；
     * 如果想要生效，就需要调用 {@link #clearRecyclerBlockData()} 或者 {@link #clearRecyclerBlockData(boolean)} 方法清除分块加载的数据。</b>
     */
    @SuppressWarnings("unused")
    public void removeAndNotifyItem(@NonNull D data) {
        remove(data, false);
    }

    /**
     * 移除数据<br/>
     * <b>注意：如果先调用了 {@link #setRecyclerBlockData(RecyclerBlockData)} 方法并传递的参数为非 {@code null}，那么该方法无效；
     * 如果想要生效，就需要调用 {@link #clearRecyclerBlockData()} 或者 {@link #clearRecyclerBlockData(boolean)} 方法清除分块加载的数据。</b>
     *
     * @param refreshAllItem true：调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表<br/>
     *                       false：调用 {@link RecyclerAdapter#notifyItemRemoved(int)}
     *                       和 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表
     */
    @SuppressWarnings("unused")
    public void remove(D data, boolean refreshAllItem) {
        remove(data, refreshAllItem, true);
    }

    void remove(D data, boolean refreshAllItem, boolean filterRecyclerBlockData) {
        if (mRecyclerBlockLoad && filterRecyclerBlockData) return;

        if (!isNullObject(data))
            remove(this.mDataList.indexOf(data), refreshAllItem, filterRecyclerBlockData);
    }

    /**
     * 移除数据，调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表<br/>
     * <b>注意：如果先调用了 {@link #setRecyclerBlockData(RecyclerBlockData)} 方法并传递的参数为非 {@code null}，那么该方法无效；
     * 如果想要生效，就需要调用 {@link #clearRecyclerBlockData()} 或者 {@link #clearRecyclerBlockData(boolean)} 方法清除分块加载的数据。</b>
     */
    @SuppressWarnings("unused")
    public void removeAndNotifyAll(@IntRange(from = 0) int index) {
        remove(index, true);
    }

    /**
     * 移除数据，调用 {@link RecyclerAdapter#notifyItemRemoved(int)}
     * 和 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表<br/>
     * <b>注意：如果先调用了 {@link #setRecyclerBlockData(RecyclerBlockData)} 方法并传递的参数为非 {@code null}，那么该方法无效；
     * 如果想要生效，就需要调用 {@link #clearRecyclerBlockData()} 或者 {@link #clearRecyclerBlockData(boolean)} 方法清除分块加载的数据。</b>
     */
    @SuppressWarnings("unused")
    public void removeAndNotifyItem(@IntRange(from = 0) int index) {
        remove(index, false);
    }

    /**
     * 移除数据<br/>
     * <b>注意：如果先调用了 {@link #setRecyclerBlockData(RecyclerBlockData)} 方法并传递的参数为非 {@code null}，那么该方法无效；
     * 如果想要生效，就需要调用 {@link #clearRecyclerBlockData()} 或者 {@link #clearRecyclerBlockData(boolean)} 方法清除分块加载的数据。</b>
     *
     * @param refreshAllItem true：调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表<br/>
     *                       false：调用 {@link RecyclerAdapter#notifyItemRemoved(int)}
     *                       和 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表
     */
    @SuppressWarnings("unused")
    public void remove(@IntRange(from = 0) int index, boolean refreshAllItem) {
        remove(index, refreshAllItem, true);
    }

    void remove(@IntRange(from = 0) int index, boolean refreshAllItem, boolean filterRecyclerBlockData) {
        if (mRecyclerBlockLoad && filterRecyclerBlockData) return;

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
     * 移除数据，调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表<br/>
     * <b>注意：如果先调用了 {@link #setRecyclerBlockData(RecyclerBlockData)} 方法并传递的参数为非 {@code null}，那么该方法无效；
     * 如果想要生效，就需要调用 {@link #clearRecyclerBlockData()} 或者 {@link #clearRecyclerBlockData(boolean)} 方法清除分块加载的数据。</b>
     */
    @SuppressWarnings("unused")
    public void removeAndNotifyAll(@IntRange(from = 0) int start, int count) {
        remove(start, count, true);
    }

    /**
     * 移除数据，调用 {@link RecyclerAdapter#notifyItemRangeRemoved(int, int)}
     * 和 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表<br/>
     * <b>注意：如果先调用了 {@link #setRecyclerBlockData(RecyclerBlockData)} 方法并传递的参数为非 {@code null}，那么该方法无效；
     * 如果想要生效，就需要调用 {@link #clearRecyclerBlockData()} 或者 {@link #clearRecyclerBlockData(boolean)} 方法清除分块加载的数据。</b>
     */
    @SuppressWarnings("unused")
    public void removeAndNotifyItem(@IntRange(from = 0) int start, int count) {
        remove(start, count, false);
    }

    /**
     * 移除数据<br/>
     * <b>注意：如果先调用了 {@link #setRecyclerBlockData(RecyclerBlockData)} 方法并传递的参数为非 {@code null}，那么该方法无效；
     * 如果想要生效，就需要调用 {@link #clearRecyclerBlockData()} 或者 {@link #clearRecyclerBlockData(boolean)} 方法清除分块加载的数据。</b>
     *
     * @param refreshAllItem true：调用 {@link RecyclerAdapter#notifyDataSetChanged()} 方法刷新列表<br/>
     *                       false：调用 {@link RecyclerAdapter#notifyItemRangeRemoved(int, int)}
     *                       和 {@link RecyclerAdapter#notifyItemRangeInserted(int, int)} 方法刷新列表
     */
    @SuppressWarnings("unused")
    public void remove(@IntRange(from = 0) int start, @IntRange(from = 0) int count, boolean refreshAllItem) {
        remove(start, count, refreshAllItem, true);
    }

    void remove(@IntRange(from = 0) int start, @IntRange(from = 0) int count, boolean refreshAllItem, boolean filterRecyclerBlockData) {
        if (mRecyclerBlockLoad && filterRecyclerBlockData) return;

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
        clear(true);
    }

    /**
     * 清空数据并指定是否需要刷新列表
     *
     * @param notifyAdapter 是否需要刷新列表  true：刷新  false：不刷新
     */
    @SuppressWarnings("unused")
    public void clear(boolean notifyAdapter) {
        this.mDataList.clear();
        if (notifyAdapter)
            notifyDataSetChanged();
    }


    /* ====================== item click listener event ======================= */
    private OnItemClickListener<D> mOnItemClickListener;
    private OnItemLongClickListener<D> mOnItemLongClickListener;

    /**
     * 设置单击监听，优先级高于 {@link BaseRecyclerCell#onItemClick(Context, RecyclerAdapter, RecyclerViewHolder,
     * android.databinding.ViewDataBinding, View, int, Object)}
     *
     * @param onItemClickListener {@link OnItemClickListener} 对象
     */
    @SuppressWarnings("unused")
    public void setOnItemClickListener(OnItemClickListener<D> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    /**
     * 设置长按监听，优先级高于 {@link BaseRecyclerCell#onItemLongClick(Context, RecyclerAdapter, RecyclerViewHolder,
     * android.databinding.ViewDataBinding, View, int, Object)}
     *
     * @param onItemLongClickListener {@link OnItemLongClickListener} 对象
     */
    @SuppressWarnings("unused")
    public void setOnItemLongClickListener(OnItemLongClickListener<D> onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    /**
     * item 单击监听接口
     */
    public interface OnItemClickListener<D> {
        /**
         * item 点击监听
         *
         * @param context         上下文
         * @param recyclerAdapter 适配器对象
         * @param holder          封装的Holder对象
         * @param itemView        item 根布局
         * @param position        点击位置
         * @param itemData        item 数据
         * @return 根据返回结果确定是否需要继续响应 {@link BaseRecyclerCell#onItemClick(Context, RecyclerAdapter,
         * RecyclerViewHolder, android.databinding.ViewDataBinding, View, int, Object)} 方法，true：继续响应 ； false：不继续响应
         */
        boolean onItemClick(@NonNull Context context, @NonNull RecyclerAdapter recyclerAdapter,
                            @NonNull RecyclerViewHolder holder, @NonNull View itemView, int position, D itemData);
    }

    /**
     * item 长按监听接口
     */
    public interface OnItemLongClickListener<D> {
        /**
         * item 长按监听
         *
         * @param context         上下文
         * @param recyclerAdapter 适配器对象
         * @param holder          封装的Holder对象
         * @param itemView        item 根布局
         * @param position        点击位置
         * @param itemData        item 数据
         * @return 根据返回结果确定是否需要继续响应 {@link BaseRecyclerCell#onItemLongClick(Context, RecyclerAdapter,
         * RecyclerViewHolder, android.databinding.ViewDataBinding, View, int, Object)} 方法，true：继续响应 ； false：不继续响应<br/><br/>
         * <b>注意：<br/>
         * 如果返回false，那么表示item的 {@link View.OnLongClickListener} 的回调方法也会直接返回false；<br/>
         * 如果返回true，那么item的 {@link View.OnLongClickListener} 的回调方法返回结果就是 {@link BaseRecyclerCell#onItemLongClick(Context, RecyclerAdapter,
         * RecyclerViewHolder, android.databinding.ViewDataBinding, View, int, Object)} 方法 返回的结果</b>
         */
        boolean onItemLongClick(@NonNull Context context, @NonNull RecyclerAdapter recyclerAdapter,
                                @NonNull RecyclerViewHolder holder, @NonNull View itemView, int position, D itemData);
    }
}
