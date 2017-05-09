package cn.easyar.samples.helloarvideo.bean;

import java.util.ArrayList;

/**
 * Designed by guoyx on 5/7/17.
 */
public class ResultBean {
    // Region 类型
    public String type;
    public ArrayList<VideoBean> list;

    public ResultBean(String type,ArrayList<VideoBean> list){
        this.type = type;
        this.list = list;
    }

}
