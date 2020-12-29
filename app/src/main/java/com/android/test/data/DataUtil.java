package com.android.test.data;

import com.renj.recycler.adapter.SimpleMultiItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2017-04-12    17:00
 * <p/>
 * 描述：模拟获取数据类
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */

public class DataUtil {
    public static List<SimpleMultiItemEntity> getTextData(int itemType) {
        List<SimpleMultiItemEntity> result = new ArrayList<>();
        for (int i = 'A'; i <= 'Z'; i++) {
            result.add(new SimpleMultiItemEntity(itemType, ((char) i + "")));
        }
        for (int i = 'a'; i <= 'z'; i++) {
            result.add(new SimpleMultiItemEntity(itemType, ((char) i + "")));
        }
        return result;
    }

    public static List<SimpleMultiItemEntity> refreshData(int itemType, int dataCount) {
        List<SimpleMultiItemEntity> result = new ArrayList<>();
        for (int i = 0; i < dataCount; i++) {
            result.add(new SimpleMultiItemEntity(itemType, "刷新数据 - " + System.currentTimeMillis() + " : " + i));
        }
        return result;
    }

    public static List<SimpleMultiItemEntity> loadMoreData(int itemType, int dataCount) {
        List<SimpleMultiItemEntity> result = new ArrayList<>();
        for (int i = 0; i < dataCount; i++) {
            result.add(new SimpleMultiItemEntity(itemType, "加载更多数据 - " + System.currentTimeMillis() + " : " + i));
        }
        return result;
    }

    public static SimpleMultiItemEntity[] getImageArray(int itemType) {
        SimpleMultiItemEntity[] result = new SimpleMultiItemEntity[ImageUrl.IMAGES.length];
        for (int i = 0; i < ImageUrl.IMAGES.length; i++) {
            result[i] = new SimpleMultiItemEntity(itemType, ImageUrl.IMAGES[i]);
        }
        return result;
    }

    public static List<SimpleMultiItemEntity> getImageList(int itemType) {
        // 因为需要进行增删操作，所以不能使用 Arrays.asList() 方法将数组转为集合返回

        // return Arrays.asList(ImageUrl.IMAGES);

        List<SimpleMultiItemEntity> result = new ArrayList<>();
        for (String imageUrl : ImageUrl.IMAGES) {
            result.add(new SimpleMultiItemEntity(itemType, imageUrl));
        }
        return result;
    }
}
