package com.uwork.libutil;

import android.content.Context;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by K.W. on 17/3/10.
 */

public class ToastUtils {

    private static Toast toast;

    public static void show(Context context, String str) {
        if (toast == null) {
            toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        }
//        toast.setGravity(Gravity.TOP, 0, height / 3);
        toast.setText(str);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void show(Context context, @StringRes int stringId) {
        show(context, context.getResources().getString(stringId));
    }
}
