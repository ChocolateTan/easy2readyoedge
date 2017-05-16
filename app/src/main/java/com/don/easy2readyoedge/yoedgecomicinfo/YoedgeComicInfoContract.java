package com.don.easy2readyoedge.yoedgecomicinfo;

import com.don.easy2readyoedge.core.mvp.MvpView;

import android.view.View;

/**
 * Created by DON on 17/02/21.
 */

public interface YoedgeComicInfoContract {
  /**
   * Created by don on 10/31/16.
   */

  interface Presenter{

    void initControl();

    void loadWebView(String url);
  }
  interface YoedgeComicInfoView {
    void findControl();
    void showWebContent(String url);

    void showSnackbar(View view, String text);
  }
}
