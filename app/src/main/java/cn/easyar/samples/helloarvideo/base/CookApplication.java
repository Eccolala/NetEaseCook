package cn.easyar.samples.helloarvideo.base;

import android.app.Application;
import com.avos.avoscloud.AVOSCloud;

/**
 * Designed by guoyx on 5/3/17.
 */

public class CookApplication extends Application {
    public static CookApplication mInstance;


    @Override public void onCreate() {
        super.onCreate();

        mInstance = this;
        getInstance();

        startLeanClound();
    }

    private void startLeanClound() {
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this, "bQ8tfxRU7XEEhIaBj95m9eM3-gzGzoHsz", "KFJDcLMAwqpfuXbuB5CrIl2K");
    }

    public static CookApplication getInstance(){
        return mInstance;
    }
}
