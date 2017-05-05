package cn.easyar.samples.helloarvideo.utils;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.SaveCallback;
import java.util.Arrays;

/**
 * Designed by guoyx on 5/3/17.
 */

public class BmobBuilder {

    public static void saveVue() {
        final AVObject todoFolder = new AVObject("Region");// 构建对象
        todoFolder.put("type", "sichuan");
        todoFolder.put("count", 2);
        todoFolder.put("title", "川菜");

        final AVObject todo1 = new AVObject("Video");
        todo1.put("name", "辣子鸡丁");
        todo1.put("play", 0);
        todo1.put("cover",ConstantUtil.LAZICHKEN_PIC);
        todo1.put("url",ConstantUtil.LAZICHKEN_VIDEO);
        todo1.put("desc","辣子鸡丁是川菜中比较有代表性的一道佳肴，开胃可口，辣椒的数量可以根据" +
            "大家吃辣的程度放多放少，关键是鸡肉和红辣椒的选材一定要好，做出来的辣子鸡丁才美味");
        todo1.put("material","鸡肉 500g，红干辣椒50g");
        todo1.put("ingredient","油适量, 盐适量，姜适量，葱适量");

        final AVObject todo2 = new AVObject("Video");
        todo2.put("name","水煮牛肉");
        todo2.put("play",0);
        todo2.put("cover",ConstantUtil.NIUROU_PIC);
        todo2.put("url",ConstantUtil.NIUROU_VIDEO);
        todo2.put("desc","绝对是这个季节的宠儿，水煮牛肉，满盆的都是能量啊，火辣辣的一锅，" +
            "一家人围着吃，吃的浑身冒汗。屋里也暖，人也暖，心也暖");
        todo2.put("material","牛里脊500g");
        todo2.put("ingredient","豆芽100g，粉条1把，干辣椒8个，葱一段，姜一段，八角一个");

        AVObject.saveAllInBackground(Arrays.asList(todo1, todo2), new SaveCallback() {
            @Override
            public void done(AVException e) {
                AVRelation<AVObject> relation = todoFolder.getRelation("containedVideo");// 新建一个 AVRelation
                relation.add(todo1);
                relation.add(todo2);
                // 上述 3 行代码表示 relation 关联了 3 个 Todo 对象

                todoFolder.saveInBackground();
            }
        });

    }
}

