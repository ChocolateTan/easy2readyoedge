package com.don.easy2readyoedge.yoedgecomicinfo;

import android.view.View;
import com.orz.orzframework.mvp.MvpPresenter;
import com.orz.orzframework.mvp.MvpView;

/**
 * Created by DON on 17/02/21.
 */

public interface YoedgeComicInfoContract {
  /**
   * Created by don on 10/31/16.
   */

  interface Presenter extends MvpPresenter{

    void initControl();

    void loadWebView(String url);
  }
  interface ViewModule extends MvpView {
    void findControl();
    void showWebContent(String url);

    void showSnackbar(View view, String text);
  }
}
