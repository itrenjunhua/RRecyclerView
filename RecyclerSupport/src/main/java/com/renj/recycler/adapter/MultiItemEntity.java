package com.renj.recycler.adapter;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2020-12-28   17:23
 * <p>
 * 描述：使用多种条目类型时数据需实现该类，或者直接使用 {@link SimpleMultiItemEntity}
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public interface MultiItemEntity {
    int getItemType();
}
