package com.android.test.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.android.test.R;
import com.android.test.adapter.cell.CellFactory;
import com.android.test.data.DataUtil;
import com.renj.recycler.adapter.IRecyclerCell;
import com.renj.recycler.adapter.RecyclerAdapter;
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
        title = (TextView) findViewById(R.id.title);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        title.setText("使用多种 item 类型（View 点击事件）");

        setRecyclerView();
    }

    private void setRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerAdapter adapter = new RecyclerAdapter(createCells(initDta()));

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        // 增加分割线
        recyclerView.addItemDecoration(new LinearItemDecoration(LinearLayoutManager.VERTICAL)
                .dividerHeight((int) getResources().getDimension(R.dimen.line_height))
                .dividerColor(getResources().getColor(R.color.line_bg2)));
    }

    private List createCells(List<String> dataList) {
        List<IRecyclerCell> cells = new ArrayList<>();
        for (String s : dataList) {
            if (s.length() > 1) {
                cells.add(CellFactory.createVerticalImageCell(s, glideUtils));
            } else {
                cells.add(CellFactory.createVerticalTextCell(s));
            }
        }
        return cells;
    }

    /**
     * 随机生成数据
     */
    private List<String> initDta() {
        List<String> dataList = new ArrayList<>();
        Random random = new Random();
        for (int i = 'A', j = 0; i <= 'Z'; i++, j++) {
            int anInt = random.nextInt(2) + 1;
            if (anInt % 2 == 0) dataList.add((char) i + "");
            else dataList.add(DataUtil.getImageArray()[j]);
        }
        return dataList;
    }
}
