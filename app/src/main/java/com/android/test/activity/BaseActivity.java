package com.android.test.activity;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2017-04-12    16:40
 * <p/>
 * 描述：
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
    }

    @Override
    public void onClick(View v) {
        // 可以先拦截处理公共的点击事件，然后子类重写之后处理自己的点击事件
        handlerClick(v, v.getId());
    }

    /**
     * 子类处理自己的点击事件
     *
     * @param view
     * @param vId
     */
    protected void handlerClick(View view, int vId) {

    }

    /**
     * 获取布局文件id
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化View
     */
    protected abstract void initView();
}
