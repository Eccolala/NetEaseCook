package cn.easyar.samples.helloarvideo.module.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import butterknife.BindView;
import cn.easyar.samples.helloarvideo.ARPlayer.MainActivity;
import cn.easyar.samples.helloarvideo.R;
import cn.easyar.samples.helloarvideo.adapter.HomePagerAdapter;
import cn.easyar.samples.helloarvideo.base.RxLazyFragment;
import cn.easyar.samples.helloarvideo.common.HomeActivity;
import cn.easyar.samples.helloarvideo.widget.CircleImageView;
import com.flyco.tablayout.SlidingTabLayout;

/**
 * 主页 Fragment
 */
public class HomePageFragment extends RxLazyFragment {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.toolbar_user_avatar)
    CircleImageView mCircleImageView;

    @BindView(R.id.sliding_tabs)
    SlidingTabLayout mSlidingTab;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;


    @Override public int getLayoutResId() {
        return R.layout.fragment_home;
    }


    @Override public void finishCreateView(Bundle state) {
        setHasOptionsMenu(true);
        initToolBar();
        initViewpager();
    }


    private void initViewpager() {
        HomePagerAdapter mHomeAdapter = new HomePagerAdapter(getChildFragmentManager(),
            getApplicationContext());
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(mHomeAdapter);
        mSlidingTab.setViewPager(mViewPager);
        mViewPager.setCurrentItem(0);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.id_action_AR:
                //AR 发现
                startActivity(new Intent(getActivity(), MainActivity.class));
                break;

        }

        return super.onOptionsItemSelected(item);
    }


    private void initToolBar() {
        mToolbar.setTitle("");
        ((HomeActivity) getActivity()).setSupportActionBar(mToolbar);
        mCircleImageView.setImageResource(R.mipmap.ic_hotbitmapgg_avatar);
    }


    public static HomePageFragment newInstance() {
        return new HomePageFragment();
    }
}
