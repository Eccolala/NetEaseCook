package cn.easyar.samples.helloarvideo.module.home.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import cn.easyar.samples.helloarvideo.R;
import cn.easyar.samples.helloarvideo.base.RxLazyFragment;

/**
 * 用户信息 Fragment
 */
public class HomeUserFragment extends RxLazyFragment {



    @Override public int getLayoutResId() {
        return R.layout.fragment_home_user;
    }



    @Override public void finishCreateView(Bundle state) {

    }

    public static Fragment newInstance(){
        return new HomeUserFragment();
    }

}
