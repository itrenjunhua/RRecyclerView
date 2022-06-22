package com.android.test.activity;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.test.R;
import com.android.test.cell.HorizontalTextCell;
import com.android.test.cell.RecyclerCellType;
import com.android.test.data.DataUtil;
import com.renj.recycler.adapter.BaseRecyclerCell;
import com.renj.recycler.adapter.RecyclerAdapter;
import com.renj.recycler.adapter.SimpleMultiItemEntity;
import com.renj.recycler.draw.GridItemDecoration;

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

        List<SimpleMultiItemEntity> textData = DataUtil.getTextData(RecyclerCellType.HORIZONTAL_TEXT_CELL);
        textData.add(0, new SimpleMultiItemEntity(RecyclerCellType.HORIZONTAL_TEXT_CELL, "aa"));
        textData.add(new SimpleMultiItemEntity(RecyclerCellType.HORIZONTAL_TEXT_CELL, "AA"));
        textData.add(new SimpleMultiItemEntity(RecyclerCellType.HORIZONTAL_TEXT_CELL, "BB"));
        RecyclerAdapter adapter = new RecyclerAdapter(textData) {
            @NonNull
            @Override
            protected BaseRecyclerCell getRecyclerCell(int itemTypeValue) {
                return new HorizontalTextCell();
            }
        };

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        // 增加分割线
        recyclerView.addItemDecoration(new GridItemDecoration(GridLayoutManager.HORIZONTAL).dividerHeight(40, 30/*(int) getResources().getDimension(R.dimen.line_height)*/)
                .dividerColor(getResources().getColor(R.color.colorH), getResources().getColor(R.color.colorV), getResources().getColor(R.color.colorP))
                .dividerRowAndColHeight(40, 50)
                .drawFirstRowBefore(true, getResources().getDrawable(R.drawable.shape_divider_bg))
                .drawFirstColBefore(true, getResources().getColor(R.color.colorFirstCol))
                .drawLastRowAfter(true, getResources().getDrawable(R.drawable.shape_divider_bg))
                .drawLastColAfter(true, getResources().getColor(R.color.colorLastCol))
                .borderCrossPointColor(getResources().getColor(R.color.colorBorderPoint))
        );
    }
}
