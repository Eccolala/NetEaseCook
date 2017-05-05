package cn.easyar.samples.helloarvideo.base;

import android.app.Application;
import cn.easyar.samples.helloarvideo.utils.BmobBuilder;
import com.avos.avoscloud.AVOSCloud;
import com.facebook.stetho.Stetho;

/**
 * Designed by guoyx on 5/3/17.
 */

public class CookApplication extends Application {
    public static CookApplication mInstance;


    @Override public void onCreate() {
        super.onCreate();

        mInstance = this;
        getInstance();
        AVOSCloud.initialize(this, "GMynXdsaV090VvlwbhAC476X-gzGzoHsz", "JPOefd9mzSsftVF1b7mHwxRd");

        //集成数据库查看
        Stetho.initializeWithDefaults(this);


    }


    public static CookApplication getInstance(){
        return mInstance;
    }
}
