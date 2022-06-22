package com.android.test.cell;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.android.test.R;
import com.android.test.data.UserData;
import com.android.test.utils.ToastUtil;
import com.renj.recycler.adapter.BaseRecyclerCell;
import com.renj.recycler.adapter.RecyclerAdapter;
import com.renj.recycler.adapter.RecyclerViewHolder;
import com.renj.recycler.adapter.SimpleMultiItemEntity;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-06-05   15:48
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class UserDataCell extends BaseRecyclerCell<SimpleMultiItemEntity<UserData>> {

    public UserDataCell() {
        super(R.layout.item_recycler_user);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, SimpleMultiItemEntity<UserData> itemData) {
        holder.setText(R.id.text_username, "姓名：" + itemData.getData().userName);
        holder.setText(R.id.text_age, "年龄：" + itemData.getData().age + "岁");
    }

    @Override
    public void onItemClick(@NonNull Context context, @NonNull RecyclerAdapter recyclerAdapter,
                            @NonNull RecyclerViewHolder holder, @NonNull View itemView, int position, SimpleMultiItemEntity<UserData> itemData) {
        ToastUtil.showSingleToast(context, "姓名:" + itemData.getData().userName);
    }
}
