package cn.easyar.samples.helloarvideo.common;

import android.os.Bundle;
import android.view.KeyEvent;
import cn.easyar.samples.helloarvideo.R;
import cn.easyar.samples.helloarvideo.base.RxBaseActivity;
import cn.easyar.samples.helloarvideo.module.home.HomePageFragment;
import cn.easyar.samples.helloarvideo.utils.ToastUtil;

/**
 * 首屏 Activity
 */

public class HomeActivity extends RxBaseActivity {
    private long exitTime;

    private HomePageFragment mHomePageFragment;


    @Override public int getLayoutId() {
        return R.layout.activity_home;
    }


    @Override public void initViews(Bundle savedInstanceState) {
        //初始化Fragment
        initFragments();
        showHomeFragment();
    }


    private void showHomeFragment() {
        // 添加显示第一个fragment
        getSupportFragmentManager()
            .beginTransaction()
            .add(R.id.container, mHomePageFragment)
            .show(mHomePageFragment).commit();
    }


    /**
     * 监听back键处理DrawerLayout和SearchView
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            exitApp();
        }

        return true;
    }


    /**
     * 退出 App
     */
    /**
     * 双击退出App
     */
    private void exitApp() {

        if (System.currentTimeMillis() - exitTime > 2000) {
            ToastUtil.showShort(HomeActivity.this,"再按一次退出");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }


    private void initFragments() {
        mHomePageFragment = HomePageFragment.newInstance();
    }


    @Override public void initToolBar() {

    }
}
