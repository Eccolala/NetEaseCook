package cn.easyar.samples.helloarvideo.module.home.video;

import android.os.Bundle;
import cn.easyar.samples.helloarvideo.R;
import cn.easyar.samples.helloarvideo.base.RxLazyFragment;
import cn.easyar.samples.helloarvideo.utils.ConstantUtil;

/**
 * 评论 Fragment
 */
public class VideoCommentFragment extends RxLazyFragment {


    @Override protected void finishTask() {

    }


    @Override public int getLayoutResId() {
        return R.layout.fragment_video_comment;
    }


    @Override public void finishCreateView(Bundle state) {

    }


    public static VideoCommentFragment newInstance(int aid) {

        VideoCommentFragment fragment = new VideoCommentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantUtil.AID, aid);
        fragment.setArguments(bundle);
        return fragment;
    }
}
