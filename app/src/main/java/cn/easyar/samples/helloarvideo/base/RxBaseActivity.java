package cn.easyar.samples.helloarvideo.base;

/**
 * Designed by guoyx on 5/3/17.
 */

import android.os.Bundle;
import android.os.PersistableBundle;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Designed by hcc on 16/8/7 21:18
 *
 * <p/>
 * Activity基类
 */
public abstract class RxBaseActivity extends RxAppCompatActivity {

    private Unbinder bind;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //设置布局内容
        setContentView(getLayoutId());
        //初始化黄油刀控件绑定框架
        bind = ButterKnife.bind(this);
        //初始化控件
        initViews(savedInstanceState);
        //初始化ToolBar
        initToolBar();
    }


    public abstract int getLayoutId();

    public abstract void initViews(Bundle savedInstanceState);

    public abstract void initToolBar();


    public void loadData() {}


    public void showProgressBar() {}


    public void hideProgressBar() {}


    public void initRecyclerView() {}


    public void initRefreshLayout() {}


    public void finishTask() {}





    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {

        super.onPostCreate(savedInstanceState);

    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
        bind.unbind();
    }
}
