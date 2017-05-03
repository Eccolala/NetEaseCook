package cn.easyar.samples.helloarvideo.base;

import android.app.Application;

/**
 * Designed by guoyx on 5/3/17.
 */

public class CookApplication extends Application {
    public static CookApplication mInstance;


    @Override public void onCreate() {
        super.onCreate();

        mInstance = this;
        getInstance();
    }


    public static CookApplication getInstance(){
        return mInstance;
    }
}
