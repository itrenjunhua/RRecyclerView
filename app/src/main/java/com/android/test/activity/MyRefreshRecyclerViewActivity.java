package com.android.test.activity;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.test.R;
import com.android.test.cell.RecyclerCellType;
import com.android.test.cell.VerticalTextCell;
import com.android.test.data.DataUtil;
import com.android.test.utils.ToastUtil;
import com.android.test.view.RefreshRecyclerView;
import com.renj.recycler.adapter.BaseRecyclerCell;
import com.renj.recycler.adapter.RecyclerAdapter;
import com.renj.recycler.adapter.SimpleMultiItemEntity;
import com.renj.recycler.draw.LinearItemDecoration;

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
    private RecyclerAdapter<SimpleMultiItemEntity> adapter;

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
                    adapter.addAndNotifyAll(0, DataUtil.refreshData(RecyclerCellType.VERTICAL_TEXT_CELL, 3));
                    recyclerView.finishRefreshing();
                    ToastUtil.showSingleToast(MyRefreshRecyclerViewActivity.this, "刷新完成");
                    break;
                case WHAT_FINISH_LOADING:
                    if (loadingCount < 2) {
                        loadingCount++;
                        // 模拟数据
                        adapter.addAndNotifyAll(DataUtil.loadMoreData(RecyclerCellType.VERTICAL_TEXT_CELL, 3));
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
        adapter = new RecyclerAdapter(DataUtil.getTextData(RecyclerCellType.VERTICAL_TEXT_CELL)) {
            @NonNull
            @Override
            protected BaseRecyclerCell getRecyclerCell(int itemTypeValue) {
                return new VerticalTextCell();
            }
        };

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        // 增加分割线
        recyclerView.addItemDecoration(new LinearItemDecoration(LinearLayoutManager.VERTICAL)
                .dividerHeight((int) getResources().getDimension(R.dimen.line_height))
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
