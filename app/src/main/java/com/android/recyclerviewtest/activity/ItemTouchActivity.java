package com.android.recyclerviewtest.activity;

import android.graphics.Canvas;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.recyclerviewtest.R;
import com.android.recyclerviewtest.adapter.RecyclerAdapter;
import com.android.recyclerviewtest.adapter.cell.VerticalTextCell;
import com.android.recyclerviewtest.data.DataUtil;
import com.android.recyclerviewtest.draw.CustomItemDecoration;
import com.android.recyclerviewtest.utils.RLog;

import java.util.ArrayList;
import java.util.List;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2017-04-13    15:59
 * <p/>
 * 描述：item 拖拽和侧滑删除功能，使用 ItemTouchHelper 类重写相关方法实现<br/><br/>
 * <b>记住需要调用</b> <code>itemTouchHelper.attachToRecyclerView(recyclerView);</code> <b>方法将
 * ItemTouchHelper 和RecyclerView 绑定到一起</b>
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */

public class ItemTouchActivity extends BaseActivity {
    private TextView title;
    private RecyclerView recyclerView;
    private List<VerticalTextCell> cells;

    private RecyclerAdapter adapter;
    private LinearLayoutManager layoutManager;
    private int screenWidth;

    // 使用 ItemTouchHelper 类实现 RecyclerView 的拖拽和侧滑删除功能
    private ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
        /**
         * 用于设置拖拽和滑动的方向
         * @param recyclerView
         * @param viewHolder
         * @return
         */
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags = 0, swipeFlags = 0;
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof StaggeredGridLayoutManager || layoutManager instanceof GridLayoutManager) {
                // 网格式布局有4个方向拖拽，但是不能测试
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            } else if (layoutManager instanceof LinearLayoutManager) {
                // 线性式布局有2个方向
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN; // 设置拖拽方向为 上下

                swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END; // 设置侧滑方向 左右
            }
            // 调用 makeMovementFlags() 方法 或者 makeFlag() 方法使值生效
            return makeMovementFlags(dragFlags, swipeFlags);// swipeFlags 为0的话item不能滑动
        }

        /**
         * 长按 item 拖拽时会回调这个方法
         * @param recyclerView
         * @param viewHolder
         * @param target
         * @return
         */
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            // 获取拖拽位置
            int srcPosition = viewHolder.getAdapterPosition();
            int targetPosition = target.getAdapterPosition();
            // 交换两个位置的数据
            changePosition(srcPosition, targetPosition, cells);
            // 更新Adapter
            adapter.notifyItemMoved(srcPosition, targetPosition);
            return true;
        }

        /**
         * List集合交换两个位置的数据
         */
        private void changePosition(int srcPosition, int targetPosition, List<VerticalTextCell> cells) {
            VerticalTextCell srcData = cells.get(srcPosition);
            VerticalTextCell targetData = cells.get(targetPosition);
            cells.add(srcPosition, targetData);
            cells.add(targetPosition + 1, srcData);
            cells.remove(srcPosition + 1);
            cells.remove(targetPosition + 1);
        }

        /**
         * 处理滑动删除
         * @param viewHolder
         * @param direction
         */
        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            // 获取位置
            int adapterPosition = viewHolder.getAdapterPosition();
            // 移除数据
            cells.remove(adapterPosition);
            // 更新Adapter数据，一定要调用 notifyItemRangeChanged() 方法，否则角标会错乱
            adapter.notifyItemRemoved(adapterPosition);
            adapter.notifyItemRangeChanged(adapterPosition, cells.size() - adapterPosition);

            RLog.i("删除 item .......");
        }

        /**
         * 开始拖拽时回调
         * @param viewHolder
         * @param actionState
         */
        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            super.onSelectedChanged(viewHolder, actionState);
            // 判断状态
            if (ItemTouchHelper.ACTION_STATE_DRAG == actionState || ItemTouchHelper.ACTION_STATE_SWIPE == actionState) {
                viewHolder.itemView.setBackgroundColor(getResources().getColor(R.color.start_drag_color));
                RLog.i("开始拖拽或者开始侧滑.......");
            }
        }

        /**
         * 拖拽完成时回调，可以恢复颜色
         * @param recyclerView
         * @param viewHolder
         */
        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            viewHolder.itemView.setBackgroundColor(getResources().getColor(R.color.item_bg));
            RLog.i("拖拽完成.......");
        }

        /**
         * 拖拽视图位置发生变化时回调，动态改变颜色
         * @param c
         * @param recyclerView
         * @param viewHolder
         * @param dX
         * @param dY
         * @param actionState
         * @param isCurrentlyActive
         */
        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            // 改变颜色(这里只有侧滑时改变颜色)
            viewHolder.itemView.setAlpha(1 - (Math.abs(dX) / screenWidth));
            RLog.i("位置发生变化.......");
        }

        @Override
        public boolean isLongPressDragEnabled() {
            // 返回true则为所有item都设置可以拖拽
            return true;
        }
    });


    @Override
    protected int getLayoutId() {
        return R.layout.activity_recycler_view;
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.title);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        title.setText("item 长按拖拽和侧滑删除");

        screenWidth();
        setRecyclerView();
    }

    private void setRecyclerView() {
        // 将 RecyclerView 和 ItemTouchHelper 绑定到一起
        itemTouchHelper.attachToRecyclerView(recyclerView);

        layoutManager = new LinearLayoutManager(this);
        List<String> textData = DataUtil.getTextData();
        cells = new ArrayList<>();
        for (String data : textData) {
            VerticalTextCell verticalTextCell = new VerticalTextCell(data);
            cells.add(verticalTextCell);
        }

        adapter = new RecyclerAdapter(cells);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        // 增加分割线
        recyclerView.addItemDecoration(new CustomItemDecoration().dividerHeight((int) getResources()
                .getDimension(R.dimen.line_height))
                .dividerColor(getResources().getColor(R.color.line_bg)));

    }

    /**
     * 获取屏幕宽度
     */
    private void screenWidth() {
        WindowManager windowManager = getWindowManager();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
    }
}
