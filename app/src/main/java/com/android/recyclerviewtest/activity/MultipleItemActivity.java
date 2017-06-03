package com.android.recyclerviewtest.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.android.recyclerviewtest.R;
import com.android.recyclerviewtest.adapter.MultipleItemAdapter;
import com.android.recyclerviewtest.data.DataUtil;
import com.android.recyclerviewtest.draw.CustomItemDecoration;

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
    private List<String> datas;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recycler_view;
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.title);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        title.setText("使用多种 item 类型（View 点击事件）");

        initDta();
        setRecyclerView();
    }

    private void setRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        MultipleItemAdapter adapter = new MultipleItemAdapter(this, datas);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        // 增加分割线
        recyclerView.addItemDecoration(new CustomItemDecoration().dividerHeight((int) getResources()
                .getDimension(R.dimen.line_height))
                .dividerColor(getResources().getColor(R.color.line_bg2)));
    }

    /**
     * 随机生成数据
     */
    private void initDta() {
        datas = new ArrayList<>();
        Random random = new Random();
        for (int i = 'A', j = 0; i <= 'Z'; i++, j++) {
            int anInt = random.nextInt(2) + 1;
            if (anInt % 2 == 0) datas.add((char) i + "");
            else datas.add(DataUtil.getImageArray()[j]);
        }
    }
}
