package com.renj.recycler.adapter;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2020-12-28   17:52
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class SimpleMultiItemEntity<T> implements MultiItemEntity {

    private final int itemType;
    private T data;

    public SimpleMultiItemEntity(int itemType, T data) {
        this.itemType = itemType;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
