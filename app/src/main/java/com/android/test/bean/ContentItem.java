package com.android.test.bean;


import com.android.test.cell.RecyclerCellType;
import com.renj.recycler.adapter.MultiItemEntity;

public class ContentItem implements MultiItemEntity {

    public final String content;

    public ContentItem(String content) {
        this.content = content;
    }

    @Override
    public int getItemType() {
        return RecyclerCellType.VERTICAL_TEXT_CELL;
    }
}
