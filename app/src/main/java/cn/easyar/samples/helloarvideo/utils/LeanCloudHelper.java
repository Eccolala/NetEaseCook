package cn.easyar.samples.helloarvideo.utils;

import android.util.Log;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import java.util.Arrays;
import java.util.List;

/**
 * Designed by guoyx on 5/3/17.
 */

public class LeanCloudHelper {
    public static void saveData() {
        final AVObject object = AVObject.createWithoutData("Video", "590c8216da2f6000534559cc");

        final AVObject comment = new AVObject("Comment");
        comment.put("cover", "http://ook0mwqpi.bkt.clouddn.com/j.jpg");
        comment.put("name", "本蛇蟒蚺");
        comment.put("msg", "手速那里我笑了hhh");

        final AVObject comment1 = new AVObject("Comment");
        comment1.put("cover", "http://ook0mwqpi.bkt.clouddn.com/k.jpg");
        comment1.put("name", "高渐离");
        comment1.put("msg", "修仙人士表示愿意用一枚聚气丹换。");

        final AVObject comment2 = new AVObject("Comment");
        comment2.put("cover",
            "http://ook0mwqpi.bkt.clouddn.com/l.jpg");
        comment2.put("name", "浪树的兔子");
        comment2.put("msg", "哦，对了结尾还吃给我们看哦，可好了呢");

        AVObject.saveAllInBackground(Arrays.asList(comment, comment1, comment2),
            new SaveCallback() {
                @Override public void done(AVException e) {
                    AVRelation<AVObject> relation = object.getRelation("590c8216da2f6000534559cc");
                    relation.add(comment);
                    relation.add(comment1);
                    relation.add(comment2);

                    object.saveInBackground();
                }
            });
    }


    public static void peekData() {
        AVObject object = AVObject.createWithoutData("Video", "590c8216da2f6000534559cb");
        AVRelation<AVObject> relation = object.getRelation("containedComment");
        AVQuery<AVObject> query = relation.getQuery();
        query.findInBackground(new FindCallback<AVObject>() {
            @Override public void done(List<AVObject> list, AVException e) {
                if (e != null) {
                    return;
                }
                for (int i = 0; i < list.size(); i++) {
                    AVObject o = list.get(i);
                    String msg = (String) o.get("msg");
                    Log.d("TAG", msg);
                }
            }
        });
    }
}
