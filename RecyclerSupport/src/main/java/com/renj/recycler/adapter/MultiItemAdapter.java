package com.renj.recycler.adapter;

import java.util.List;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2020-12-28   15:58
 * <p>
 * 描述：多种条目类型时使用的适配器
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public abstract class MultiItemAdapter<T extends MultiItemEntity> extends RecyclerAdapter<T> {

    public MultiItemAdapter() {
        super();
    }

    public MultiItemAdapter(List<T> dataList) {
        super(dataList);
    }

    @Override
    public int getItemViewType(int position) {
        return mDataList.get(position).getItemType();
    }
}
