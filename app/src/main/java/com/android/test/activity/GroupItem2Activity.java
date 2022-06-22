package com.android.test.activity;

import android.graphics.Color;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.test.R;
import com.android.test.cell.VerticalStringCell;
import com.renj.recycler.adapter.BaseRecyclerCell;
import com.renj.recycler.adapter.RecyclerAdapter;
import com.renj.recycler.draw.GroupItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2021-10-08   11:21
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class GroupItem2Activity extends BaseActivity {
    private TextView title;
    private RecyclerView recyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recycler_view;
    }

    @Override
    protected void initView() {
        title = findViewById(R.id.title);
        recyclerView = findViewById(R.id.recyclerview);

        title.setText("列表分组吸顶(非ItemDecoration)");

        setRecyclerView();
    }


    private List<String> initData() {
        List<String> dataList = new ArrayList<>();
        dataList.add("java");
        dataList.add("jdk");
        dataList.add("php");
        dataList.add("c++");
        dataList.add("linux");
        dataList.add("windows");
        dataList.add("macos");
        dataList.add("red hat");
        dataList.add("python");
        dataList.add("jvm");
        dataList.add("wechat");
        dataList.add("cellphone");
        dataList.add("mouse");
        dataList.add("iphone");
        dataList.add("huawei");
        dataList.add("xiaomi");
        dataList.add("meizu");
        dataList.add("vivo");
        dataList.add("oppo");
        dataList.add("mocrosoft");
        dataList.add("google");
        dataList.add("whatsapp");
        dataList.add("iMac");
        dataList.add("c#");
        dataList.add("iOS");
        dataList.add("water");
        dataList.add("xiaohongshu");
        dataList.add("renj");
        dataList.add("zuk");
        dataList.add("kotlin");
        dataList.add("flutter");
        dataList.add("go");

        Collections.sort(dataList);
        return dataList;
    }

    private void setRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        final List<String> dataList = initData();
        RecyclerAdapter<String> adapter = new RecyclerAdapter<String>(dataList) {
            @NonNull
            @Override
            protected BaseRecyclerCell<String> getRecyclerCell(int itemTypeValue) {
                return new VerticalStringCell();
            }
        };

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        // 增加分割线
        recyclerView.addItemDecoration(new GroupItemDecoration(this)
                .setDividerPaddingLeft(12)
                .setDividerPaddingRight(12)
                .setGroupDividerHeight(32)
                .setGroupNameMarginLeft(16)
                .setGroupNameSize(16)
                .setGroupDividerColor(Color.RED)
                .setItemDividerColor(Color.BLUE)
                .setOnGroupListener(new GroupItemDecoration.OnGroupListener() {
                    @Override
                    public boolean isGroupNameShow(int position) {
                        if (position == 0) {
                            return true;
                        } else {
                            String preGroupName = getGroupName(position - 1);
                            String groupName = getGroupName(position);
                            return !TextUtils.equals(preGroupName, groupName);
                        }
                    }

                    @Override
                    public String getGroupName(int position) {
                        return dataList.get(position).substring(0, 1);
                    }
                }));
    }
}
