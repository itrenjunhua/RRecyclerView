package com.android.test.activity;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.test.R;
import com.android.test.bean.ContentItem;
import com.android.test.bean.HeaderItem;
import com.android.test.cell.GroupContentCell;
import com.android.test.cell.GroupHeaderCell;
import com.android.test.cell.RecyclerCellType;
import com.renj.recycler.adapter.BaseRecyclerCell;
import com.renj.recycler.adapter.MultiItemEntity;
import com.renj.recycler.adapter.RecyclerAdapter;
import com.renj.recycler.draw.LinearItemDecoration;
import com.renj.recycler.stickyheaders.AdapterDataProvider;
import com.renj.recycler.stickyheaders.StickyLinearLayoutManager;

import java.util.ArrayList;
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
        return R.layout.activity_recycler_group;
    }

    @Override
    protected void initView() {
        title = findViewById(R.id.title);
        recyclerView = findViewById(R.id.recyclerview);

        title.setText("列表分组吸顶(非ItemDecoration)");

        setRecyclerView();
    }


    private List<MultiItemEntity> initData() {
        List<MultiItemEntity> dataList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            dataList.add(new HeaderItem("分组1", "分组1说明：我是分组1"));
            dataList.add(new ContentItem("java"));
            dataList.add(new ContentItem("jdk"));
            dataList.add(new ContentItem("php"));
            dataList.add(new ContentItem("c++"));
            dataList.add(new ContentItem("linux"));
            dataList.add(new HeaderItem("分组2", "分组2说明：我是分组2"));
            dataList.add(new ContentItem("windows"));
            dataList.add(new ContentItem("macos"));
            dataList.add(new HeaderItem("分组3", "分组3说明：我是分组3"));
            dataList.add(new ContentItem("red hat"));
            dataList.add(new ContentItem("python"));
            dataList.add(new ContentItem("jvm"));
            dataList.add(new ContentItem("wechat"));
            dataList.add(new ContentItem("cellphone"));
            dataList.add(new ContentItem("mouse"));
            dataList.add(new ContentItem("iphone"));
            dataList.add(new ContentItem("huawei"));
            dataList.add(new ContentItem("xiaomi"));
            dataList.add(new ContentItem("meizu"));
            dataList.add(new ContentItem("vivo"));
            dataList.add(new ContentItem("oppo"));
            dataList.add(new ContentItem("mocrosoft"));
            dataList.add(new ContentItem("google"));
            dataList.add(new HeaderItem("分组4", "分组4说明：我是分组4"));
            dataList.add(new ContentItem("whatsapp"));
            dataList.add(new ContentItem("iMac"));
            dataList.add(new ContentItem("c#"));
            dataList.add(new ContentItem("iOS"));
            dataList.add(new ContentItem("water"));
            dataList.add(new HeaderItem("分组5", "分组5说明：我是分组5"));
            dataList.add(new ContentItem("xiaohongshu"));
            dataList.add(new ContentItem("renj"));
            dataList.add(new ContentItem("zuk"));
            dataList.add(new ContentItem("kotlin"));
            dataList.add(new ContentItem("flutter"));
            dataList.add(new ContentItem("go"));
        }
        dataList.add(new ContentItem("我是最后一个数据"));

        return dataList;
    }

    private void setRecyclerView() {
        final List<MultiItemEntity> dataList = initData();
        GroupRecyclerAdapter adapter = new GroupRecyclerAdapter();
        StickyLinearLayoutManager layoutManager = new StickyLinearLayoutManager(this, adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        // 增加分割线
        recyclerView.addItemDecoration(new LinearItemDecoration(LinearLayoutManager.VERTICAL)
                .dividerHeight(2));
        adapter.setData(dataList);
    }

    static class GroupRecyclerAdapter extends RecyclerAdapter<MultiItemEntity> implements AdapterDataProvider {

        @NonNull
        @Override
        protected BaseRecyclerCell<?> getRecyclerCell(int itemTypeValue) {
            if (itemTypeValue == RecyclerCellType.VERTICAL_HEADER) {
                return new GroupHeaderCell();
            } else {
                return new GroupContentCell();
            }
        }

        @Override
        public List<?> getAdapterData() {
            return getDataList();
        }
    }
}
