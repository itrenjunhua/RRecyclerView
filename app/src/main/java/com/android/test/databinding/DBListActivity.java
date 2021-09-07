package com.android.test.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.android.test.R;
import com.renj.recycler.databinding.BaseRecyclerCell;
import com.renj.recycler.databinding.RecyclerAdapter;
import com.renj.recycler.databinding.RecyclerUtils;
import com.renj.recycler.databinding.SimpleMultiItemEntity;
import com.renj.recycler.draw.LinearItemDecoration;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2021-08-27   15:31
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class DBListActivity extends AppCompatActivity {
    private TextView title;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.db_activity_recycler_view);
        initView();
    }


    protected void initView() {
        title = findViewById(R.id.title);
        recyclerView = findViewById(R.id.recyclerview);

        title.setText("列表数据(DataBinding)");

        setRecyclerView();
    }

    private void setRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerAdapter<SimpleMultiItemEntity> adapter = new RecyclerAdapter<SimpleMultiItemEntity>() {
            @NonNull
            @Override
            protected BaseRecyclerCell getRecyclerCell(int itemTypeValue) {
                return new DBVerticalTextCell();
            }
        };

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        // 增加分割线
        recyclerView.addItemDecoration(new LinearItemDecoration(LinearLayoutManager.VERTICAL)
                .dividerHeight((int) getResources().getDimension(R.dimen.line_height))
                .dividerColor(getResources().getColor(R.color.line_bg2)));

        initData(adapter);

    }

    private void initData(final RecyclerAdapter<SimpleMultiItemEntity> adapter) {
        Handler handler = new Handler();
        ObservableList<SimpleMultiItemEntity> textData = getTextData(0);
        // adapter.setData(textData);

        adapter.getDataList().addAll(textData);
        adapter.getDataList().addOnListChangedCallback(RecyclerUtils.getListChangedCallback(adapter));

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // adapter.add(loadMoreData(0, 10), false);
                adapter.getDataList().addAll(loadMoreData(0, 10));
            }
        }, 2000);
    }

    private ObservableList<SimpleMultiItemEntity> getTextData(int itemType) {
        ObservableList<SimpleMultiItemEntity> result = new ObservableArrayList<>();
        for (int i = 'A'; i <= 'Z'; i++) {
            result.add(new SimpleMultiItemEntity(itemType, ((char) i + "")));
        }
        return result;
    }

    private ObservableList<SimpleMultiItemEntity> loadMoreData(int itemType, int dataCount) {
        ObservableList<SimpleMultiItemEntity> result = new ObservableArrayList<>();
        for (int i = 0; i < dataCount; i++) {
            result.add(new SimpleMultiItemEntity(itemType, "加载更多数据 - " + System.currentTimeMillis() + " : " + i));
        }
        return result;
    }
}
