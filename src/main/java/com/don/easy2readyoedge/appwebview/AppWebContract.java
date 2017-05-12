package com.don.easy2readyoedge.appwebview;

import com.don.easy2readyoedge.core.mvp.MvpView;

/**
 * Created by DON on 17/02/21.
 */

public interface AppWebContract {
  /**
   * Created by don on 10/31/16.
   */
  interface Presenter{

    void initControl();

    void loadWebView(String url);
  }
  interface ViewModel {
    void findControl();
    void showWebViewContent(String url);
  }
}
