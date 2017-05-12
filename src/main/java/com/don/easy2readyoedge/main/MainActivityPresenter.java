package com.don.easy2readyoedge.main;

import javax.inject.Inject;

/**
 * Created by don on 10/21/16.
 */

public class MainActivityPresenter implements MainActivityContract.Presenter {
  private MainActivityContract.ViewModel mView;
  @Inject
  MainActivityPresenter(MainActivityContract.ViewModel view){
    this.mView = view;
  }
  @Override
  public void initControl(){
    mView.initControl();
  }
  @Override
  public void showExitAlert(){
    mView.showExitAlert();
  }
  @Override
  public void setToolbar(){mView.setToolbar();}
  @Override
  public void showHome(){
    mView.hideHuiJiComicPage();
    mView.hideMoreComicPage();
    mView.hideBookMarkPage();
    mView.showHomePage();
  }
  @Override
  public void showHuiJiComic(){
    mView.hideHomePage();
    mView.hideMoreComicPage();
    mView.hideBookMarkPage();
    mView.showHuiJiComicPage();
  }
  @Override
  public void showMoreComic(){
    mView.hideHomePage();
    mView.hideHuiJiComicPage();
    mView.hideBookMarkPage();
    mView.showMoreComicPage();
  }
  @Override
  public void showBookMark(){
    mView.hideHomePage();
    mView.hideHuiJiComicPage();
    mView.hideMoreComicPage();
    mView.showBookMarkPage();
  }
//  public void showChangeTypeAlert(){
//    mView.showSettingAlert();
//  }
//  public void changeShowType(Context ctx, String isOn){
//    ACache.get(ctx).put(CacheConfigs.WEB_VIEW_OPEN, isOn);
//  }
//  public boolean getShowType(Context ctx){
//    String isOpenWebView = ACache.get(ctx).getAsString(CacheConfigs.WEB_VIEW_OPEN);
//    if(!TextUtils.isEmpty(isOpenWebView) && isOpenWebView.equals("true")){
//      return true;
//    }else{
//      return false;
//    }
//  }
}
