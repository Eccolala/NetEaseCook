package cn.easyar.samples.helloarvideo.module.home.video;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import cn.easyar.samples.helloarvideo.R;
import cn.easyar.samples.helloarvideo.base.RxBaseActivity;
import cn.easyar.samples.helloarvideo.media.MediaController;
import cn.easyar.samples.helloarvideo.media.VideoPlayerView;
import cn.easyar.samples.helloarvideo.media.callback.VideoBackListener;
import cn.easyar.samples.helloarvideo.utils.ConstantUtil;
import tv.danmaku.ijk.media.player.IMediaPlayer;

public class VideoPlayerActivity extends RxBaseActivity
    implements VideoBackListener {

    @BindView(R.id.playerView)
    VideoPlayerView mPlayerView;

    @BindView(R.id.buffering_indicator)
    View mBufferingIndicator;

    @BindView(R.id.video_start)
    RelativeLayout mVideoPrepareLayout;

    @BindView(R.id.video_start_info)
    TextView mPrepareText;

    //视频地址
    private String url;

    //菜谱名
    private String title;

    private int LastPosition = 0;

    private String startText = "初始化播放器...";


    @Override public int getLayoutId() {
        return R.layout.activity_video_player;
    }


    @Override public void initViews(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            url = intent.getStringExtra(ConstantUtil.EXTRA_VIDEO_URL);
            title = intent.getStringExtra(ConstantUtil.EXTRA_NAME);
        }

        initMediaPlayer();

    }


    @Override public void initToolBar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setBackgroundDrawable(null);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }


    /**
     * 初始化加载动画
     */
    private void initAnimation() {

        mVideoPrepareLayout.setVisibility(View.VISIBLE);
        startText = startText + "【完成】\n解析视频地址...【完成】\n全舰弹幕填装...";
        mPrepareText.setText(startText);
    }


    @SuppressLint("UseSparseArrays")
    private void initMediaPlayer() {
        //配置播放器
        MediaController mMediaController = new MediaController(this);
        mMediaController.setTitle(title);
        mPlayerView.setMediaController(mMediaController);
        mPlayerView.setMediaBufferingIndicator(mBufferingIndicator);
        mPlayerView.requestFocus();
        mPlayerView.setOnInfoListener(onInfoListener);
        mPlayerView.setOnSeekCompleteListener(onSeekCompleteListener);
        mPlayerView.setOnCompletionListener(onCompletionListener);
        mPlayerView.setOnControllerEventsListener(onControllerEventsListener);

        loadData();

    }


    /**
     * 控制条控制状态事件回调
     */
    private VideoPlayerView.OnControllerEventsListener onControllerEventsListener
        = new VideoPlayerView.OnControllerEventsListener() {

        @Override
        public void onVideoPause() {

        }


        @Override
        public void OnVideoResume() {

        }
    };


    @Override public void loadData() {
        mPlayerView.setVideoURI(Uri.parse(url));
        mPlayerView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override public void onPrepared(IMediaPlayer iMediaPlayer) {
                startText = startText + "【完成】\n视频缓冲中...";
                mPrepareText.setText(startText);
                mVideoPrepareLayout.setVisibility(View.GONE);
            }
        });
    }


    /**
     * 视频播放完成事件回调
     */
    private IMediaPlayer.OnCompletionListener onCompletionListener
        = new IMediaPlayer.OnCompletionListener() {

        @Override public void onCompletion(IMediaPlayer iMediaPlayer) {
            mPlayerView.pause();
        }
    };


    @Override
    protected void onResume() {

        super.onResume();
        if (mPlayerView != null && !mPlayerView.isPlaying()) {
            mPlayerView.seekTo(LastPosition);
        }
    }


    @Override
    protected void onPause() {

        super.onPause();
        if (mPlayerView != null) {
            LastPosition = mPlayerView.getCurrentPosition();
            mPlayerView.pause();
        }

    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
        if (mPlayerView != null && mPlayerView.isDrawingCacheEnabled()) {
            mPlayerView.destroyDrawingCache();
        }
    }


    /**
     * 视频缓冲事件回调
     */
    private IMediaPlayer.OnInfoListener onInfoListener = new IMediaPlayer.OnInfoListener() {

        @Override
        public boolean onInfo(IMediaPlayer mp, int what, int extra) {

            if (what == IMediaPlayer.MEDIA_INFO_BUFFERING_START) {
                if (mBufferingIndicator != null) {
                    mBufferingIndicator.setVisibility(View.VISIBLE);
                }
            } else if (what == IMediaPlayer.MEDIA_INFO_BUFFERING_END) {
                if (mBufferingIndicator != null) {
                    mBufferingIndicator.setVisibility(View.GONE);
                }
            }
            return true;
        }
    };

    /**
     * 视频跳转事件回调
     */
    private IMediaPlayer.OnSeekCompleteListener onSeekCompleteListener
        = new IMediaPlayer.OnSeekCompleteListener() {

        @Override
        public void onSeekComplete(IMediaPlayer mp) {

        }
    };


    public static void launch(Activity activity, String cid, String url, String desc, String name
        , String material, String ingredient) {

        Intent mIntent = new Intent(activity, VideoPlayerActivity.class);
        mIntent.putExtra(ConstantUtil.EXTRA_CID, cid);
        mIntent.putExtra(ConstantUtil.EXTRA_VIDEO_URL, url);
        mIntent.putExtra(ConstantUtil.EXTRA_DESC, desc);
        mIntent.putExtra(ConstantUtil.EXTRA_NAME, name);
        mIntent.putExtra(ConstantUtil.EXTRA_MATERIAL, material);
        mIntent.putExtra(ConstantUtil.EXTRA_INGREDIENT, ingredient);
        activity.startActivity(mIntent);
    }


    @Override public void back() {

    }
}
