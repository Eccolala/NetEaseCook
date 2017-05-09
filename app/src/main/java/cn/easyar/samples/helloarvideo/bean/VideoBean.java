package cn.easyar.samples.helloarvideo.bean;

/**
 * Designed by guoyx on 5/7/17.
 */
public class VideoBean {
    public String desc;
    public String cover;
    public String name;
    public int param;
    public String ingredient;
    public String material;
    public String url;
    public int play;


    public VideoBean(String desc, String cover, String name, int param, String ingredient
        , String material, String url, int play) {
        this.desc = desc;
        this.cover = cover;
        this.name = name;
        this.param = param;
        this.ingredient = ingredient;
        this.material = material;
        this.url = url;
        this.play = play;
    }

}
