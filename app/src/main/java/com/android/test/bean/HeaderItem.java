package com.android.test.bean;


import com.android.test.cell.RecyclerCellType;
import com.renj.recycler.adapter.MultiItemEntity;
import com.renj.recycler.stickyheaders.StickyHeaderModel;

public class HeaderItem implements MultiItemEntity, StickyHeaderModel {
    public final String title;
    public final String message;

    public HeaderItem(String title, String message) {
        this.title = title;
        this.message = message;
    }

    @Override
    public int getItemType() {
        return RecyclerCellType.VERTICAL_HEADER;
    }
}
