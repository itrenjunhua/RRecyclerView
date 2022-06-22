package com.renj.recycler.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2021-09-28   9:23
 * <p>
 * 描述：用于分组吸顶效果的 ItemDecoration
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class GroupItemDecoration extends RecyclerView.ItemDecoration {

    private final DisplayMetrics displayMetrics;

    private int groupDividerHeight;  // 分组分割线高度
    private int groupDividerColor;   // 分组分割线颜色
    private int groupNameMarginLeft; // 分组文字左边距

    private int itemDividerHeight;   // 分组内item分割线高度
    private int itemDividerColor;    // 分组内item分割线颜色

    private int dividerPaddingLeft;  // 分割线左间距
    private int dividerPaddingRight; // 分割线右间距

    private Paint dividerPaint; // 绘制分割线画笔
    private Paint textPaint; // 绘制文字画笔

    public GroupItemDecoration(Context context) {
        this.displayMetrics = context.getResources().getDisplayMetrics();
        this.groupDividerHeight = dp2Px(24);  // 默认分组高度
        this.groupNameMarginLeft = dp2Px(12); // 默认分组文字左边距
        this.itemDividerHeight = dp2Px(1);    // 默认普通分割线高度
        initPaint();
    }

    private void initPaint() {
        dividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dividerPaint.setColor(Color.GRAY);
        dividerPaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(sp2Px(14)); // 默认文字大小
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);
        // 获取组名
        String groupName = getGroupName(position);
        if (groupName == null) {
            return;
        }
        if (position == 0 || isGroupNameShow(position)) {
            outRect.top = groupDividerHeight;
        } else {
            outRect.top = itemDividerHeight;
        }
    }


    @Override
    public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(canvas, parent, state);
        // getChildCount() 获取的是当前屏幕可见 item 数量，而不是 RecyclerView 所有的 item 数量
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            // 获取当前itemView在adapter中的索引
            int childAdapterPosition = parent.getChildAdapterPosition(childView);
            /*
             * 由于分割线是绘制在每一个 itemView 的顶部，所以分割线矩形 rect.bottom = itemView.top,
             * rect.top = itemView.top - groupDividerHeight
             */
            int bottom = childView.getTop();
            int left = parent.getPaddingLeft();
            int right = parent.getPaddingRight();
            String groupName = getGroupName(childAdapterPosition);
            if (isGroupNameShow(childAdapterPosition) && !TextUtils.isEmpty(groupName)) {
                // 是分组第一个，则绘制分组分割线
                int top = bottom - groupDividerHeight;
                // 绘制分组分割线矩形
                dividerPaint.setColor(groupDividerColor);
                canvas.drawRect(left, top, childView.getWidth() - right, bottom, dividerPaint);
                // 绘制分组分割线中的文字
                float baseLine = (top + bottom) / 2f - (textPaint.descent() + textPaint.ascent()) / 2f;
                assert groupName != null;
                canvas.drawText(groupName, left + groupNameMarginLeft, baseLine, textPaint);
            } else {
                // 不是分组中第一个，则绘制常规分割线
                int top = bottom - itemDividerHeight;
                dividerPaint.setColor(itemDividerColor);
                canvas.drawRect(left + dividerPaddingLeft, top, childView.getWidth() - right - dividerPaddingRight, bottom, dividerPaint);
            }
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(canvas, parent, state);
        View firstVisibleView = parent.getChildAt(0);
        int firstVisiblePosition = parent.getChildAdapterPosition(firstVisibleView);
        String groupName = getGroupName(firstVisiblePosition);
        if (TextUtils.isEmpty(groupName)) return;

        int left = parent.getPaddingLeft();
        int right = firstVisibleView.getWidth() - parent.getPaddingRight();
        // 第一个itemView(firstVisibleView) 的 bottom 值小于分割线高度，分割线随着 recyclerview 滚动，
        // 分割线top固定不变，bottom=firstVisibleView.bottom
        if (firstVisibleView.getBottom() <= groupDividerHeight && isGroupNameShow(firstVisiblePosition + 1)) {
            dividerPaint.setColor(groupDividerColor);
            canvas.drawRect(left, 0, right, firstVisibleView.getBottom(), dividerPaint);

            assert groupName != null;
            float baseLine = firstVisibleView.getBottom() / 2f - (textPaint.descent() + textPaint.ascent()) / 2f;
            canvas.drawText(groupName, left + groupNameMarginLeft, baseLine, textPaint);
        } else {
            dividerPaint.setColor(groupDividerColor);
            canvas.drawRect(left, 0, right, groupDividerHeight, dividerPaint);

            assert groupName != null;
            float baseLine = groupDividerHeight / 2f - (textPaint.descent() + textPaint.ascent()) / 2f;
            canvas.drawText(groupName, left + groupNameMarginLeft, baseLine, textPaint);
        }
    }

    // ======================= 设置各个属性 ======================= //

    /**
     * 设置分组高度
     *
     * @param groupDividerHeight 分组高度 dp
     */
    public GroupItemDecoration setGroupDividerHeight(float groupDividerHeight) {
        this.groupDividerHeight = dp2Px(groupDividerHeight);
        return this;
    }

    /**
     * 设置分组背景颜色
     *
     * @param groupDividerColor 分组背景颜色
     */
    public GroupItemDecoration setGroupDividerColor(@ColorInt int groupDividerColor) {
        this.groupDividerColor = groupDividerColor;
        return this;
    }

    /**
     * 设置分组文字左边距
     *
     * @param groupNameMarginLeft 分组文字左边距 dp
     */
    public GroupItemDecoration setGroupNameMarginLeft(int groupNameMarginLeft) {
        this.groupNameMarginLeft = dp2Px(groupNameMarginLeft);
        return this;
    }

    /**
     * 设置分组文字大小
     *
     * @param groupNameSize 分组文字大小 sp
     */
    public GroupItemDecoration setGroupNameSize(int groupNameSize) {
        this.textPaint.setTextSize(sp2Px(groupNameSize));
        return this;
    }

    /**
     * 设置分组文字颜色
     *
     * @param groupNameColor 分组文字颜色
     */
    public GroupItemDecoration setGroupNameColor(@ColorInt int groupNameColor) {
        this.textPaint.setColor(groupNameColor);
        return this;
    }

    /**
     * 设置普通分割线高度
     *
     * @param itemDividerHeight 普通分割线高度 dp
     */
    public GroupItemDecoration setItemDividerHeight(int itemDividerHeight) {
        this.itemDividerHeight = dp2Px(itemDividerHeight);
        return this;
    }

    /**
     * 设置普通分割线颜色
     *
     * @param itemDividerColor 普通分割线颜色
     */
    public GroupItemDecoration setItemDividerColor(@ColorInt int itemDividerColor) {
        this.itemDividerColor = itemDividerColor;
        return this;
    }

    /**
     * 设置普通分割线左边距
     *
     * @param dividerPaddingLeft 普通分割线左边距 dp
     */
    public GroupItemDecoration setDividerPaddingLeft(int dividerPaddingLeft) {
        this.dividerPaddingLeft = dp2Px(dividerPaddingLeft);
        return this;
    }

    /**
     * 设置普通分割线右边距
     *
     * @param dividerPaddingRight 普通分割线右边距 dp
     */
    public GroupItemDecoration setDividerPaddingRight(int dividerPaddingRight) {
        this.dividerPaddingRight = dp2Px(dividerPaddingRight);
        return this;
    }

    // ================ 监听，处理是否需要显示组和组名 ================ //

    private OnGroupListener onGroupListener;

    public GroupItemDecoration setOnGroupListener(OnGroupListener onGroupListener) {
        this.onGroupListener = onGroupListener;
        return this;
    }

    public interface OnGroupListener {

        boolean isGroupNameShow(int position);

        String getGroupName(int position);
    }

    /**
     * 返回指定位置的组名
     *
     * @param position 位置
     * @return 组名
     */
    private String getGroupName(int position) {
        if (onGroupListener != null) {
            return onGroupListener.getGroupName(position);
        }
        return null;
    }


    /**
     * 组名是否需要显示
     *
     * @param position 位置
     * @return true：需要显示
     */
    private boolean isGroupNameShow(int position) {
        if (onGroupListener != null) {
            return onGroupListener.isGroupNameShow(position);
        }
        return false;
    }

    // =========================== 单位转换 =========================== //

    private int dp2Px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, displayMetrics);
    }

    private int sp2Px(float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, displayMetrics);
    }
}
