package com.android.test.adapter.cell;

import android.support.annotation.NonNull;

import com.android.test.utils.imageutil.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2019-06-05   20:34
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class CellFactory {
    @NonNull
    public static VerticalTextCell createVerticalTextCell(String text) {
        return new VerticalTextCell(text);
    }

    @NonNull
    public static List<VerticalTextCell> createVerticalTextCell(List<String> texts) {
        List<VerticalTextCell> cells = new ArrayList<>();
        for (String text : texts) {
            cells.add(createVerticalTextCell(text));
        }
        return cells;
    }

    @NonNull
    public static HorizontalTextCell createHorizontalTextCell(String text) {
        return new HorizontalTextCell(text);
    }

    @NonNull
    public static List<HorizontalTextCell> createHorizontalTextCell(List<String> texts) {
        List<HorizontalTextCell> cells = new ArrayList<>();
        for (String text : texts) {
            cells.add(createHorizontalTextCell(text));
        }
        return cells;
    }

    @NonNull
    public static GridTextCell createGridTextCell(String text) {
        return new GridTextCell(text);
    }

    @NonNull
    public static List<GridTextCell> createGridTextCell(List<String> texts) {
        List<GridTextCell> cells = new ArrayList<>();
        for (String text : texts) {
            cells.add(createGridTextCell(text));
        }
        return cells;
    }

    @NonNull
    public static VerticalImageCell createVerticalImageCell(String text, GlideUtils glideUtils) {
        return new VerticalImageCell(text, glideUtils);
    }

    @NonNull
    public static List<VerticalImageCell> createVerticalImageCell(List<String> texts, GlideUtils glideUtils) {
        List<VerticalImageCell> cells = new ArrayList<>();
        for (String text : texts) {
            cells.add(createVerticalImageCell(text, glideUtils));
        }
        return cells;
    }

    @NonNull
    public static StaggeredVerticalImageCell createStaggeredVerticalImageCell(String text, GlideUtils glideUtils) {
        return new StaggeredVerticalImageCell(text, glideUtils);
    }

    @NonNull
    public static List<StaggeredVerticalImageCell> createStaggeredVerticalImageCell(List<String> texts, GlideUtils glideUtils) {
        List<StaggeredVerticalImageCell> cells = new ArrayList<>();
        for (String text : texts) {
            cells.add(createStaggeredVerticalImageCell(text, glideUtils));
        }
        return cells;
    }

    @NonNull
    public static StaggeredHorizontalImageCell createStaggeredHorizontalImageCell(String text, GlideUtils glideUtils) {
        return new StaggeredHorizontalImageCell(text, glideUtils);
    }

    @NonNull
    public static List<StaggeredHorizontalImageCell> createStaggeredHorizontalImageCell(List<String> texts, GlideUtils glideUtils) {
        List<StaggeredHorizontalImageCell> cells = new ArrayList<>();
        for (String text : texts) {
            cells.add(createStaggeredHorizontalImageCell(text, glideUtils));
        }
        return cells;
    }
}
