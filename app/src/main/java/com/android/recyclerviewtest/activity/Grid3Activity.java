package com.android.recyclerviewtest.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.recyclerviewtest.R;
import com.android.recyclerviewtest.adapter.CustomViewHolder;
import com.android.recyclerviewtest.adapter.SingleTypeAdapter;
import com.android.recyclerviewtest.data.DataUtil;
import com.android.recyclerviewtest.utils.ToastUtil;

import java.util.List;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2017-04-13    10:04
 * <p/>
 * 描述：活用Grid布局类型
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */

public class Grid3Activity extends BaseActivity {
    private TextView title;
    private RecyclerView recyclerView;
    private List<String> datas;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recycler_view;
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.title);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        title.setText("活用网络布局类型（View 点击事件）");
        datas = DataUtil.getTextData();
        setRecyclerView();
    }

    private void setRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 6, GridLayoutManager.VERTICAL, false);

        // 设置当前位置占用的列数
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            // 该方法返回 position 位置的 item 占用 GridLayout 的多少列
            @Override
            public int getSpanSize(int position) {
                if (position <= 3) {
                    return 6;
                } else if (position <= 9) {
                    return 3;
                } else if(position <= 15){
                    return 2;
                }else if(position <= 27){
                    return 1;
                }else if(position <= 35){
                    return 3;
                }else if(position <= 39){
                    return 6;
                }else{
                    return 1;
                }
            }
        });

        recyclerView.setLayoutManager(layoutManager);

        // 使用封装后的 Adapter
        recyclerView.setAdapter(new SingleTypeAdapter<String>(this, datas, R.layout.item_grid) {
            @Override
            public void setData(CustomViewHolder holder, final String itemData, int position) {
                // 直接使用 CustomViewHolder 的方法设置文字内容
                holder.setText(R.id.text_grid, itemData);

                // 给 item 中的指定控件添加点击事件
                holder.clickView(R.id.text_grid).setOnClickListener(new CustomViewHolder.OnClickListener() {
                    @Override
                    public void onClick(View view, int vId) {
                        ToastUtil.showSingleToast(Grid3Activity.this,"点击TextView控件 - " + itemData);
                    }
                });
            }
        });
    }
}
