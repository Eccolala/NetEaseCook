package cn.easyar.samples.helloarvideo.module.home.recommend;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import butterknife.BindView;
import cn.easyar.samples.helloarvideo.R;
import cn.easyar.samples.helloarvideo.adapter.HomeRecommendBannerSection;
import cn.easyar.samples.helloarvideo.adapter.HomeRecommendedSection;
import cn.easyar.samples.helloarvideo.adapter.SectionedRecyclerViewAdapter;
import cn.easyar.samples.helloarvideo.base.RxLazyFragment;
import cn.easyar.samples.helloarvideo.bean.ResultBean;
import cn.easyar.samples.helloarvideo.bean.VideoBean;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.FindCallback;
import java.util.ArrayList;
import java.util.List;

/**
 * TAB 推荐 fragment
 */
public class HomeRecommendedFragment extends RxLazyFragment {

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;

    private boolean mIsRefreshing = false;

    private List<ResultBean> results = new ArrayList<>();
    private List<String> banners = new ArrayList<>();

    private SectionedRecyclerViewAdapter mSectionedAdapter;


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
        initRecyclerView();
        isPrepared = false;
    }

    @Override
    protected void initRecyclerView() {

        mSectionedAdapter = new SectionedRecyclerViewAdapter();
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

            @Override
            public int getSpanSize(int position) {

                switch (mSectionedAdapter.getSectionItemViewType(position)) {
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        return 2;

                    case SectionedRecyclerViewAdapter.VIEW_TYPE_FOOTER:
                        return 2;

                    default:
                        return 1;
                }
            }
        });
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mSectionedAdapter);
        setRecycleNoScroll();
    }


    private void setRecycleNoScroll() {
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
                return mIsRefreshing;
            }
        });
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
        final ArrayList<String> bannerResult = new ArrayList<String>() {};
        final ArrayList<ResultBean> recoList = new ArrayList<>();
        final ArrayList<VideoBean> videoList1 = new ArrayList<>();
        final ArrayList<VideoBean> videoList2 = new ArrayList<>();

        AVQuery query = new AVQuery("Banner");
        query.findInBackground(new FindCallback() {
            @Override public void done(List list, AVException e) {
                if (e != null) {
                    return;
                }
                for (int i= 0; i < list.size(); i++) {
                    AVObject object = (AVObject) list.get(i);
                    bannerResult.add((String)object.get("cover"));
                }


                AVObject folder = AVObject.createWithoutData("Region", "590c821644d904007bd62385");
                AVRelation<AVObject> relation = folder.getRelation("containedVideo");
                AVQuery query = relation.getQuery();
                query.findInBackground(new FindCallback() {
                    @Override public void done(List list, AVException e) {
                        if (e != null) {
                            return;
                        }

                        for (int i = 0; i < list.size(); i++) {
                            AVObject object = (AVObject) list.get(i);
                            videoList1.add(new VideoBean((String) object.get("desc"),
                                (String) object.get("cover"),
                                (String) object.get("name"),
                                (Integer) object.get("param"),
                                (String) object.get("ingredient"),
                                (String) object.get("material"),
                                (String) object.get("url"),
                                (Integer) object.get("play")));
                        }

                        AVObject folder2 = AVObject.createWithoutData("Region",
                            "590c7f492f301e006c0ee5dc");
                        AVRelation<AVObject> relation2 = folder2.getRelation("containedVideo");
                        AVQuery query2 = relation2.getQuery();

                        query2.findInBackground(new FindCallback() {
                            @Override public void done(List list, AVException e) {
                                if (e != null) {
                                    return;
                                }
                                for (int i = 0; i < list.size(); i++) {
                                    AVObject object = (AVObject) list.get(i);
                                    videoList2.add(new VideoBean((String) object.get("desc"),
                                        (String) object.get("cover"),
                                        (String) object.get("name"),
                                        (Integer) object.get("param"),
                                        (String) object.get("ingredient"),
                                        (String) object.get("material"),
                                        (String) object.get("url"),
                                        (Integer) object.get("play")));
                                }

                                recoList.add(new ResultBean("热门推荐", videoList2));
                                recoList.add(new ResultBean("川菜", videoList1));
                                Log.d("TAG", "stop");

                                finishTask(bannerResult, recoList);

                            }
                        });
                    }

                });

            }
        });

    }


    private void finishTask(ArrayList<String> bannerResult, ArrayList<ResultBean> recoList) {
        mSwipeRefreshLayout.setRefreshing(false);
        mIsRefreshing = false;
        mSectionedAdapter.addSection(new HomeRecommendBannerSection(bannerResult));

        int size = recoList.size();

        for (int i = 0; i < size; i++) {
            String type = recoList.get(i).type;
            if (!TextUtils.isEmpty(type)) {
                mSectionedAdapter.addSection(new HomeRecommendedSection(
                    getActivity(),
                    recoList.get(i).type,
                    recoList.get(i).type,
                    2,
                    recoList.get(i).list));
            }
        }
        mSectionedAdapter.notifyDataSetChanged();

    }


    /**
     * 清空所有数据及adpter,还有标志位
     */
    private void clearData() {
        banners.clear();
        results.clear();
        mIsRefreshing = true;
        mSectionedAdapter.removeAllSections();
    }


    public static Fragment newInstance() {
        return new HomeRecommendedFragment();
    }

}
