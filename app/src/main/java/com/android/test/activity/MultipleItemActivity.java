package com.android.test.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.android.test.R;
import com.android.test.cell.RecyclerCellType;
import com.android.test.cell.UserDataCell;
import com.android.test.cell.VerticalImageCell;
import com.android.test.cell.VerticalTextCell;
import com.android.test.data.DataUtil;
import com.android.test.data.UserData;
import com.renj.recycler.adapter.BaseRecyclerCell;
import com.renj.recycler.adapter.RecyclerAdapter;
import com.renj.recycler.adapter.SimpleMultiItemEntity;
import com.renj.recycler.draw.LinearItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2017-04-13    11:18
 * <p/>
 * 描述：使用多种 item 类型
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */

public class MultipleItemActivity extends BaseActivity {
    private TextView title;
    private RecyclerView recyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recycler_view;
    }

    @Override
    protected void initView() {
        title = findViewById(R.id.title);
        recyclerView = findViewById(R.id.recyclerview);

        title.setText("使用多种 item 类型（View 点击事件）");

        setRecyclerView();
    }

    private void setRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerAdapter<SimpleMultiItemEntity> adapter = new RecyclerAdapter<SimpleMultiItemEntity>(initDta()) {
            @NonNull
            @Override
            protected BaseRecyclerCell getRecyclerCell(int itemTypeValue) {
                if (itemTypeValue == RecyclerCellType.VERTICAL_TEXT_CELL)
                    return new VerticalTextCell();
                else if (itemTypeValue == RecyclerCellType.USER_DATA_CELL)
                    return new UserDataCell();
                else
                    return new VerticalImageCell(glideUtils);
            }
        };

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        // 增加分割线
        recyclerView.addItemDecoration(new LinearItemDecoration(LinearLayoutManager.VERTICAL)
                .dividerHeight((int) getResources().getDimension(R.dimen.line_height))
                .dividerColor(getResources().getColor(R.color.line_bg2)));
    }

    /**
     * 随机生成数据
     */
    private List<SimpleMultiItemEntity> initDta() {
        List<SimpleMultiItemEntity> dataList = new ArrayList<>();
        Random random = new Random();
        SimpleMultiItemEntity[] imageArray = DataUtil.getImageArray(RecyclerCellType.VERTICAL_IMAGE_CELL);
        for (int i = 'A', j = 0; i <= 'Z'; i++, j++) {
            int anInt = random.nextInt(3) + 1;
            if (anInt % 3 == 0)
                dataList.add(new SimpleMultiItemEntity<>(RecyclerCellType.VERTICAL_TEXT_CELL, "多种条目——文字类型 " + i));
            else if (anInt % 3 == 1) {
                dataList.add(new SimpleMultiItemEntity<>(RecyclerCellType.USER_DATA_CELL, new UserData("张三 - " + i, i)));
            } else {
                if (j < imageArray.length)
                    dataList.add(imageArray[j]);
            }
        }
        return dataList;
    }
}
