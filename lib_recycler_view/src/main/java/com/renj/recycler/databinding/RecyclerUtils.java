package com.renj.recycler.databinding;

import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 所在项目名: AndroidDataBinding
 * <p>
 * 所在包名: com.renj.databinding.utils
 * <p>
 * 当前类名: RecyclerUtils
 * <p>
 * 作者：Renj
 * <p>
 * 描述:DataBinding使用时数据更新自动刷新列表工具类。
 * 注意：如果Adapter种数据使用的是 {@link RecyclerBlockData} ，那么不需要再次调佣该类的方法。
 * <p>
 * 创建时间: 2021-09-07 14:09
 */
public class RecyclerUtils {
    public static ObservableList.OnListChangedCallback getListChangedCallback(final RecyclerView.Adapter adapter) {
        return new ObservableList.OnListChangedCallback() {
            @Override
            public void onChanged(ObservableList observableList) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList observableList, int i, int i1) {
                adapter.notifyItemRangeChanged(i, i1);
            }

            @Override
            public void onItemRangeInserted(ObservableList observableList, int i, int i1) {
                adapter.notifyItemRangeInserted(i, i1);
            }

            @Override
            public void onItemRangeMoved(ObservableList observableList, int i, int i1, int i2) {
                if (i2 == 1) {
                    adapter.notifyItemMoved(i, i1);
                } else {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onItemRangeRemoved(ObservableList observableList, int i, int i1) {
                adapter.notifyItemRangeRemoved(i, i1);
            }
        };
    }
}
