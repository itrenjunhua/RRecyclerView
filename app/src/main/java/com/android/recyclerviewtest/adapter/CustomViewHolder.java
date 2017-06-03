package com.android.recyclerviewtest.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2017-04-06    11:14
 * <p/>
 * 描述： 继承至 RecyclerView.ViewHolder 的自定义 ViewHolder
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private OnItemViewClickListener mOnItemViewClickListener;
    private OnItemLongViewClickListener mOnItemLongViewClickListener;
    private OnClickListener mOnClickListener;

    public CustomViewHolder(View itemView) {
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
                if (null != mOnItemLongViewClickListener)
                    return mOnItemLongViewClickListener.onItemLongViewClick(v);
                return false;
            }
        });
    }

    /**
     * 增加一个需要设置单击监听的控件，与 {@link #setOnClickListener(OnClickListener onClickListener)}  方法连用
     *
     * @param vId 控件id
     * @param <E>
     * @return
     */
    public <E extends CustomViewHolder> E clickView(int vId) {
        getView(vId).setOnClickListener(this);
        return (E) this;
    }

    /**
     * 增加一个需要设置单击监听的控件，与 {@link #setOnClickListener(OnClickListener onClickListener)}  方法连用
     *
     * @param view View 对象
     * @param <E>
     * @return
     */
    public <E extends CustomViewHolder> E clickView(View view) {
        view.setOnClickListener(this);
        return (E) this;
    }

    /**
     * 增加多个需要设置单击监听的控件，与 {@link #setOnClickListener(OnClickListener onClickListener)}  方法连用
     *
     * @param vIds 可变参数，控件id
     * @param <E>
     * @return
     */
    public <E extends CustomViewHolder> E clickView(int... vIds) {
        for (int vId : vIds) {
            getView(vId).setOnClickListener(this);
        }
        return (E) this;
    }

    /**
     * 增加多个需要设置单击监听的控件，与 {@link #setOnClickListener(OnClickListener onClickListener)}  方法连用
     *
     * @param views 可变参数，View 对象
     * @param <E>
     * @return
     */
    public <E extends CustomViewHolder> E clickView(View... views) {
        for (View view : views) {
            view.setOnClickListener(this);
        }
        return (E) this;
    }

    /**
     * 设置单击监听
     *
     * @param onItemViewClickListener {@link CustomViewHolder.OnItemViewClickListener} 对象
     */
    public void setOnItemViewClickListener(OnItemViewClickListener onItemViewClickListener) {
        this.mOnItemViewClickListener = onItemViewClickListener;
    }

    /**
     * 设置长按监听
     *
     * @param onItemLongViewClickListener {@link CustomViewHolder.OnItemLongViewClickListener} 对象
     */
    public void setOnItemLongViewClickListener(OnItemLongViewClickListener onItemLongViewClickListener) {
        this.mOnItemLongViewClickListener = onItemLongViewClickListener;
    }

    /**
     * 设置 item 中 View 的单击监听
     *
     * @param onClickListener {@link CustomViewHolder.OnClickListener} 对象
     */
    public void setOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    /**
     * 根据控件 id 设置文字，当 id 不存在或者 id 表示的控件不是 TextView及其子类 时不会设置文字内容
     *
     * @param vId  view id
     * @param text 内容
     */
    public void setText(int vId, String text) {
        if (vId > 0) {
            View viewById = getView(vId);
            if (viewById instanceof TextView) {
                ((TextView) viewById).setText(text);
            }
        }
    }

    /**
     * 根据控件 id 设置图片，当 id 不存在或者 id 表示的控件不是 ImageView及其子类 时不会设置图片
     *
     * @param vId    view id
     * @param bitmap Bitmap对象
     */
    public void setBitmap(int vId, Bitmap bitmap) {
        if (vId > 0) {
            View viewById = getView(vId);
            if (viewById instanceof ImageView) {
                ((ImageView) viewById).setImageBitmap(bitmap);
            }
        }
    }

    /**
     * 根据控件 id 设置图片资源，当 id 不存在或者 id 表示的控件不是 ImageView及其子类 时不会设置图片资源
     *
     * @param vId   view id
     * @param resId 资源ID
     */
    public void setBitmap(int vId, int resId) {
        if (vId > 0) {
            View viewById = getView(vId);
            if (viewById instanceof ImageView) {
                ((ImageView) viewById).setImageResource(resId);
            }
        }
    }

    /**
     * 根据 id 返回控件，不需要强制转换
     *
     * @param vId 控件 id
     * @param <E> E extends View
     * @return
     */
    public <E extends View> E getView(int vId) {
        return (E) itemView.findViewById(vId);
    }

    @Override
    public void onClick(View v) {
        if (this.mOnClickListener != null) {
            int vId = v.getId();
            this.mOnClickListener.onClick(v, vId);
        }
    }

    /**
     * item 中控件点击事件回调接口
     */
    public interface OnClickListener {
        /**
         * item 中控件点击事件回调
         *
         * @param view 点击的View对象
         * @param vId  点击的View对象ID
         */
        void onClick(View view, int vId);
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
    public interface OnItemLongViewClickListener {
        boolean onItemLongViewClick(View itemView);
    }
}
