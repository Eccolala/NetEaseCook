package cn.easyar.samples.helloarvideo.module.home.video;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import cn.easyar.samples.helloarvideo.R;
import cn.easyar.samples.helloarvideo.base.RxBaseActivity;
import cn.easyar.samples.helloarvideo.bean.VideoBean;
import cn.easyar.samples.helloarvideo.event.AppBarStateChangeEvent;
import cn.easyar.samples.helloarvideo.utils.ConstantUtil;
import cn.easyar.samples.helloarvideo.utils.DisplayUtil;
import cn.easyar.samples.helloarvideo.utils.SystemBarHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.Thing;
import java.util.ArrayList;
import java.util.List;

public class VideoDetailsActivity extends RxBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @BindView(R.id.video_preview)
    ImageView mVideoPreview;

    @BindView(R.id.tab_layout)
    SlidingTabLayout mSlidingTabLayout;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @BindView(R.id.fab)
    FloatingActionButton mFAB;

    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.tv_player)
    TextView mTvPlayer;

    @BindView(R.id.tv_av)
    TextView mAvText;

    private List<String> titles = new ArrayList<>();

    private List<Fragment> fragments = new ArrayList<>();

    private int param;

    private String imgUrl;

    private VideoBean mVideoDetailsInfo;

    private String desc;
    private String material;
    private String ingredient;
    private String url;
    private String videoName;


    @Override public int getLayoutId() {
        return R.layout.activity_video_details;
    }


    @Override public void initViews(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            param = intent.getIntExtra(ConstantUtil.EXTRA_AV, -1);
            imgUrl = intent.getStringExtra(ConstantUtil.EXTRA_IMG_URL);
            desc = intent.getStringExtra(ConstantUtil.EXTRA_DESC);
            material = intent.getStringExtra(ConstantUtil.EXTRA_MATERIAL);
            ingredient = intent.getStringExtra(ConstantUtil.EXTRA_INGREDIENT);
            url = intent.getStringExtra(ConstantUtil.EXTRA_VIDEO_URL);
            videoName = intent.getStringExtra(ConstantUtil.EXTRA_NAME);

        }

        Glide.with(VideoDetailsActivity.this)
            .load(imgUrl)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate()
            .into(mVideoPreview);

        mFAB.setClickable(true);
        mFAB.setBackgroundTintList(
            ColorStateList.valueOf(getResources().getColor(R.color.gray_20)));
        mFAB.setTranslationY(
            -getResources().getDimension(R.dimen.floating_action_button_size_half));
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                VideoPlayerActivity.launch(VideoDetailsActivity.this, String.valueOf(param), url,
                    desc, videoName
                    , material, ingredient);
            }
        });

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                setViewsTranslation(verticalOffset);
            }
        });
        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeEvent() {

            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state, int verticalOffset) {

                if (state == State.EXPANDED) {
                    //展开状态
                    mTvPlayer.setVisibility(View.GONE);
                    mAvText.setVisibility(View.VISIBLE);
                    mToolbar.setContentInsetsRelative(
                        DisplayUtil.dp2px(VideoDetailsActivity.this, 15), 0);
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    mTvPlayer.setVisibility(View.VISIBLE);
                    mAvText.setVisibility(View.GONE);
                    mToolbar.setContentInsetsRelative(
                        DisplayUtil.dp2px(VideoDetailsActivity.this, 150), 0);
                } else {
                    mTvPlayer.setVisibility(View.GONE);
                    mAvText.setVisibility(View.VISIBLE);
                    mToolbar.setContentInsetsRelative(
                        DisplayUtil.dp2px(VideoDetailsActivity.this, 15), 0);
                }
            }
        });

        finishTask();
    }


    @Override public void finishTask() {

        mFAB.setBackgroundTintList(
            ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        mCollapsingToolbarLayout.setTitle("");
        if (TextUtils.isEmpty(imgUrl)) {
            Glide.with(VideoDetailsActivity.this)
                .load(mVideoDetailsInfo)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(mVideoPreview);
        }
        VideoIntroductionFragment mVideoIntroductionFragment
            = VideoIntroductionFragment.newInstance(param,desc,videoName,material,ingredient);
        VideoCommentFragment mVideoCommentFragment = VideoCommentFragment.newInstance(param);

        fragments.add(mVideoIntroductionFragment);
        fragments.add(mVideoCommentFragment);

        // setPagerTitle(String.valueOf(mVideoDetailsInfo.getStat().getReply()));
        setPagerTitle(String.valueOf(1024));
    }


    @Override public void initToolBar() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        //设置还没收缩时状态下字体颜色
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
        //设置收缩后Toolbar上字体的颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);

        //设置StatusBar透明
        SystemBarHelper.immersiveStatusBar(this);
        SystemBarHelper.setHeightAndPadding(this, mToolbar);

        mAvText.setText(videoName);
    }


    private void setPagerTitle(String num) {
        titles.add("简介");
        titles.add("评论" + "(" + num + ")");

        VideoDetailsPagerAdapter mAdapter = new VideoDetailsPagerAdapter(
            getSupportFragmentManager(),
            fragments, titles);

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mSlidingTabLayout.setViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }


            @Override
            public void onPageSelected(int position) {

                measureTabLayoutTextWidth(position);
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    private void measureTabLayoutTextWidth(int position) {
        String title = titles.get(position);
        TextView titleView = mSlidingTabLayout.getTitleView(position);
        TextPaint paint = titleView.getPaint();
        float textWidth = paint.measureText(title);
        mSlidingTabLayout.setIndicatorWidth(textWidth / 3);
    }


    public static void launch(Activity activity, String url, String name, String material, String ingredient, String desc, int param, String imgUrl) {
        Intent intent = new Intent(activity, VideoDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(ConstantUtil.EXTRA_AV, param);
        intent.putExtra(ConstantUtil.EXTRA_VIDEO_URL, url);
        intent.putExtra(ConstantUtil.EXTRA_NAME, name);
        intent.putExtra(ConstantUtil.EXTRA_MATERIAL, material);
        intent.putExtra(ConstantUtil.EXTRA_INGREDIENT, ingredient);
        intent.putExtra(ConstantUtil.EXTRA_DESC, desc);
        intent.putExtra(ConstantUtil.EXTRA_IMG_URL, imgUrl);
        activity.startActivity(intent);
    }


    private void setViewsTranslation(int target) {

        mFAB.setTranslationY(target);
        if (target == 0) {
            showFAB();
        } else if (target < 0) {
            hideFAB();
        }
    }


    private void showFAB() {

        mFAB.animate().scaleX(1f).scaleY(1f)
            .setInterpolator(new OvershootInterpolator())
            .start();

        mFAB.setClickable(true);
    }


    private void hideFAB() {

        mFAB.animate().scaleX(0f).scaleY(0f)
            .setInterpolator(new AccelerateInterpolator())
            .start();

        mFAB.setClickable(false);
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
            .setName("VideoDetails Page") // TODO: Define a title for the content shown.
            // TODO: Make sure this auto-generated URL is correct.
            .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
            .build();
        return new Action.Builder(Action.TYPE_VIEW)
            .setObject(object)
            .setActionStatus(Action.STATUS_TYPE_COMPLETED)
            .build();
    }


    public static class VideoDetailsPagerAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> fragments;

        private List<String> titles;


        VideoDetailsPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {

            super(fm);
            this.fragments = fragments;
            this.titles = titles;
        }


        @Override
        public Fragment getItem(int position) {

            return fragments.get(position);
        }


        @Override
        public int getCount() {

            return fragments.size();
        }


        @Override
        public CharSequence getPageTitle(int position) {

            return titles.get(position);
        }
    }
}
