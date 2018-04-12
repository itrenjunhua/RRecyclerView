package com.android.recyclerviewtest.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.recyclerviewtest.R;
import com.android.recyclerviewtest.adapter.ListGridAdapter;
import com.android.recyclerviewtest.adapter.SingleTypeAdapter;
import com.android.recyclerviewtest.data.DataUtil;
import com.android.recyclerviewtest.draw.CustomItemDecoration;
import com.android.recyclerviewtest.utils.RLog;
import com.android.recyclerviewtest.utils.ToastUtil;

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
    private List<String> datas;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recycler_view;
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.title);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        title.setText("水平方向网格（item 有长按事件）");

        datas = DataUtil.getTextData();
        setRecyclerView();
    }

    private void setRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3, GridLayoutManager.HORIZONTAL, false);
        ListGridAdapter adapter = new ListGridAdapter(this, datas, GridLayoutManager.HORIZONTAL);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        // 增加分割线
        recyclerView.addItemDecoration(new CustomItemDecoration().dividerHeight((int) getResources().getDimension(R.dimen.line_height))
                .isDrawFirstLowBefore(true)
                .isDrawFirstColBefore(true)
                .isDrawLastLowAfter(true)
                .isDrawLastColAfter(true)
        );

        // 给 item 添加长按事件
        adapter.setOnItemLongClickListener(new SingleTypeAdapter.OnItemLongClickListener<String>() {
            @Override
            public boolean onItemLongClick(View itemView, int position, List<String> datas, String itemData) {
                RLog.i("长按 位置:" + position + "；数据:" + itemData);
                ToastUtil.showSingleToast(Grid2Activity.this, "长按 位置:" + position + "；数据:" + itemData);
                return true;
            }
        });
    }
}
