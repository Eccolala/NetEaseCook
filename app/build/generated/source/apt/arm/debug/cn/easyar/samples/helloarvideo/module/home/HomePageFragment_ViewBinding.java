// Generated code from Butter Knife. Do not modify!
package cn.easyar.samples.helloarvideo.module.home;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import cn.easyar.samples.helloarvideo.R;
import cn.easyar.samples.helloarvideo.widget.CircleImageView;
import com.flyco.tablayout.SlidingTabLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HomePageFragment_ViewBinding implements Unbinder {
  private HomePageFragment target;

  @UiThread
  public HomePageFragment_ViewBinding(HomePageFragment target, View source) {
    this.target = target;

    target.mToolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'mToolbar'", Toolbar.class);
    target.mCircleImageView = Utils.findRequiredViewAsType(source, R.id.toolbar_user_avatar, "field 'mCircleImageView'", CircleImageView.class);
    target.mSlidingTab = Utils.findRequiredViewAsType(source, R.id.sliding_tabs, "field 'mSlidingTab'", SlidingTabLayout.class);
    target.mViewPager = Utils.findRequiredViewAsType(source, R.id.view_pager, "field 'mViewPager'", ViewPager.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    HomePageFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mToolbar = null;
    target.mCircleImageView = null;
    target.mSlidingTab = null;
    target.mViewPager = null;
  }
}
