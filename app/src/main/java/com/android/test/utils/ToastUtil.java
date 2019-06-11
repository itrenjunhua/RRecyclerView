package com.android.test.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-04-13   1:44
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ToastUtil {
    private static Toast mToast;

    public static void showSingleToast(Context context, String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        }
        mToast.setText(msg);
        mToast.show();
    }
}
