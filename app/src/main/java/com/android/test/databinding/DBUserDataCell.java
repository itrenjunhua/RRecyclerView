package com.android.test.databinding;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.view.View;

import com.android.databinding.library.baseAdapters.BR;
import com.android.test.R;
import com.android.test.data.UserData;
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
public class DBUserDataCell extends BaseRecyclerCell<SimpleMultiItemEntity<UserData>> {

    public DBUserDataCell() {
        super(R.layout.db_item_recycler_user);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, ViewDataBinding viewDataBinding,
                                 int position, SimpleMultiItemEntity<UserData> itemData) {
        viewDataBinding.setVariable(BR.userData, itemData.getData());
    }

    @Override
    public void onItemClick(@NonNull Context context, @NonNull RecyclerAdapter recyclerAdapter,
                            @NonNull RecyclerViewHolder holder, ViewDataBinding viewDataBinding,
                            @NonNull View itemView, int position, SimpleMultiItemEntity<UserData> itemData) {
        ToastUtil.showSingleToast(context, "姓名:" + itemData.getData().userName);
    }
}
