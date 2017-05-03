package cn.easyar.samples.helloarvideo.module.home;

import android.os.Bundle;
import cn.easyar.samples.helloarvideo.R;
import cn.easyar.samples.helloarvideo.base.RxLazyFragment;

/**
 * 主页 Fragment
 */
public class HomePageFragment extends RxLazyFragment {

    public HomePageFragment() {
        // Required empty public constructor
    }


    @Override public int getLayoutResId() {
        return R.layout.fragment_home;
    }


    @Override public void finishCreateView(Bundle state) {

    }


    public static HomePageFragment newInstance() {
        return new HomePageFragment();
    }
}
