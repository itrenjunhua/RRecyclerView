package com.android.test.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.android.test.R;
import com.android.test.cell.GridTextCell;
import com.android.test.cell.RecyclerCellType;
import com.android.test.data.DataUtil;
import com.renj.recycler.adapter.SimpleMultiItemEntity;
import com.renj.recycler.adapter.BaseRecyclerCell;
import com.renj.recycler.adapter.RecyclerAdapter;
import com.renj.recycler.draw.GridItemDecoration;

import java.util.List;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2017-04-13    10:04
 * <p/>
 * 描述：活用Grid布局类型
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */

public class Grid3Activity extends BaseActivity {
    private TextView title;
    private RecyclerView recyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recycler_view;
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.title);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        title.setText("活用网络布局类型（View 点击事件）");
        setRecyclerView();
    }

    private void setRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 6, GridLayoutManager.VERTICAL, false);

        // 设置当前位置占用的列数
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            // 该方法返回 position 位置的 item 占用 GridLayout 的多少列
            @Override
            public int getSpanSize(int position) {
                if (position <= 3) {
                    return 6;
                } else if (position <= 9) {
                    return 3;
                } else if (position <= 15) {
                    return 2;
                } else if (position <= 27) {
                    return 1;
                } else if (position <= 35) {
                    return 3;
                } else if (position <= 39) {
                    return 6;
                } else {
                    return 1;
                }
            }
        });


        List<SimpleMultiItemEntity> textData = DataUtil.getTextData(RecyclerCellType.GRID_TEXT_CELL);
        textData.add(0, new SimpleMultiItemEntity<>(RecyclerCellType.GRID_TEXT_CELL, "aa"));
        textData.add(new SimpleMultiItemEntity<>(RecyclerCellType.GRID_TEXT_CELL, "AA"));
        textData.add(new SimpleMultiItemEntity<>(RecyclerCellType.GRID_TEXT_CELL, "BB"));
        RecyclerAdapter<SimpleMultiItemEntity> adapter = new RecyclerAdapter<SimpleMultiItemEntity>(textData){
            @NonNull
            @Override
            protected BaseRecyclerCell getRecyclerCell(int itemTypeValue) {
                return new GridTextCell();
            }
        };

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        // 增加分割线
        recyclerView.addItemDecoration(new GridItemDecoration(GridLayoutManager.VERTICAL).dividerHeight(40, 30/*(int) getResources().getDimension(R.dimen.line_height)*/)
                .dividerColor(getResources().getColor(R.color.colorH), getResources().getColor(R.color.colorV), getResources().getColor(R.color.colorP))
                .dividerRowAndColHeight(40,50)
                .drawFirstRowBefore(true, getResources().getColor(R.color.colorFirstLow))
                //.drawFirstColBefore(true, getResources().getColor(R.color.colorFirstCol))
                .drawLastRowAfter(true, getResources().getColor(R.color.colorLastLow))
                .drawLastColAfter(true, getResources().getColor(R.color.colorLastCol))
                .borderCrossPointColor(getResources().getColor(R.color.colorBorderPoint))
        );
    }
}
