// Generated code from Butter Knife. Do not modify!
package cn.easyar.samples.helloarvideo.module.home.recommend;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import cn.easyar.samples.helloarvideo.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HomeRecommendedFragment_ViewBinding implements Unbinder {
  private HomeRecommendedFragment target;

  @UiThread
  public HomeRecommendedFragment_ViewBinding(HomeRecommendedFragment target, View source) {
    this.target = target;

    target.mSwipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.swipe_refresh_layout, "field 'mSwipeRefreshLayout'", SwipeRefreshLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    HomeRecommendedFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mSwipeRefreshLayout = null;
  }
}
