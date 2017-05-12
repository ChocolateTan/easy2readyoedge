package com.don.easy2readyoedge.appwebview;

import com.orz.orzframework.mvp.MvpPresenter;
import com.orz.orzframework.mvp.MvpView;

/**
 * Created by DON on 17/02/21.
 */

public interface AppWebContract {
  
  interface ViewModel extends MvpView {

    void findControl();

    void showWebViewContent(String url);
  }

  interface Presenter extends MvpPresenter {

    void initControl();

    void loadWebView(String url);
  }

}
