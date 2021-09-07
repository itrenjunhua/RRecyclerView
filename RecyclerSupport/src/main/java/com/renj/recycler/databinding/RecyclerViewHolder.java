package com.renj.recycler.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2019-06-05   9:55
 * <p>
 * 描述：{@link RecyclerView.ViewHolder} 封装类，最终转发到 {@link BaseRecyclerCell} 中
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class RecyclerViewHolder<D, C extends BaseRecyclerCell<D>> extends RecyclerView.ViewHolder {
    C mItemCell;
    ViewDataBinding viewDataBinding;
    // 保存当前 item 的所有控件id，减少 findViewById 次数
    private SparseArray<View> itemViews = new SparseArray<>();

    public RecyclerViewHolder(@NonNull ViewGroup parent, C itemCell) {
        this(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), itemCell.mItemLayoutResId, parent, false));
        this.mItemCell = itemCell;
    }

    private RecyclerViewHolder(ViewDataBinding viewDataBinding) {
        super(viewDataBinding.getRoot());
        this.viewDataBinding = viewDataBinding;
        // 增加单击事件
        viewDataBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnItemViewClickListener) mOnItemViewClickListener.onItemViewClick(v);
            }
        });

        // 增加长按事件
        viewDataBinding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null != mOnItemViewLongClickListener)
                    return mOnItemViewLongClickListener.onItemLongViewClick(v);
                return false;
            }
        });
    }

    public ViewDataBinding getViewDataBinding() {
        return viewDataBinding;
    }

    public void onBindViewHolder(int position, D itemData) {
        mItemCell.onBindViewHolder(this, viewDataBinding, position, itemData);
    }

    public void onAttachedToWindow() {
        mItemCell.onAttachedToWindow(this,viewDataBinding);
    }

    public void onDetachedFromWindow() {
        mItemCell.onDetachedFromWindow(this,viewDataBinding);
    }

    public TextView getTextView(@IdRes int vId) {
        return getView(vId);
    }

    @SuppressWarnings("unused")
    public void setText(@IdRes int vId, @NonNull CharSequence content) {
        getTextView(vId).setText(content);
    }

    @SuppressWarnings("unused")
    public void setText(@IdRes int vId, @StringRes int strId) {
        getTextView(vId).setText(strId);
    }

    @SuppressWarnings("unused")
    public void setEnabled(@IdRes int vId, boolean enable) {
        getView(vId).setEnabled(enable);
    }

    public ImageView getImageView(@IdRes int vId) {
        return getView(vId);
    }

    @SuppressWarnings("unused")
    public void setBitmap(@IdRes int vId, @NonNull Bitmap bitmap) {
        getImageView(vId).setImageBitmap(bitmap);
    }

    @SuppressWarnings("unused")
    public void setBitmap(@IdRes int vId, @DrawableRes int resId) {
        getImageView(vId).setImageResource(resId);
    }

    @SuppressWarnings("unchecked")
    public <V extends View> V getView(@IdRes int vId) {
        View view = itemViews.get(vId);
        if (view == null) {
            view = itemView.findViewById(vId);
            itemViews.put(vId, view);
        }
        return (V) view;
    }


    /* ====================== item click listener event ======================= */
    private OnItemViewClickListener mOnItemViewClickListener;
    private OnItemViewLongClickListener mOnItemViewLongClickListener;

    /**
     * 设置单击监听
     *
     * @param onItemViewClickListener {@link OnItemViewClickListener} 对象
     */
    public void setOnItemViewClickListener(OnItemViewClickListener onItemViewClickListener) {
        this.mOnItemViewClickListener = onItemViewClickListener;
    }

    /**
     * 设置长按监听
     *
     * @param onItemViewLongClickListener {@link OnItemViewLongClickListener} 对象
     */
    public void setOnItemViewLongClickListener(OnItemViewLongClickListener onItemViewLongClickListener) {
        this.mOnItemViewLongClickListener = onItemViewLongClickListener;
    }

    /**
     * item 单击监听接口
     */
    public interface OnItemViewClickListener {
        void onItemViewClick(View itemView);
    }

    /**
     * item 长按监听接口
     */
    public interface OnItemViewLongClickListener {
        boolean onItemLongViewClick(View itemView);
    }
}
