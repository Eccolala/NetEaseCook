package cn.easyar.samples.helloarvideo.module.home.video;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.easyar.samples.helloarvideo.R;
import cn.easyar.samples.helloarvideo.base.RxLazyFragment;
import cn.easyar.samples.helloarvideo.utils.ConstantUtil;

/**
 * View
 */
public class VideoIntroductionFragment extends RxLazyFragment {
    @BindView(R.id.tv_title)
    TextView mTitleText;

    @BindView(R.id.tv_description)
    TextView txt_description;

    @BindView(R.id.tv_material)
    TextView txt_material;

    @BindView(R.id.tv_ingredient)
    TextView txt_ingredient;

    private int param;

    private String name;

    private String material;

    private String desc;

    private String ingredient;


    @Override public int getLayoutResId() {
        return R.layout.fragment_video_introduction;
    }


    @Override public void finishCreateView(Bundle state) {
        param = getArguments().getInt(ConstantUtil.EXTRA_AV);
        name = getArguments().getString(ConstantUtil.EXTRA_NAME);
        material = getArguments().getString(ConstantUtil.EXTRA_MATERIAL);
        desc = getArguments().getString(ConstantUtil.EXTRA_DESC);
        ingredient = getArguments().getString(ConstantUtil.EXTRA_INGREDIENT);

        loadData();
    }


    @Override protected void loadData() {

        finishTask();
    }


    @Override protected void finishTask() {
        //设置视频标题
        mTitleText.setText(name);
        txt_description.setText(desc);
        txt_material.setText(material);
        txt_description.setText(desc);
        txt_ingredient.setText(ingredient);
    }


    public static VideoIntroductionFragment newInstance(int param, String desc, String name
        , String material, String ingredient) {

        VideoIntroductionFragment fragment = new VideoIntroductionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantUtil.EXTRA_AV, param);
        bundle.putString(ConstantUtil.EXTRA_DESC, desc);
        bundle.putString(ConstantUtil.EXTRA_NAME, name);
        bundle.putString(ConstantUtil.EXTRA_MATERIAL, material);
        bundle.putString(ConstantUtil.EXTRA_INGREDIENT, ingredient);
        fragment.setArguments(bundle);
        return fragment;
    }


    @OnClick(R.id.btn_share)
    void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, "来自「逸厨房」的分享:" + desc);
        startActivity(Intent.createChooser(intent, name));
    }

}
