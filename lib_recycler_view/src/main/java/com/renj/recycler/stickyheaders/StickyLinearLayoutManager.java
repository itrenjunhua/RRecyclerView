package com.renj.recycler.stickyheaders;

import android.content.Context;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.renj.recycler.stickyheaders.handler.StickyHeaderHandler;
import com.renj.recycler.stickyheaders.handler.ViewHolderFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 使用该 {@link StickyLinearLayoutManager} 管理器作为 {@link RecyclerView} 的布局管理器<br/>
 * <b>特别注意：使用该方式实现分组吸顶时，{@link RecyclerView} 需要一个非 {@link android.widget.LinearLayout} 的父布局，
 * 建议使用 {@link android.widget.RelativeLayout} 或者 {@link android.widget.FrameLayout}</b>
 */
public class StickyLinearLayoutManager extends LinearLayoutManager {

    private AdapterDataProvider mHeaderProvider;
    private StickyHeaderHandler mHeaderHandler;

    private List<Integer> mHeaderPositions = new ArrayList<>();

    private ViewHolderFactory viewHolderFactory;

    private int headerElevation = StickyHeaderHandler.NO_ELEVATION;

    @Nullable
    private StickyHeaderListener mHeaderListener;

    public StickyLinearLayoutManager(Context context, AdapterDataProvider headerProvider) {
        this(context, VERTICAL, false, headerProvider);
    }

    public StickyLinearLayoutManager(Context context, int orientation, boolean reverseLayout, AdapterDataProvider headerProvider) {
        super(context, orientation, reverseLayout);

        this.mHeaderProvider = headerProvider;
    }

    /**
     * 设置吸顶状态监听
     *
     * @param listener {@link StickyHeaderListener}
     */
    public void setStickyHeaderListener(@Nullable StickyHeaderListener listener) {
        this.mHeaderListener = listener;
        if (mHeaderHandler != null) {
            mHeaderHandler.setListener(listener);
        }
    }

    /**
     * 吸顶之后是否需要增加影响
     *
     * @param elevateHeaders true：增加影响，默认大小 5，不需要增加阴影
     * @see #elevateHeaders(int)
     */
    public void elevateHeaders(boolean elevateHeaders) {
        elevateHeaders(elevateHeaders ? StickyHeaderHandler.DEFAULT_ELEVATION : StickyHeaderHandler.NO_ELEVATION);
    }

    /**
     * 设置吸顶之后是增加影响大小，大于0，如果小于0表示没有
     *
     * @param dpElevation 阴影大小
     * @see #elevateHeaders(boolean)
     */
    public void elevateHeaders(int dpElevation) {
        this.headerElevation = dpElevation > 0 ? dpElevation : StickyHeaderHandler.NO_ELEVATION;
        if (mHeaderHandler != null) {
            mHeaderHandler.setElevateHeaders(headerElevation);
        }
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        cacheHeaderPositions();
        if (mHeaderHandler != null) {
            resetHeaderHandler();
        }
    }

    @Override
    public void scrollToPosition(int position) {
        super.scrollToPositionWithOffset(position, 0);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int scroll = super.scrollVerticallyBy(dy, recycler, state);
        if (Math.abs(scroll) > 0) {
            if (mHeaderHandler != null) {
                mHeaderHandler.updateHeaderState(findFirstVisibleItemPosition(), getVisibleHeaders(), viewHolderFactory, findFirstCompletelyVisibleItemPosition() == 0);
            }
        }
        return scroll;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int scroll = super.scrollHorizontallyBy(dx, recycler, state);
        if (Math.abs(scroll) > 0) {
            if (mHeaderHandler != null) {
                mHeaderHandler.updateHeaderState(findFirstVisibleItemPosition(), getVisibleHeaders(), viewHolderFactory, findFirstCompletelyVisibleItemPosition() == 0);
            }
        }
        return scroll;
    }

    @Override
    public void removeAndRecycleAllViews(RecyclerView.Recycler recycler) {
        super.removeAndRecycleAllViews(recycler);
        if (mHeaderHandler != null) {
            mHeaderHandler.clearHeader();
        }
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        viewHolderFactory = new ViewHolderFactory(view);
        mHeaderHandler = new StickyHeaderHandler(view);
        mHeaderHandler.setElevateHeaders(headerElevation);
        mHeaderHandler.setListener(mHeaderListener);
        if (mHeaderPositions.size() > 0) {
            mHeaderHandler.setHeaderPositions(mHeaderPositions);
            resetHeaderHandler();
        }
        super.onAttachedToWindow(view);
    }

    @Override
    public void onDetachedFromWindow(RecyclerView view, RecyclerView.Recycler recycler) {
        if (mHeaderHandler != null) {
            mHeaderHandler.clearVisibilityObserver();
        }
        super.onDetachedFromWindow(view, recycler);
    }

    private void resetHeaderHandler() {
        mHeaderHandler.reset(getOrientation());
        mHeaderHandler.updateHeaderState(findFirstVisibleItemPosition(), getVisibleHeaders(), viewHolderFactory, findFirstCompletelyVisibleItemPosition() == 0);
    }

    private Map<Integer, View> getVisibleHeaders() {
        Map<Integer, View> visibleHeaders = new LinkedHashMap<>();

        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            int dataPosition = getPosition(view);
            if (mHeaderPositions.contains(dataPosition)) {
                visibleHeaders.put(dataPosition, view);
            }
        }
        return visibleHeaders;
    }

    private void cacheHeaderPositions() {
        mHeaderPositions.clear();
        List<?> adapterData = mHeaderProvider.getAdapterData();
        if (adapterData == null) {
            if (mHeaderHandler != null) {
                mHeaderHandler.setHeaderPositions(mHeaderPositions);
            }
            return;
        }

        for (int i = 0; i < adapterData.size(); i++) {
            if (adapterData.get(i) instanceof StickyHeaderModel) {
                mHeaderPositions.add(i);
            }
        }
        if (mHeaderHandler != null) {
            mHeaderHandler.setHeaderPositions(mHeaderPositions);
        }
    }

    /**
     * 吸顶状态监听
     */
    public interface StickyHeaderListener {

        /**
         * 吸顶布局开始吸顶
         *
         * @param headerView      吸顶控件
         * @param adapterPosition 吸顶控件在适配器中的位置
         */
        void headerAttached(View headerView, int adapterPosition);

        /**
         * 吸顶布局脱离吸顶
         *
         * @param headerView      吸顶控件
         * @param adapterPosition 吸顶控件在适配器中的位置
         */
        void headerDetached(View headerView, int adapterPosition);
    }
}
