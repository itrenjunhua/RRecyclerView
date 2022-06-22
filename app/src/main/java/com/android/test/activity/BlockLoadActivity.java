package com.android.test.activity;

import android.os.Handler;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.test.R;
import com.android.test.cell.RecyclerCellType;
import com.android.test.cell.UserDataCell;
import com.android.test.cell.VerticalImageCell;
import com.android.test.cell.VerticalTextCell;
import com.android.test.data.DataUtil;
import com.android.test.data.UserData;
import com.renj.recycler.adapter.BaseRecyclerCell;
import com.renj.recycler.adapter.RecyclerAdapter;
import com.renj.recycler.adapter.RecyclerBlockData;
import com.renj.recycler.adapter.SimpleMultiItemEntity;
import com.renj.recycler.draw.LinearItemDecoration;

import java.util.ArrayList;
import java.util.List;

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
public class BlockLoadActivity extends BaseActivity {
    private TextView title;
    private RecyclerView recyclerView;
    // 列表数据
    private RecyclerBlockData<SimpleMultiItemEntity> recyclerBlockData;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recycler_view;
    }

    @Override
    protected void initView() {
        title = findViewById(R.id.title);
        recyclerView = findViewById(R.id.recyclerview);

        title.setText("列表数据分块加载");

        setRecyclerView();
    }

    private void setRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerAdapter<SimpleMultiItemEntity> adapter = new RecyclerAdapter<SimpleMultiItemEntity>() {
            @NonNull
            @Override
            protected BaseRecyclerCell getRecyclerCell(int itemTypeValue) {
                if (itemTypeValue == RecyclerCellType.VERTICAL_TEXT_CELL)
                    return new VerticalTextCell();
                else if (itemTypeValue == RecyclerCellType.USER_DATA_CELL)
                    return new UserDataCell();
                else
                    return new VerticalImageCell();
            }
        };

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        // 增加分割线
        recyclerView.addItemDecoration(new LinearItemDecoration(LinearLayoutManager.VERTICAL)
                .dividerHeight((int) getResources().getDimension(R.dimen.line_height))
                .dividerColor(getResources().getColor(R.color.line_bg2)));

        recyclerBlockData = new RecyclerBlockData<>();
        recyclerBlockData.addBlock(1); // UserBean
        recyclerBlockData.addBlock(2); // String
        recyclerBlockData.addBlock(3); // Image
        adapter.setRecyclerBlockData(recyclerBlockData);

        initData();
    }

    private void initData() {
        Handler handler = new Handler();

        List<SimpleMultiItemEntity> stringList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            stringList.add(new SimpleMultiItemEntity<>(RecyclerCellType.VERTICAL_TEXT_CELL, "多种条目——文字类型 " + i));
        }
        recyclerBlockData.setBlockData(2, stringList);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<SimpleMultiItemEntity> userList = new ArrayList<>();
                for (int i = 0; i < 8; i++) {
                    userList.add(new SimpleMultiItemEntity<>(RecyclerCellType.USER_DATA_CELL, new UserData("张三 - " + i, i)));
                }
                recyclerBlockData.setBlockData(1, userList);
            }
        }, 2000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<SimpleMultiItemEntity> imageList = DataUtil.getImageList(RecyclerCellType.VERTICAL_IMAGE_CELL, 1);
                recyclerBlockData.setBlockData(3, imageList);
            }
        }, 3000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<SimpleMultiItemEntity> userList = new ArrayList<>();
                for (int i = 0; i < 2; i++) {
                    userList.add(new SimpleMultiItemEntity<>(RecyclerCellType.USER_DATA_CELL,
                            new UserData("李四 - " + i, i)));
                }
                recyclerBlockData.addBlockData(1, userList);
            }
        }, 5000);

//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                recyclerBlockData.addBlockData(2, new SimpleMultiItemEntity<>(
//                        RecyclerCellType.VERTICAL_TEXT_CELL, "懒加载多种条目——文字类型"));
//            }
//        }, 6000);

//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                recyclerBlockData.setBlockData(2, new SimpleMultiItemEntity<>(
//                        RecyclerCellType.VERTICAL_TEXT_CELL, "多种条目——文字类型新数据"));
//            }
//        }, 8000);

//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                recyclerBlockData.removeBlockData(2, new SimpleMultiItemEntity<>(
//                        RecyclerCellType.VERTICAL_TEXT_CELL, "多种条目——文字类型 1"));
//
//                recyclerBlockData.removeBlockData(2, new SimpleMultiItemEntity<>(
//                        RecyclerCellType.USER_DATA_CELL, new UserData("张三 - 1", 1)));
//            }
//        }, 8000);

//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                recyclerBlockData.removeBlockData(1, 1, 3);
//            }
//        }, 8000);

//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                recyclerBlockData.removeBlock(2);
//            }
//        }, 8000);

//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                recyclerBlockData.clear();
//            }
//        }, 8000);
    }
}
