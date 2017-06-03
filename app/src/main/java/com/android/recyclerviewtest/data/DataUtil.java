package com.android.recyclerviewtest.data;

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
    public static List<String> getTextData() {
        List<String> result = new ArrayList<>();
        for (int i = 'A'; i <= 'Z'; i++) {
            result.add((char) i + "");
        }
        for (int i = 'a'; i <= 'z'; i++) {
            result.add((char) i + "");
        }
        return result;
    }

    public static List<String> refreshData(int dataCount) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < dataCount; i++) {
            result.add("刷新数据 - " + i);
        }
        return result;
    }

    public static List<String> loadMoreData(int dataCount) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < dataCount; i++) {
            result.add("加载更多数据 - " + i);
        }
        return result;
    }

    public static String[] getImageArray() {
        return ImageUrl.IMAGES;
    }

    public static List<String> getImageList() {
        // 因为需要进行增删操作，所以不能使用 Arrays.asList() 方法将数组转为集合返回

        // return Arrays.asList(ImageUrl.IMAGES);

        List<String> result = new ArrayList<>();
        for (String imageUrl : ImageUrl.IMAGES) {
            result.add(imageUrl);
        }
        return result;
    }
}
