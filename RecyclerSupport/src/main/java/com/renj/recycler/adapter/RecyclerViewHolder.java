package com.renj.recycler.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-06-05   9:55
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    // 保存当前 item 的所有控件id，减少 findViewById 次数
    private SparseArray<View> itemViews = new SparseArray<>();

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        // 增加单击事件
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnItemViewClickListener) mOnItemViewClickListener.onItemViewClick(v);
            }
        });

        // 增加长按事件
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null != mOnItemViewLongClickListener)
                    return mOnItemViewLongClickListener.onItemLongViewClick(v);
                return false;
            }
        });
    }

    public RecyclerViewHolder(@NonNull Context context, @NonNull ViewGroup parent, @LayoutRes int layoutId) {
        this(LayoutInflater.from(context).inflate(layoutId, parent, false));
    }

    public View getItemView() {
        return itemView;
    }

    public TextView getTextView(@IdRes int vId) {
        return getView(vId);
    }

    public void setText(@IdRes int vId, @NonNull CharSequence content) {
        getTextView(vId).setText(content);
    }

    public void setText(@IdRes int vId, @StringRes int strId) {
        getTextView(vId).setText(strId);
    }

    public Button getButton(@IdRes int vId) {
        return getView(vId);
    }

    public void setEnabled(@IdRes int vId, boolean enable) {
        getButton(vId).setEnabled(enable);
    }

    public ImageView getImageView(@IdRes int vId) {
        return getView(vId);
    }

    public void setBitmap(@IdRes int vId, @NonNull Bitmap bitmap) {
        getImageView(vId).setImageBitmap(bitmap);
    }

    public void setBitmap(@IdRes int vId, @DrawableRes int resId) {
        getImageView(vId).setImageResource(resId);

    }

    public  <T extends View> T getView(@IdRes int vId) {
        View view = itemViews.get(vId);
        if (view == null) {
            view = getItemView().findViewById(vId);
            itemViews.put(vId, view);
        }
        return (T) view;
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
