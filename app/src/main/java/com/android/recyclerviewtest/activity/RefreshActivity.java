package com.android.recyclerviewtest.activity;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.android.recyclerviewtest.R;
import com.android.recyclerviewtest.adapter.ListGridAdapter;
import com.android.recyclerviewtest.data.DataUtil;
import com.android.recyclerviewtest.draw.CustomItemDecoration;
import com.android.recyclerviewtest.utils.ToastUtil;

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
    private List<String> datas;

    private ListGridAdapter adapter;
    private LinearLayoutManager layoutManager;
    private int lastVisibleItem; // 当前页面最后一个显示的position
    private boolean isLoading = false; // 是否正在加载更多标记

    private static final int MSG_REFRESH = 0x001;
    private static final int MSG_LOADMORE = 0x002;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_REFRESH:
                    ToastUtil.showSingleToast(RefreshActivity.this, "刷新完成");
                    List<String> refreshData = (List<String>) msg.obj;
                    adapter.addDatas(0, refreshData);

                    // 停止刷新动画
                    swipeRefreshLayout.setRefreshing(false);
                    break;
                case MSG_LOADMORE:
                    isLoading = false;
                    ToastUtil.showSingleToast(RefreshActivity.this, "加载更多完成");
                    List<String> loadMoreData = (List<String>) msg.obj;
                    adapter.addDatas(loadMoreData);
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

        datas = DataUtil.getTextData();
        setRecyclerView();
    }

    private void setRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        adapter = new ListGridAdapter(this, datas, LinearLayoutManager.VERTICAL);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        // 增加分割线
        recyclerView.addItemDecoration(new CustomItemDecoration().dividerHeight((int) getResources()
                .getDimension(R.dimen.line_height))
                .dividerColor(getResources().getColor(R.color.line_bg)));

        // 改变颜色，还可设置其他属性
        swipeRefreshLayout.setColorSchemeColors(0x80FF0000,0x8000FF00,0x800000FF);

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
                        List<String> refreshData = DataUtil.refreshData(3);
                        Message msg = Message.obtain();
                        msg.what = MSG_REFRESH;
                        msg.obj = refreshData;
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
                                List<String> loadMoreData = DataUtil.loadMoreData(2);
                                Message msg = Message.obtain();
                                msg.what = MSG_LOADMORE;
                                msg.obj = loadMoreData;
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
