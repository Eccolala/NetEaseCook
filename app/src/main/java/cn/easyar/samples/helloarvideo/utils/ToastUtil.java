package cn.easyar.samples.helloarvideo.utils;

/**
 * Designed by guoyx on 5/3/17.
 */

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import cn.easyar.samples.helloarvideo.base.CookApplication;

public class ToastUtil {

    public static void showShort(Context context, String text) {

        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }


    public static void showShort(Context context, int resId) {

        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }


    public static void showLong(Context context, String text) {

        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }


    public static void showLong(Context context, int resId) {

        Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
    }


    public static void ShortToast(final String text) {

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override public void run() {
                Toast.
                    makeText(CookApplication.getInstance(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
