package com.don.easy2readyoedge.home;

import com.don.easy2readyoedge.core.mvp.MvpView;

/**
 * Created by DON on 17/02/21.
 */

public interface HomeContract {
  interface Presenter{

    void initControl();

    void showWebView();
  }

  interface ViewModel {
    void findControl();
    void initWebView();
    void loadWebView(String url);
    void showComicDetail();
  }
}
