package cn.easyar.samples.helloarvideo.module.home.weibo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import cn.easyar.samples.helloarvideo.R;
import cn.easyar.samples.helloarvideo.base.RxLazyFragment;
import cn.easyar.samples.helloarvideo.module.home.recommend.HomeRecommendedFragment;

/**
 * 社区 Fragment
 */
public class HomeWeiboFragment extends RxLazyFragment {



    @Override public int getLayoutResId() {
        return R.layout.fragment_home_weibo;
    }


    @Override public void finishCreateView(Bundle state) {

    }


    public static Fragment newIntance() {
        return new HomeRecommendedFragment();
    }
}
