package com.android.test.activity;

import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.test.R;
import com.android.test.cell.RecyclerCellType;
import com.android.test.cell.VerticalTextCell;
import com.android.test.data.DataUtil;
import com.android.test.utils.RLog;
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
            changePosition(srcPosition, targetPosition, adapter.getDataList());
            // 更新Adapter
            adapter.notifyItemMoved(srcPosition, targetPosition);
            return true;
        }

        /**
         * List集合交换两个位置的数据
         */
        private void changePosition(int srcPosition, int targetPosition, List<SimpleMultiItemEntity> dataList) {
            SimpleMultiItemEntity srcData = dataList.get(srcPosition);
            SimpleMultiItemEntity targetData = dataList.get(targetPosition);
            dataList.add(srcPosition, targetData);
            dataList.add(targetPosition + 1, srcData);
            dataList.remove(srcPosition + 1);
            dataList.remove(targetPosition + 1);
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
            adapter.getDataList().remove(adapterPosition);
            // 更新Adapter数据，一定要调用 notifyItemRangeChanged() 方法，否则角标会错乱
            adapter.notifyItemRemoved(adapterPosition);
            adapter.notifyItemRangeChanged(adapterPosition, adapter.getDataList().size() - adapterPosition);

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
        List<SimpleMultiItemEntity> textData = DataUtil.getTextData(RecyclerCellType.VERTICAL_TEXT_CELL);
        adapter = new RecyclerAdapter(textData) {
            @NonNull
            @Override
            protected BaseRecyclerCell getRecyclerCell(int itemTypeValue) {
                return new VerticalTextCell(recyclerView, itemTouchHelper);
            }
        };

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        // 增加分割线
        recyclerView.addItemDecoration(new LinearItemDecoration(LinearLayoutManager.VERTICAL)
                .dividerHeight((int) getResources().getDimension(R.dimen.line_height))
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
