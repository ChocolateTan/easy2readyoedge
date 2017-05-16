package com.don.easy2readyoedge.home;

import com.don.easy2readyoedge.configs.ApiConfigs;

/**
 * Created by don on 10/22/16.
 */

public class HomeFragmentPresenter implements HomeContract.Presenter {
  private HomeContract.ViewModel mView;

  HomeFragmentPresenter(HomeContract.ViewModel view){
    this.mView = view;
  }

  @Override
  public void initControl(){
    mView.findControl();
    mView.initWebView();
  }
  @Override
  public void showWebView(){
//    webViewLoadUrl("http://tieba.baidu.com/p/4354214551");
    mView.loadWebView(ApiConfigs.API_HOME_URL);
  }
}
