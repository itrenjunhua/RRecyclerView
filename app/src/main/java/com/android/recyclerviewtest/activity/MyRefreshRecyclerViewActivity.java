package com.android.recyclerviewtest.activity;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.android.recyclerviewtest.R;
import com.android.recyclerviewtest.adapter.RecyclerAdapter;
import com.android.recyclerviewtest.adapter.cell.CellFactory;
import com.android.recyclerviewtest.adapter.cell.VerticalTextCell;
import com.android.recyclerviewtest.data.DataUtil;
import com.android.recyclerviewtest.draw.CustomItemDecoration;
import com.android.recyclerviewtest.utils.ToastUtil;
import com.android.recyclerviewtest.view.RefreshRecyclerView;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2017-10-18   17:48
 * <p>
 * 描述：使用自定义的刷新RecyclerView
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class MyRefreshRecyclerViewActivity extends BaseActivity {
    private TextView title;
    private RefreshRecyclerView recyclerView;
    private RecyclerAdapter<VerticalTextCell> adapter;

    // 定义加载更多的次数
    private int loadingCount = 0;

    private final static int WHAT_FINISH_REFRESHING = 1;
    private final static int WHAT_FINISH_LOADING = 2;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_FINISH_REFRESHING:
                    // 模拟数据
                    adapter.addData(0, CellFactory.createVerticalTextCell(DataUtil.refreshData(3)));
                    recyclerView.finishRefreshing();
                    ToastUtil.showSingleToast(MyRefreshRecyclerViewActivity.this, "刷新完成");
                    break;
                case WHAT_FINISH_LOADING:
                    if (loadingCount < 2) {
                        loadingCount++;
                        // 模拟数据
                        adapter.addData(CellFactory.createVerticalTextCell(DataUtil.loadMoreData(3)));
                        recyclerView.finishLoading();
                        ToastUtil.showSingleToast(MyRefreshRecyclerViewActivity.this, "加载完成");
                    } else {
                        recyclerView.setHasMore(false);
                    }
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_recyclerview;
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.title);
        recyclerView = (RefreshRecyclerView) findViewById(R.id.recyclerview);
        // recyclerView.setCanLoadMore(false);
        // recyclerView.setCanRefresh(false);

        title.setText("使用自定义的刷新RecyclerView控件");

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new RecyclerAdapter<>(CellFactory.createVerticalTextCell(DataUtil.getTextData()));

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        // 增加分割线
        recyclerView.addItemDecoration(new CustomItemDecoration().dividerHeight((int) getResources().getDimension(R.dimen.line_height))
                .dividerColor(getResources().getColor(R.color.line_bg)));

        // 刷新监听
        recyclerView.setOnRefreshListener(new RefreshRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread() {
                    @Override
                    public void run() {
                        SystemClock.sleep(2000);
                        handler.sendEmptyMessage(WHAT_FINISH_REFRESHING);
                    }
                }.start();
            }
        });
        // 加载更多监听
        recyclerView.setOnLoadMoreListener(new RefreshRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                new Thread() {
                    @Override
                    public void run() {
                        SystemClock.sleep(2000);
                        handler.sendEmptyMessage(WHAT_FINISH_LOADING);
                    }
                }.start();
            }
        });

    }
}
