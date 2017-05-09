package cn.easyar.samples.helloarvideo.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.easyar.samples.helloarvideo.R;
import cn.easyar.samples.helloarvideo.bean.VideoBean;
import cn.easyar.samples.helloarvideo.common.OriginalRankActivity;
import cn.easyar.samples.helloarvideo.module.home.video.VideoDetailsActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Designed by hcc on 16/8/27 11:51
 *
 * 首页推荐界面Section
 */
public class HomeRecommendedSection extends StatelessSection {

    private Context mContext;

    private String title;

    private String type;

    private int liveCount;

    private static final String TYPE_RECOMMENDED = "recommend";

    private static final String TYPE_LIVE = "live";

    private static final String TYPE_BANGUMI = "bangumi_2";

    private static final String GOTO_BANGUMI = "bangumi_list";

    private static final String TYPE_ACTIVITY = "activity";

    private List<VideoBean> datas = new ArrayList<>();

    private final Random mRandom;

    private int[] icons = new int[] {
        R.mipmap.ic_header_hot, R.mipmap.ic_category_t160
    };


    public HomeRecommendedSection(Context context, String title, String type, int liveCount, List<VideoBean> datas) {

        super(R.layout.layout_home_recommend_head, R.layout.layout_home_recommend_foot,
            R.layout.layout_home_recommend_boby);

        this.mContext = context;
        this.title = title;
        this.type = type;
        this.liveCount = liveCount;
        this.datas = datas;

        mRandom = new Random();
    }


    @Override
    public int getContentItemsTotal() {

        return datas.size();
    }


    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {

        return new ItemViewHolder(view);
    }


    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {

        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        final VideoBean bodyBean = datas.get(position);

        Glide.with(mContext)
            .load(Uri.parse(bodyBean.cover))
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate()
            .into(itemViewHolder.mVideoImg);

        itemViewHolder.mVideoTitle.setText(bodyBean.name);
        itemViewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                VideoDetailsActivity.launch((Activity) mContext,
                    bodyBean.url, bodyBean.name,
                    bodyBean.material, bodyBean.ingredient,
                    bodyBean.desc, bodyBean.param, bodyBean.cover);
            }
        });

        itemViewHolder.mLiveLayout.setVisibility(View.GONE);
        itemViewHolder.mBangumiLayout.setVisibility(View.GONE);
        itemViewHolder.mVideoLayout.setVisibility(View.VISIBLE);
        itemViewHolder.mVideoPlayNum.setText(bodyBean.play + "");
    }


    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {

        return new HeaderViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {

        HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
        setTypeIcon(headerViewHolder);
        headerViewHolder.mTypeTv.setText(title);
        headerViewHolder.mTypeRankBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mContext.startActivity(
                    new Intent(mContext, OriginalRankActivity.class));
            }
        });

        switch (type) {
            case TYPE_RECOMMENDED:
                headerViewHolder.mTypeMore.setVisibility(View.GONE);
                headerViewHolder.mTypeRankBtn.setVisibility(View.VISIBLE);
                headerViewHolder.mAllLiveNum.setVisibility(View.GONE);
                break;

        }
    }


    /**
     * 根据title设置typeIcon
     */
    private void setTypeIcon(HeaderViewHolder headerViewHolder) {

        switch (title) {
            case "热门推荐":
                headerViewHolder.mTypeImg.setImageResource(icons[0]);
                break;
            case "川菜":
                headerViewHolder.mTypeImg.setImageResource(icons[1]);
                break;

        }
    }


    @Override
    public RecyclerView.ViewHolder getFooterViewHolder(View view) {

        return new FootViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder) {

        final FootViewHolder footViewHolder = (FootViewHolder) holder;
        footViewHolder.mDynamic.setText(String.valueOf(mRandom.nextInt(200)) + "条新动态,点这里刷新");
        footViewHolder.mRefreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                footViewHolder.mRefreshBtn
                    .animate()
                    .rotation(360)
                    .setInterpolator(new LinearInterpolator())
                    .setDuration(1000).start();
            }
        });

        footViewHolder.mRecommendRefresh.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                footViewHolder.mRecommendRefresh
                    .animate()
                    .rotation(360)
                    .setInterpolator(new LinearInterpolator())
                    .setDuration(1000).start();
            }
        });

        switch (type) {
            case TYPE_RECOMMENDED:
                footViewHolder.mMoreBtn.setVisibility(View.GONE);
                footViewHolder.mRefreshLayout.setVisibility(View.GONE);
                footViewHolder.mRecommendRefreshLayout.setVisibility(View.VISIBLE);
                break;
        }
    }


    static class HeaderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_type_img)
        ImageView mTypeImg;

        @BindView(R.id.item_type_tv)
        TextView mTypeTv;

        @BindView(R.id.item_type_more)
        TextView mTypeMore;

        @BindView(R.id.item_type_rank_btn)
        TextView mTypeRankBtn;

        @BindView(R.id.item_live_all_num)
        TextView mAllLiveNum;


        HeaderViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    static class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_view)
        CardView mCardView;

        @BindView(R.id.video_preview)
        ImageView mVideoImg;

        @BindView(R.id.video_title)
        TextView mVideoTitle;

        @BindView(R.id.video_play_num)
        TextView mVideoPlayNum;

        @BindView(R.id.video_review_count)
        TextView mVideoReviewCount;

        @BindView(R.id.layout_live)
        RelativeLayout mLiveLayout;

        @BindView(R.id.layout_video)
        LinearLayout mVideoLayout;

        @BindView(R.id.item_live_up)
        TextView mLiveUp;

        @BindView(R.id.item_live_online)
        TextView mLiveOnline;

        @BindView(R.id.layout_bangumi)
        RelativeLayout mBangumiLayout;

        @BindView(R.id.item_bangumi_update)
        TextView mBangumiUpdate;


        public ItemViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    static class FootViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_btn_more)
        Button mMoreBtn;

        @BindView(R.id.item_dynamic)
        TextView mDynamic;

        @BindView(R.id.item_btn_refresh)
        ImageView mRefreshBtn;

        @BindView(R.id.item_refresh_layout)
        LinearLayout mRefreshLayout;

        @BindView(R.id.item_recommend_refresh_layout)
        LinearLayout mRecommendRefreshLayout;

        @BindView(R.id.item_recommend_refresh)
        ImageView mRecommendRefresh;


        FootViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
