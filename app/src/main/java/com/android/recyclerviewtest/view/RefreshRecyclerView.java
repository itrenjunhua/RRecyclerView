package com.android.recyclerviewtest.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Scroller;
import android.widget.TextView;

import com.android.recyclerviewtest.R;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2017-10-18   15:32
 * <p>
 * 描述：自定义带刷新和加载更多的RecyclerView控件
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class RefreshRecyclerView extends RecyclerView {
    // 下拉刷新
    private final static int DOWN_REFRESH_STATE = 0;
    // 释放刷新
    private final static int RELEASE_REFRESH_STATE = 1;
    // 正在加载
    private final static int REFRESHING_STATE = 2;
    // 不同状态下显示的文字
    private final static String DOWN_REFRESH = "下拉刷新";
    private final static String RELEASE_REFRESH = "释放刷新";
    private final static String REFRESHING = "正在刷新";
    private final static String LOADING = "正在加载数据";
    private final static String NO_MORE = "没有更多";

    // 头的当前状态，默认下拉刷新
    private int mHeaderCurrentState = DOWN_REFRESH_STATE;
    // 是否正在加载更多数据，默认不是
    private boolean isLoading = false;
    // 是否还有更多数据
    private boolean isHasMore = true;
    // 是否能加载更多数据
    private boolean isCanLoadMore = true;
    // 是否能刷新
    private boolean isCanRefresh = true;

    // 头布局相关控件
    private ViewGroup headerView;
    private ImageView arrowView;
    private ProgressBar headerPb;
    private TextView headerMsg;

    // 尾布局相关控件
    private View footerView;
    private ProgressBar footerPb;
    private TextView footerMsg;

    private int headerViewHeight; // 头布局高度
    private int footerViewHeight; // 尾布局高度

    // 定义箭头由下到上和由上到下的动画
    private Animation down2UpAnimtion;
    private Animation up2DownAnimtion;

    // 布局管理器
    private LinearLayoutManager layoutManager;
    // 用于控制滑动动画的对象
    private Scroller scroller;

    // 记录按下的位置和移动的位置的差值
    private int downY, dY;
    // 定义摩擦系数，头布局显示的高度 = 实际移动高度 * 摩擦系数
    private float frictionValue = 0.65f;
    // 头布局显示的最大高度值，默认4倍头布局实际高度
    private int headerViewMaxHeight;
    // 包含头和尾的包裹适配器
    private RefreshWrapAdapter refreshWrapAdapter;

    public RefreshRecyclerView(Context context) {
        this(context, null);
    }

    public RefreshRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        scroller = new Scroller(context);
        initHeaderView(context);
        initFooterView(context);
        initAnimation();
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        layoutManager = (LinearLayoutManager) layout;
    }

    /**
     * 根据滑动状态发生改变显示加载更多的布局
     */
    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        //在静止的状态下   && 必须是最后显示的条目就是RecyclerView的最后一个条目 && 没有在加载更多的数据
        boolean isState = state == RecyclerView.SCROLL_STATE_IDLE;
        //最后一个条目显示的下标
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        boolean isLastVisibleItem = lastVisibleItemPosition == getAdapter().getItemCount() - 2;
        if (isState && isLastVisibleItem && !isLoading && isHasMore && isCanLoadMore) {
            isLoading = true;
            //显示脚
            footerView.setPadding(0, 0, 0, 0);
            //滑动到显示的脚的位置，加1表示移动到加载更多条目的位置
            smoothScrollToPosition(lastVisibleItemPosition + 1);

            //加载数据
            if (mOnLoadMoreListener != null)
                mOnLoadMoreListener.onLoadMore();
        }
    }

    /**
     * 重写分发事件方法<br/>
     * 原因：没有使用onTouchEvent()是因为dispatchTouchEvent()方法回调的频率高一些
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!isCanRefresh)
            return super.dispatchTouchEvent(ev);
        if (mHeaderCurrentState == REFRESHING_STATE)
            return super.dispatchTouchEvent(ev);

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:// 按下
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:// 移动
                int moveY = (int) ev.getY();

                // 条件 RecyclerView的第一个条目的下标是0 && 往下拽的行为
                dY = (int) (moveY * frictionValue - downY);
                if (dY > headerViewMaxHeight)
                    dY = headerViewMaxHeight;
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                int top = -headerViewHeight + dY;
                if (firstVisibleItemPosition == 0 && dY > 0) {
                    // 切换头的状态
                    if (mHeaderCurrentState == DOWN_REFRESH_STATE && top >= 0) {
                        // 由下拉刷新变为释放刷新
                        mHeaderCurrentState = RELEASE_REFRESH_STATE;
                        headerMsg.setText(RELEASE_REFRESH);
                        // 执行动画
                        arrowView.startAnimation(down2UpAnimtion);
                    } else if (mHeaderCurrentState == RELEASE_REFRESH_STATE && top < 0) {
                        mHeaderCurrentState = DOWN_REFRESH_STATE;
                        headerMsg.setText(DOWN_REFRESH);
                        // 执行动画
                        arrowView.startAnimation(up2DownAnimtion);
                    }
                    // 执行头的显示和隐藏操作
                    headerView.setPadding(0, top, 0, 0);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:// 弹起
            case MotionEvent.ACTION_CANCEL:// 事件取消
            case MotionEvent.ACTION_OUTSIDE:// 外部点击
                int mFirstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                if (mFirstVisibleItemPosition == 0 && dY > 0) {
                    if (mHeaderCurrentState == DOWN_REFRESH_STATE) {
                        // 隐藏头
                        // headerView.setPadding(0, -headerViewHeight, 0, 0);
                        startMoveAnimation(-(headerViewHeight - dY), -dY);
                    } else if (mHeaderCurrentState == RELEASE_REFRESH_STATE) {
                        // 把状态切换为正在加载 把头缩回置本身头的高度 隐藏箭头 显示进度条
                        mHeaderCurrentState = REFRESHING_STATE;
                        headerMsg.setText(REFRESHING);
                        //headerView.setPadding(0, 0, 0, 0);
                        startMoveAnimation(-(headerViewHeight - dY), headerViewHeight - dY);
                        // 清除动画后控件才可以隐藏
                        arrowView.clearAnimation();
                        arrowView.setVisibility(View.INVISIBLE);
                        headerPb.setVisibility(View.VISIBLE);
                        // 加载最新的数据
                        if (mOnRefreshListener != null)
                            mOnRefreshListener.onRefresh();
                    }
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        // 包装成RefreshWrapAdapter
        refreshWrapAdapter = new RefreshWrapAdapter(headerView, footerView, adapter);
        super.setAdapter(refreshWrapAdapter);
    }

    /**
     * 初始化尾布局
     */
    private void initFooterView(Context context) {
        footerView = inflate(context, R.layout.recycler_footer, null);
        footerPb = (ProgressBar) footerView.findViewById(R.id.pb_footer);
        footerMsg = (TextView) footerView.findViewById(R.id.tv_footer);

        // 设置宽度填充屏幕，不然的话就只有包裹内容
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        footerView.setLayoutParams(layoutParams);
        //测量
        footerView.measure(0, 0);
        footerViewHeight = footerView.getMeasuredHeight();
        //隐藏
        footerView.setPadding(0, -footerViewHeight, 0, 0);
    }

    /**
     * 初始化头布局
     */
    private void initHeaderView(Context context) {
        headerView = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.recycler_header, null);
        arrowView = (ImageView) headerView.findViewById(R.id.iv_arrow);
        headerPb = (ProgressBar) headerView.findViewById(R.id.pb);
        headerMsg = (TextView) headerView.findViewById(R.id.tv_state);

        // 设置宽度填充屏幕，不然的话就只有包裹内容
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        headerView.setLayoutParams(layoutParams);

        //隐藏进度条
        headerPb.setVisibility(View.INVISIBLE);
        //测量默认头的高度
        headerView.measure(0, 0);
        //获取测量后的高度
        headerViewHeight = headerView.getMeasuredHeight();
        headerViewMaxHeight = headerViewHeight * 4;
        //隐藏头
        headerView.setPadding(0, -headerViewHeight, 0, 0);
    }

    /**
     * 初始化动画
     */
    private void initAnimation() {
        down2UpAnimtion = createDown2UpAnimation();
        up2DownAnimtion = createUp2DownAnimation();
    }

    /**
     * 从下拉刷新切换到释放刷新的动画 (由下到上)
     *
     * @return
     */
    private Animation createDown2UpAnimation() {
        Animation animation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(200);
        animation.setFillAfter(true);
        return animation;
    }

    /**
     * 从释放刷新切换到下拉刷新的动画 (由上到下)
     *
     * @return
     */
    private Animation createUp2DownAnimation() {
        Animation animation = new RotateAnimation(-180, -360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(200);
        animation.setFillAfter(true);
        return animation;
    }

    /**
     * 开始移动动画
     *
     * @param startY 开始位置
     * @param dY     移动值
     */
    private void startMoveAnimation(int startY, int dY) {
        int duration = Math.abs(dY) * 10;
        scroller.startScroll(0, startY, 0, dY, duration > 500 ? 500 : duration);
        postInvalidateOnAnimation();
    }

    /**
     * 维持动画
     */
    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            int currY = scroller.getCurrY();
            headerView.setPadding(0, currY, 0, 0);
            postInvalidateOnAnimation();
        }
    }

    /**
     * 获取当前文职的条目类型
     *
     * @param position 位置
     * @return 条目类型  注意：10068：头布局类型  10010：尾布局类型
     */
    public int getItemViewType(int position) {
        return refreshWrapAdapter.getItemViewType(position);
    }

    /**
     * 设置能不能加载更多
     *
     * @param canRefresh true：能<br/>
     *                   false：不能，不能加载更多就不会再显示加载更多的布局，也不会回调方法了，相当于普通的RecyclerView
     */
    public void setCanRefresh(boolean canRefresh) {
        this.isCanRefresh = canRefresh;
        if (!isCanRefresh) {
            headerView.setPadding(0, -headerViewHeight, 0, 0);
        }
    }

    /**
     * 设置能不能加载更多
     *
     * @param canLoadMore true：能<br/>
     *                    false：不能，不能加载更多就不会再显示加载更多的布局，也不会回调方法了，相当于普通的RecyclerView
     */
    public void setCanLoadMore(boolean canLoadMore) {
        this.isCanLoadMore = canLoadMore;
        if (!isCanLoadMore) {
            footerView.setPadding(0, -footerViewHeight, 0, 0);
        }
    }

    /**
     * 设置是否还有更多数据
     *
     * @param hasMore true：有更多 false：没有更多数据，显示没有更多提示
     */
    public void setHasMore(boolean hasMore) {
        this.isHasMore = hasMore;
        if (hasMore) {
            footerMsg.setText(LOADING);
            footerPb.setVisibility(VISIBLE);
        } else {
            footerMsg.setText(NO_MORE);
            footerPb.setVisibility(GONE);
        }
    }

    /**
     * 刷新完成，恢复默认状态
     */
    public void finishRefreshing() {
        if (refreshWrapAdapter != null)
            refreshWrapAdapter.notifyDataSetChanged();

        //headerView.setPadding(0, -headerViewHeight, 0, 0);
        startMoveAnimation(0, -headerViewHeight);
        mHeaderCurrentState = DOWN_REFRESH_STATE;
        headerMsg.setText(DOWN_REFRESH);
        headerPb.setVisibility(INVISIBLE);
        arrowView.setVisibility(VISIBLE);
    }

    /**
     * 加载更多完成，恢复默认状态
     */
    public void finishLoading() {
        if (refreshWrapAdapter != null)
            refreshWrapAdapter.notifyDataSetChanged();

        isLoading = false;
        footerView.setPadding(0, -footerViewHeight, 0, 0);
        footerMsg.setText(LOADING);
    }

    /************************* 刷新和加载更多监听设置 ***************************/
    /**
     * 刷新新数据接口
     */
    public interface OnRefreshListener {
        void onRefresh();
    }

    /**
     * 加载更多的接口
     */
    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    private OnRefreshListener mOnRefreshListener;
    private OnLoadMoreListener mOnLoadMoreListener;

    /**
     * 设置刷新监听
     *
     * @param mOnRefreshListener
     */
    public void setOnRefreshListener(OnRefreshListener mOnRefreshListener) {
        this.mOnRefreshListener = mOnRefreshListener;
    }

    /**
     * 设置加载更多监听
     *
     * @param mOnLoadMoreListener
     */
    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }
}
