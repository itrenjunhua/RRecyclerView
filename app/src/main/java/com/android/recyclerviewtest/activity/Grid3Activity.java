package com.android.recyclerviewtest.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.android.recyclerviewtest.R;
import com.android.recyclerviewtest.adapter.RecyclerAdapter;
import com.android.recyclerviewtest.adapter.cell.GridTextCell;
import com.android.recyclerviewtest.data.DataUtil;

import java.util.ArrayList;
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


        List<GridTextCell> cells = new ArrayList<>();
        List<String> textData = DataUtil.getTextData();
        textData.add(0, "aa");
        textData.add("AA");
        textData.add("BB");
        for (String data : textData) {
            GridTextCell gridTextCell = new GridTextCell(data);
            cells.add(gridTextCell);
        }
        RecyclerAdapter adapter = new RecyclerAdapter(cells);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(layoutManager);
    }
}
