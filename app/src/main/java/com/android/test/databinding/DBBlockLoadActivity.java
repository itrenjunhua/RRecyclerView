package com.android.test.databinding;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.test.R;
import com.android.test.cell.RecyclerCellType;
import com.android.test.data.ImageUrl;
import com.android.test.data.UserData;
import com.renj.recycler.databinding.BaseRecyclerCell;
import com.renj.recycler.databinding.RecyclerAdapter;
import com.renj.recycler.databinding.RecyclerBlockData;
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
public class DBBlockLoadActivity extends AppCompatActivity {
    private TextView title;
    private RecyclerView recyclerView;
    // 列表数据
    private RecyclerBlockData<SimpleMultiItemEntity> recyclerBlockData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.db_activity_recycler_view);
        initView();
    }


    protected void initView() {
        title = findViewById(R.id.title);
        recyclerView = findViewById(R.id.recyclerview);

        title.setText("列表数据分块加载(DataBinding)");

        setRecyclerView();
    }

    private void setRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerAdapter<SimpleMultiItemEntity> adapter = new RecyclerAdapter<SimpleMultiItemEntity>() {
            @NonNull
            @Override
            protected BaseRecyclerCell getRecyclerCell(int itemTypeValue) {
                if (itemTypeValue == RecyclerCellType.VERTICAL_TEXT_CELL)
                    return new DBVerticalTextCell();
                else if (itemTypeValue == RecyclerCellType.USER_DATA_CELL)
                    return new DBUserDataCell();
                else
                    return new DBVerticalImageCell();
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

        ObservableList<SimpleMultiItemEntity> stringList = new ObservableArrayList<>();
        for (int i = 0; i < 10; i++) {
            stringList.add(new SimpleMultiItemEntity<>(RecyclerCellType.VERTICAL_TEXT_CELL, "多种条目——文字类型 " + i));
        }
        recyclerBlockData.setBlockData(2, stringList);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ObservableList<SimpleMultiItemEntity> userList = new ObservableArrayList<>();
                for (int i = 0; i < 8; i++) {
                    userList.add(new SimpleMultiItemEntity<>(RecyclerCellType.USER_DATA_CELL, new UserData("张三 - " + i, i)));
                }
                recyclerBlockData.setBlockData(1, userList);
            }
        }, 2000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ObservableList<SimpleMultiItemEntity> imageList = getImageList(RecyclerCellType.VERTICAL_IMAGE_CELL, 1);
                recyclerBlockData.setBlockData(3, imageList);
            }
        }, 3000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ObservableList<SimpleMultiItemEntity> userList = new ObservableArrayList<>();
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

    public ObservableList<SimpleMultiItemEntity> getImageList(int itemType, int dataCount) {
        // 因为需要进行增删操作，所以不能使用 Arrays.asList() 方法将数组转为集合返回

        // return Arrays.asList(ImageUrl.IMAGES);

        ObservableList<SimpleMultiItemEntity> result = new ObservableArrayList<>();
        if (dataCount > ImageUrl.IMAGES.length) dataCount = ImageUrl.IMAGES.length;

        for (int i = 0; i < dataCount; i++) {
            result.add(new SimpleMultiItemEntity(itemType, ImageUrl.IMAGES[i]));
        }
        return result;
    }
}
