package com.android.test.databinding;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

import com.android.test.BR;
import com.android.test.R;
import com.android.test.utils.ToastUtil;
import com.renj.recycler.databinding.BaseRecyclerCell;
import com.renj.recycler.databinding.RecyclerAdapter;
import com.renj.recycler.databinding.RecyclerViewHolder;
import com.renj.recycler.databinding.SimpleMultiItemEntity;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2019-06-05   15:48
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class DBVerticalImageCell extends BaseRecyclerCell<SimpleMultiItemEntity<String>> {

    public DBVerticalImageCell() {
        super(R.layout.db_item_multiple_img);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, ViewDataBinding viewDataBinding,
                                 int position, SimpleMultiItemEntity<String> itemData) {
        viewDataBinding.setVariable(BR.itemValue, itemData.getData());
    }

    @Override
    public void onItemClick(@NonNull Context context, @NonNull RecyclerAdapter recyclerAdapter,
                            @NonNull RecyclerViewHolder holder, ViewDataBinding viewDataBinding,
                            @NonNull View itemView, int position, SimpleMultiItemEntity<String> itemData) {
        ToastUtil.showSingleToast(context, "图片链接 - " + itemData.getData());
    }
}
