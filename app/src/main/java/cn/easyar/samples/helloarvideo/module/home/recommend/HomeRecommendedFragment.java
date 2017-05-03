package cn.easyar.samples.helloarvideo.module.home.recommend;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import butterknife.BindView;
import cn.easyar.samples.helloarvideo.R;
import cn.easyar.samples.helloarvideo.base.RxLazyFragment;

/**
 * TAB 推荐 fragment
 */
public class HomeRecommendedFragment extends RxLazyFragment {

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private boolean mIsRefreshing = false;


    @Override public int getLayoutResId() {
        return R.layout.fragment_home_recommended;
    }


    @Override public void finishCreateView(Bundle state) {
        isPrepared = true;
        lazyLoad();
    }


    @Override
    protected void lazyLoad() {

        if (!isPrepared || !isVisible) {
            return;
        }

        initRefreshLayout();

        isPrepared = false;
    }


    @Override
    protected void initRefreshLayout() {

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                mIsRefreshing = true;
                loadData();
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                clearData();
                loadData();
            }
        });

    }


    /**
     * 加载数据
     */
    @Override
    protected void loadData() {

    }


    /**
     * 清空所有数据及adpter,还有标志位
     */
    private void clearData() {}

    public static Fragment newInstance() {
        return new HomeRecommendedFragment();
    }
}
