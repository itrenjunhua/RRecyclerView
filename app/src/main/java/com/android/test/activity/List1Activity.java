package com.android.test.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.android.test.R;
import com.android.test.cell.RecyclerCellType;
import com.android.test.cell.VerticalTextCell;
import com.android.test.data.DataUtil;
import com.renj.recycler.adapter.SimpleMultiItemEntity;
import com.renj.recycler.adapter.BaseRecyclerCell;
import com.renj.recycler.adapter.RecyclerAdapter;
import com.renj.recycler.draw.LinearItemDecoration;

import java.util.List;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2017-04-12    16:44
 * <p/>
 * 描述：垂直方向列表
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */

public class List1Activity extends BaseActivity {
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

        title.setText("垂直方向列表（item 有点击事件）");

        setRecyclerView();
    }

    private void setRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        List<SimpleMultiItemEntity> textData = DataUtil.getTextData(RecyclerCellType.VERTICAL_TEXT_CELL);
        RecyclerAdapter adapter = new RecyclerAdapter(textData) {
            @NonNull
            @Override
            protected BaseRecyclerCell getRecyclerCell(int itemTypeValue) {
                return new VerticalTextCell();
            }
        };

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        // 增加分割线
        recyclerView.addItemDecoration(new LinearItemDecoration(RecyclerView.VERTICAL)
                .dividerHeight((int) getResources().getDimension(R.dimen.line_height))
                .dividerDrawable(getResources().getDrawable(R.drawable.shape_divider_bg))
                .dividerRowAndColHeight(40, 40)
                .drawFirstRowBefore(true, getResources().getColor(R.color.colorFirstLow))
                .drawFirstColBefore(true, getResources().getColor(R.color.colorFirstCol))
                .drawLastRowAfter(true, getResources().getColor(R.color.colorLastLow))
                .drawLastColAfter(true, getResources().getColor(R.color.colorLastCol))
                .borderCrossPointColor(getResources().getColor(R.color.colorBorderPoint))
        );
    }
}
