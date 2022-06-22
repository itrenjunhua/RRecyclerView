package com.renj.recycler.stickyheaders;

import java.util.List;

/**
 * RecyclerView的自定义Adapter需要实现该接口，返回当前列表的所有数据
 */
public interface AdapterDataProvider {

    List<?> getAdapterData();
}
