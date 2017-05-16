package com.don.easy2readyoedge.appwebview;

/**
 * Created by don on 10/31/16.
 */

public class AppWebViewPresenter implements AppWebContract.Presenter {
  private final AppWebContract.ViewModel mView;

  AppWebViewPresenter(AppWebContract.ViewModel view){
    this.mView = view;
  }
  @Override
  public void initControl(){ mView.findControl(); }
  @Override
  public void loadWebView(String url){ mView.showWebViewContent(url); }
}
