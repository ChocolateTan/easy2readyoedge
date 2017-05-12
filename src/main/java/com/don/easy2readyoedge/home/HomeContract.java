package com.don.easy2readyoedge.home;

import com.orz.orzframework.mvp.MvpPresenter;
import com.orz.orzframework.mvp.MvpView;

/**
 * Created by DON on 17/02/21.
 */

public interface HomeContract {

  interface Presenter extends MvpPresenter {

    void initControl();

    void showWebView();
  }

  interface ViewModel extends MvpView {

    void findControl();

    void initWebView();

    void loadWebView(String url);

    void showComicDetail();
  }
}
