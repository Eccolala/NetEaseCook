package cn.easyar.samples.helloarvideo.module.home.video;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import cn.easyar.samples.helloarvideo.R;
import cn.easyar.samples.helloarvideo.adapter.VideoCommentAdapter;
import cn.easyar.samples.helloarvideo.base.RxLazyFragment;
import cn.easyar.samples.helloarvideo.bean.Comments;
import cn.easyar.samples.helloarvideo.utils.ConstantUtil;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import java.util.ArrayList;
import java.util.List;

/**
 * 评论 Fragment
 */
public class VideoCommentFragment extends RxLazyFragment {
    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;

    @BindView(R.id.et_send)
    EditText editArea;

    private ArrayList<Comments> comments = new ArrayList<>();

    private VideoCommentAdapter mRecyclerAdapter;

    //视频参数，相当于 id
    private int param;


    @Override protected void finishTask() {
        mRecyclerAdapter.notifyDataSetChanged();
    }


    @Override protected void initRecyclerView() {
        mRecyclerAdapter = new VideoCommentAdapter(mRecyclerView, comments);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mRecyclerAdapter);

    }


    @Override protected void loadData() {
        final AVQuery<AVObject> query = new AVQuery<>("Video");
        query.whereEqualTo("param", param);
        query.getFirstInBackground(new GetCallback<AVObject>() {
            @Override public void done(AVObject avObject, AVException e) {
                if (e != null) {
                    return;
                }

                AVRelation<AVObject> relation = avObject.getRelation("containedComment");
                AVQuery<AVObject> query = relation.getQuery();
                query.findInBackground(new FindCallback<AVObject>() {
                    @Override public void done(List<AVObject> list, AVException e) {
                        if (e != null) {
                            return;
                        }
                        for (int i = 0; i < list.size(); i++) {
                            AVObject o = list.get(i);
                            String cover = (String) o.get("cover");
                            String userName = (String) o.get("name");
                            String msg = (String) o.get("msg");

                            Comments comment = new Comments(cover, userName, msg);
                            comments.add(comment);
                        }
                        finishTask();

                    }
                });

            }
        });

    }


    @OnClick(R.id.ib_send)
    void sendComment() {
        final String commentTxt = editArea.getText().toString().trim();

        final AVQuery<AVObject> query = new AVQuery<>("Video");
        query.whereEqualTo("param", param);
        query.getFirstInBackground(new GetCallback<AVObject>() {
            @Override public void done(final AVObject avObject, AVException e) {
                if (e != null) {
                    return;
                }
                final AVObject comment = new AVObject("Comment");
                comment.put("cover", "http://ook0mwqpi.bkt.clouddn.com/z.jpg");
                comment.put("name", "Aceler");
                comment.put("msg", commentTxt);

                comment.saveInBackground(new SaveCallback() {
                    @Override public void done(AVException e) {
                        AVRelation<AVObject> relation = avObject.getRelation("containedComment");
                        relation.add(comment);

                        avObject.saveInBackground(new SaveCallback() {
                            @Override public void done(AVException e) {
                                if (e != null) {
                                    return;
                                }
                                Toast.makeText(getActivity(), "评论成功",
                                    Toast.LENGTH_SHORT);


                            }
                        });

                    }
                });

            }
        });
        editArea.setText("");
        editArea.clearFocus();
        InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(editArea.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

    }


    @Override public int getLayoutResId() {
        return R.layout.fragment_video_comment;
    }


    @Override public void finishCreateView(Bundle state) {
        param = getArguments().getInt(ConstantUtil.AID);
        initRecyclerView();
        loadData();
    }


    public static VideoCommentFragment newInstance(int aid) {

        VideoCommentFragment fragment = new VideoCommentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantUtil.AID, aid);
        fragment.setArguments(bundle);
        return fragment;
    }
}
