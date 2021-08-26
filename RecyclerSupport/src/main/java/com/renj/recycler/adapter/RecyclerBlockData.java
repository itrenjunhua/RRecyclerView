package com.renj.recycler.adapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2021-08-26   11:43
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class RecyclerBlockData<T> {
    private RecyclerAdapter<T> mRecyclerAdapter;
    private LinkedHashMap<Integer, List<T>> mLinkedHashMap = new LinkedHashMap<>();

    private RecyclerBlockData() {
    }

    void setRecyclerAdapter(RecyclerAdapter<T> recyclerAdapter) {
        this.mRecyclerAdapter = recyclerAdapter;

        List<T> dataList = new ArrayList<>();
        for (List<T> value : mLinkedHashMap.values()) {
            dataList.addAll(value);
        }
        mRecyclerAdapter.setData(dataList, false);
    }

    public static RecyclerBlockData create() {
        return new RecyclerBlockData<>();
    }

    public RecyclerBlockData clear() {
        mLinkedHashMap.clear();
        mRecyclerAdapter.clear();
        return this;
    }

    public RecyclerBlockData addBlock(int blockPosition) {
        mLinkedHashMap.put(blockPosition, new ArrayList<T>());
        return this;
    }

    public RecyclerBlockData removeBlock(int blockPosition) {
        List<T> removeList = mLinkedHashMap.get(blockPosition);
        if (notEmpty(removeList)) {
            List<T> tList;
            int start = 0;
            for (Integer integer : mLinkedHashMap.keySet()) {
                if (blockPosition == integer) break;
                tList = mLinkedHashMap.get(integer);
                start += tList == null ? 0 : tList.size();
            }

            mRecyclerAdapter.remove(start > 0 ? (start - 1) : 0, removeList.size(), false, false);
            mLinkedHashMap.remove(blockPosition);
        }
        return this;
    }

    public RecyclerBlockData setBlockData(int blockPosition, T blockData) {
        if (blockData == null) return this;

        List<T> blockDataList = mLinkedHashMap.get(blockPosition);
        if (blockDataList == null) blockDataList = new ArrayList<>();
        else blockDataList.clear();

        blockDataList.add(blockData);
        mLinkedHashMap.put(blockPosition, blockDataList);
        return this;
    }

    public RecyclerBlockData setBlockData(int blockPosition, List<T> blockDataList) {
        if (blockDataList == null) blockDataList = new ArrayList<>();

        mLinkedHashMap.put(blockPosition, blockDataList);
        return this;
    }

    public RecyclerBlockData addBlockData(int blockPosition, T blockData) {
        if (blockData == null) return this;

        List<T> blockDataList = mLinkedHashMap.get(blockPosition);
        if (blockDataList == null) blockDataList = new ArrayList<>();

        blockDataList.add(blockData);
        mLinkedHashMap.put(blockPosition, blockDataList);
        return this;
    }

    public RecyclerBlockData addBlockData(int blockPosition, List<T> blockDataList) {
        if (isEmpty(blockDataList)) return this;

        List<T> blockData = mLinkedHashMap.get(blockPosition);
        if (blockData == null) {
            mLinkedHashMap.put(blockPosition, blockDataList);
        } else {
            blockData.addAll(blockDataList);
            mLinkedHashMap.put(blockPosition, blockData);
        }
        return this;
    }

    public RecyclerBlockData removeBlockData(int blockPosition, T blockData) {
        if (blockData == null) return this;

        List<T> blockDataList = mLinkedHashMap.get(blockPosition);
        if (blockDataList == null) return this;

        blockDataList.remove(blockData);
        mLinkedHashMap.put(blockPosition, blockDataList);
        return this;
    }

    public RecyclerBlockData removeBlockData(int blockPosition, List<T> blockDataList) {
        if (isEmpty(blockDataList)) return this;

        List<T> blockData = mLinkedHashMap.get(blockPosition);
        if (blockData == null) return this;

        blockData.removeAll(blockDataList);
        mLinkedHashMap.put(blockPosition, blockData);
        return this;
    }

    private boolean isEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    private boolean notEmpty(List<T> list) {
        return !isEmpty(list);
    }
}
