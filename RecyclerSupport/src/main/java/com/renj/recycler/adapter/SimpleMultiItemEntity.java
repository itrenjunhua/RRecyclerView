package com.renj.recycler.adapter;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2020-12-28   17:52
 * <p>
 * 描述：多条目类型数据简单封装
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class SimpleMultiItemEntity<T> implements MultiItemEntity {
    // 当前数据的条目类型
    private final int itemType;
    private T data;

    /**
     * 创建多种条目类型数据
     *
     * @param itemType 当前数据的条目类型值，用于区分列表中的不同数据
     * @param data     数据
     */
    public SimpleMultiItemEntity(int itemType, T data) {
        this.itemType = itemType;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    /**
     * {@inheritDoc}
     *
     * @implSpec 子类实现，返回当前数据类型的条目类型值
     */
    @Override
    public int getItemType() {
        return itemType;
    }
}
