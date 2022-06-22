package com.android.test.activity;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.test.R;
import com.android.test.cell.RecyclerCellType;
import com.android.test.cell.VerticalTextCell;
import com.android.test.data.DataUtil;
import com.android.test.utils.ToastUtil;
import com.renj.recycler.adapter.BaseRecyclerCell;
import com.renj.recycler.adapter.RecyclerAdapter;
import com.renj.recycler.adapter.SimpleMultiItemEntity;
import com.renj.recycler.draw.LinearItemDecoration;

import java.util.List;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2017-04-13    14:57
 * <p/>
 * 描述：RefreshActivity 刷新与自动加载更多（使用系统的SwipeRefreshLayout控件）
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */

public class RefreshActivity extends BaseActivity {
    private TextView title;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private RecyclerAdapter<SimpleMultiItemEntity> adapter;
    private LinearLayoutManager layoutManager;
    private int lastVisibleItem; // 当前页面最后一个显示的position
    private boolean isLoading = false; // 是否正在加载更多标记

    private static final int MSG_REFRESH = 0x001;
    private static final int MSG_LOAD_MORE = 0x002;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_REFRESH:
                    ToastUtil.showSingleToast(RefreshActivity.this, "刷新完成");
                    List<SimpleMultiItemEntity> refreshData = (List<SimpleMultiItemEntity>) msg.obj;
                    adapter.addAndNotifyAll(0, refreshData);

                    // 停止刷新动画
                    swipeRefreshLayout.setRefreshing(false);
                    break;
                case MSG_LOAD_MORE:
                    isLoading = false;
                    ToastUtil.showSingleToast(RefreshActivity.this, "加载更多完成");
                    List<SimpleMultiItemEntity> loadMoreData = (List<SimpleMultiItemEntity>) msg.obj;
                    adapter.addAndNotifyAll(loadMoreData);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_refresh;
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.title);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        title.setText("刷新与自动加载更多");

        setRecyclerView();
    }

    private void setRecyclerView() {
        List<SimpleMultiItemEntity> textData = DataUtil.getTextData(RecyclerCellType.VERTICAL_TEXT_CELL);
        adapter = new RecyclerAdapter(textData){
            @NonNull
            @Override
            protected BaseRecyclerCell getRecyclerCell(int itemTypeValue) {
                return new VerticalTextCell();
            }
        };
        layoutManager = new LinearLayoutManager(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        // 增加分割线
        recyclerView.addItemDecoration(new LinearItemDecoration(LinearLayoutManager.VERTICAL)
                .dividerHeight((int) getResources().getDimension(R.dimen.line_height))
                .dividerColor(getResources().getColor(R.color.line_bg)));

        // 改变颜色，还可设置其他属性
        swipeRefreshLayout.setColorSchemeColors(0x80FF0000, 0x8000FF00, 0x800000FF);

        // 设置刷新监听
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ToastUtil.showSingleToast(RefreshActivity.this, "开始刷新");

                // 开启一个新的线程休眠2秒模拟从网络获取数据
                runThread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(2000);
                        Message msg = Message.obtain();
                        msg.what = MSG_REFRESH;
                        msg.obj = DataUtil.refreshData(RecyclerCellType.VERTICAL_TEXT_CELL, 3);
                        handler.sendMessage(msg);
                    }
                });
            }
        });

        // 增加滚动监听，用于实现加载更多
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /**
             * 当滚动状态发生变化时回调
             * @param recyclerView RecyclerView对象
             * @param newState 改变后的状态，几种取值<br/>
             *                 &nbsp;&nbsp;&nbsp;&nbsp; 0(SCROLL_STATE_IDLE)：当前屏幕停止滚动；<br/>
             *                 &nbsp;&nbsp;&nbsp;&nbsp; 1(SCROLL_STATE_DRAGGING)：屏幕在滚动 且 用户仍在触碰或手指还在屏幕上；<br/>
             *                 &nbsp;&nbsp;&nbsp;&nbsp; 2(SCROLL_STATE_SETTLING)：随用户的操作，屏幕上产生的惯性滑动。
             */
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // 滑动状态为停止并且剩余 item 数少于两个时，自动加载更多数据
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 2 >= layoutManager
                        .getItemCount()) {
                    // 判断当前是否正在加载更多数据，只有不是加载更多数据时才能开启线程加载更多数据
                    if (!isLoading) {
                        isLoading = true;
                        ToastUtil.showSingleToast(RefreshActivity.this, "自动加载更多...");

                        // 开启一个新的线程休眠2秒模拟从网络获取数据
                        runThread(new Runnable() {
                            @Override
                            public void run() {
                                SystemClock.sleep(2000);
                                Message msg = Message.obtain();
                                msg.what = MSG_LOAD_MORE;
                                msg.obj = DataUtil.loadMoreData(RecyclerCellType.VERTICAL_TEXT_CELL, 2);
                                handler.sendMessage(msg);
                            }
                        });
                    }
                }
            }

            /**
             * 正在滚动时调用
             * @param recyclerView
             * @param dx
             * @param dy
             */
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // 获取加载的最后一个可见视图在适配器的位置。
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private void runThread(Runnable runnable) {
        new Thread(runnable).start();
    }
}
