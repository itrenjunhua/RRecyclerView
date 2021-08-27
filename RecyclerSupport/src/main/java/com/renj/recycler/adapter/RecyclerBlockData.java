package com.renj.recycler.adapter;

import android.support.annotation.IntRange;

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
 * 描述：列表数据分块加载时的辅助类，通过 {@link RecyclerAdapter#setRecyclerBlockData(RecyclerBlockData)} 方法进行设置
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class RecyclerBlockData<T> {
    private RecyclerAdapter<T> mRecyclerAdapter;
    private LinkedHashMap<Integer, List<T>> mLinkedHashMap = new LinkedHashMap<>();

    public RecyclerBlockData() {
    }

    void setRecyclerAdapter(RecyclerAdapter<T> recyclerAdapter) {
        this.mRecyclerAdapter = recyclerAdapter;

        List<T> dataList = new ArrayList<>();
        for (List<T> value : mLinkedHashMap.values()) {
            dataList.addAll(value);
        }
        mRecyclerAdapter.setData(dataList, false);
    }

    /**
     * 清除所有数据
     */
    public RecyclerBlockData clear() {
        mLinkedHashMap.clear();
        if (mRecyclerAdapter != null)
            mRecyclerAdapter.clear();
        return this;
    }

    /**
     * 增加数据块，先增加的数据块值表示在列表的前面
     *
     * @param blockPosition 指定的数据块值
     */
    public RecyclerBlockData addBlock(int blockPosition) {
        mLinkedHashMap.put(blockPosition, new ArrayList<T>());
        return this;
    }

    /**
     * 移除指定的数据块中的所有数据
     *
     * @param blockPosition 指定的数据块值
     */
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

            if (mRecyclerAdapter != null)
                mRecyclerAdapter.remove(Math.max(start, 0), removeList.size(), false, false);
            mLinkedHashMap.remove(blockPosition);
        }
        return this;
    }

    /**
     * 设置指定的数据块数据，<b>注意：该方法会清除原来数据块中已经存在的值，设置新的值。</b><br/><br/>
     * <b>如果数据块值没有通过 {@link #addBlock(int)} 方法进行增加(或指定的数据块值不存在)，则会以 {@link #setBlockData(int, Object)}、
     * {@link #setBlockData(int, List)}、{@link #addBlockData(int, Object)}、{@link #setBlockData(int, List)}
     * 等类似方法的先后调用顺序为列表数据块展示顺序。</b>
     *
     * @param blockPosition 指定的数据块值
     * @param blockData     需要给数据块设置的数据
     */
    public RecyclerBlockData setBlockData(int blockPosition, T blockData) {
        if (blockData == null) return this;

        List<T> blockDataList = mLinkedHashMap.get(blockPosition);
        if (blockDataList == null) blockDataList = new ArrayList<>();
        else blockDataList.clear();

        blockDataList.add(blockData);
        mLinkedHashMap.put(blockPosition, blockDataList);

        List<T> dataList = new ArrayList<>();
        for (List<T> value : mLinkedHashMap.values()) {
            dataList.addAll(value);
        }
        if (mRecyclerAdapter != null)
            mRecyclerAdapter.setData(dataList, false);
        return this;
    }

    /**
     * 设置指定的数据块数据，<b>注意：该方法会清除原来数据块中已经存在的值，设置新的值。</b><br/><br/>
     * <b>如果数据块值没有通过 {@link #addBlock(int)} 方法进行增加(或指定的数据块值不存在)，则会以 {@link #setBlockData(int, Object)}、
     * {@link #setBlockData(int, List)}、{@link #addBlockData(int, Object)}、{@link #setBlockData(int, List)}
     * 等类似方法的先后调用顺序为列表数据块展示顺序。</b>
     *
     * @param blockPosition 指定的数据块值
     * @param blockDataList 需要给数据块设置的数据
     */
    public RecyclerBlockData setBlockData(int blockPosition, List<T> blockDataList) {
        if (blockDataList == null) blockDataList = new ArrayList<>();

        mLinkedHashMap.put(blockPosition, blockDataList);

        List<T> dataList = new ArrayList<>();
        for (List<T> value : mLinkedHashMap.values()) {
            dataList.addAll(value);
        }

        if (mRecyclerAdapter != null)
            mRecyclerAdapter.setData(dataList, false);
        return this;
    }

    /**
     * 给指定的数据块增加数据<br/><br/>
     * <b>如果数据块值没有通过 {@link #addBlock(int)} 方法进行增加(或指定的数据块值不存在)，则会以 {@link #setBlockData(int, Object)}、
     * {@link #setBlockData(int, List)}、{@link #addBlockData(int, Object)}、{@link #setBlockData(int, List)}
     *
     * @param blockPosition 指定的数据块值
     * @param blockData     需要增加的数据
     */
    public RecyclerBlockData addBlockData(int blockPosition, T blockData) {
        if (blockData == null) return this;

        List<T> blockDataList = mLinkedHashMap.get(blockPosition);
        if (blockDataList == null) blockDataList = new ArrayList<>();

        blockDataList.add(blockData);
        mLinkedHashMap.put(blockPosition, blockDataList);

        List<T> tList;
        int start = 0;
        for (Integer integer : mLinkedHashMap.keySet()) {
            tList = mLinkedHashMap.get(integer);
            start += tList == null ? 0 : tList.size();
            if (blockPosition == integer) break;
        }

        if (mRecyclerAdapter != null)
            mRecyclerAdapter.add(start > 0 ? (start - 1) : 0, blockData, false, false);

        return this;
    }

    /**
     * 给指定的数据块增加数据<br/><br/>
     * <b>如果数据块值没有通过 {@link #addBlock(int)} 方法进行增加(或指定的数据块值不存在)，则会以 {@link #setBlockData(int, Object)}、
     * {@link #setBlockData(int, List)}、{@link #addBlockData(int, Object)}、{@link #setBlockData(int, List)}
     *
     * @param blockPosition 指定的数据块值
     * @param blockDataList 需要增加的数据
     */
    public RecyclerBlockData addBlockData(int blockPosition, List<T> blockDataList) {
        if (isEmpty(blockDataList)) return this;

        List<T> blockData = mLinkedHashMap.get(blockPosition);
        if (blockData == null) {
            mLinkedHashMap.put(blockPosition, blockDataList);
        } else {
            blockData.addAll(blockDataList);
            mLinkedHashMap.put(blockPosition, blockData);
        }

        List<T> tList;
        int start = 0;
        for (Integer integer : mLinkedHashMap.keySet()) {
            tList = mLinkedHashMap.get(integer);
            start += tList == null ? 0 : tList.size();
            if (blockPosition == integer) {
                start -= blockDataList.size();
                break;
            }
        }

        if (mRecyclerAdapter != null)
            mRecyclerAdapter.add(Math.max(start, 0), blockDataList, false, false);
        return this;
    }

    /**
     * 移除指定的数据块某个数据
     *
     * @param blockPosition 指定的数据块值
     * @param blockData     需要移除的数
     */
    public RecyclerBlockData removeBlockData(int blockPosition, T blockData) {
        if (blockData == null) return this;

        List<T> blockDataList = mLinkedHashMap.get(blockPosition);
        if (blockDataList == null) return this;

        blockDataList.remove(blockData);
        mLinkedHashMap.put(blockPosition, blockDataList);

        if (mRecyclerAdapter != null)
            mRecyclerAdapter.remove(blockData, false, false);

        return this;
    }

    /**
     * 移除指定的数据块多个数据
     *
     * @param blockPosition 指定的数据块值
     * @param start         数据块需要移除数据的开始位置
     * @param count         数据块需要移除数据的个数
     */
    public RecyclerBlockData removeBlockData(int blockPosition, @IntRange(from = 0) int start, int count) {
        List<T> blockData = mLinkedHashMap.get(blockPosition);
        if (blockData == null) return this;

        if (count <= 0) return this;
        if (start < 0) start = 0;
        if ((start + count) > blockData.size()) {
            return this;
        }
        blockData.subList(start, start + count).clear();
        mLinkedHashMap.put(blockPosition, blockData);

        List<T> tList;
        int startPreCount = 0;
        for (Integer integer : mLinkedHashMap.keySet()) {
            if (blockPosition == integer) break;
            tList = mLinkedHashMap.get(integer);
            startPreCount += tList == null ? 0 : tList.size();
        }

        if (mRecyclerAdapter != null)
            mRecyclerAdapter.remove(start + (startPreCount > 0 ? (startPreCount - 1) : 0), count, false, false);
        return this;
    }

    private boolean isEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    private boolean notEmpty(List<T> list) {
        return !isEmpty(list);
    }
}
