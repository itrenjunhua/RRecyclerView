package com.android.recyclerviewtest.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.android.recyclerviewtest.R;
import com.android.recyclerviewtest.adapter.RecyclerAdapter;
import com.android.recyclerviewtest.adapter.cell.HorizontalTextCell;
import com.android.recyclerviewtest.data.DataUtil;
import com.android.recyclerviewtest.draw.CustomItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2017-04-12    16:44
 * <p/>
 * 描述：水平方向网格
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */

public class Grid2Activity extends BaseActivity {
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

        title.setText("水平方向网格（item 有长按事件）");

        setRecyclerView();
    }

    private void setRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3, GridLayoutManager.HORIZONTAL, false);

        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0)
                    return 2;
                return 1;
            }
        });

        List<HorizontalTextCell> cells = new ArrayList<>();
        List<String> textData = DataUtil.getTextData();
        textData.add(0, "aa");
        textData.add("AA");
        textData.add("BB");
        for (String data : textData) {
            HorizontalTextCell horizontalTextCell = new HorizontalTextCell(data);
            cells.add(horizontalTextCell);
        }

        RecyclerAdapter adapter = new RecyclerAdapter(cells);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(layoutManager);

        // 增加分割线
        recyclerView.addItemDecoration(new CustomItemDecoration().dividerHeight(40, (int) getResources().getDimension(R.dimen.line_height))
                .dividerColor(getResources().getColor(R.color.colorH), getResources().getColor(R.color.colorV), getResources().getColor(R.color.colorP))
                .dividerColor(getResources().getColor(R.color.colorH), getResources().getColor(R.color.colorV), getResources().getColor(R.color.colorP))
                .isDrawFirstLowBefore(true, getResources().getColor(R.color.colorFirstLow), 50)
                .isDrawFirstColBefore(true, getResources().getColor(R.color.colorFirstCol), 28)
                .isDrawLastLowAfter(true, getResources().getColor(R.color.colorLastLow), 20)
                .isDrawLastColAfterColor(true, getResources().getColor(R.color.colorLastCol))
                .borderCrossPointColor(getResources().getColor(R.color.colorBorderPoint))
        );

    }
}
