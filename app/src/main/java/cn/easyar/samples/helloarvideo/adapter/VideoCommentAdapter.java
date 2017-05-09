package cn.easyar.samples.helloarvideo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import cn.easyar.samples.helloarvideo.R;
import cn.easyar.samples.helloarvideo.bean.Comments;
import cn.easyar.samples.helloarvideo.widget.CircleImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.util.List;

/**
 * Designed by guoyx on 5/9/17.
 */
public class VideoCommentAdapter extends AbsRecyclerViewAdapter {
    public List<Comments> comments;


    public VideoCommentAdapter(RecyclerView recyclerView, List<Comments> comments) {

        super(recyclerView);
        this.comments = comments;
    }


    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).
            inflate(R.layout.item_video_comment, parent, false));
    }


    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {

        ItemViewHolder mHolder = (ItemViewHolder) holder;
        Comments list = comments.get(position);
        mHolder.mUserName.setText(list.username);
        mHolder.mContent.setText(list.msg);

        Glide.with(getContext())
            .load(list.face)
            .centerCrop()
            .dontAnimate()
            .placeholder(R.mipmap.ico_user_default)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(mHolder.mUserAvatar);

        super.onBindViewHolder(holder, position);
    }


    @Override public int getItemCount() {
        return comments.size();
    }


    public class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder {

        CircleImageView mUserAvatar;

        TextView mUserName;

        ImageView mUserLv;

        ImageView mUserSex;

        TextView mFloor;

        TextView mCommentTime;

        TextView mCommentNum;

        TextView mSpot;

        TextView mContent;


        public ItemViewHolder(View itemView) {

            super(itemView);
            mUserAvatar = $(R.id.item_user_avatar);
            mUserName = $(R.id.item_user_name);
            mUserLv = $(R.id.item_user_lever);
            mUserSex = $(R.id.item_user_sex);
            mFloor = $(R.id.item_comment_floor);
            mCommentTime = $(R.id.item_comment_time);
            mContent = $(R.id.item_comment_content);
        }
    }

}
