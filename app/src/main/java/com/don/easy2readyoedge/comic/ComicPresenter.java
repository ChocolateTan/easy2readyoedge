package com.don.easy2readyoedge.comic;

/**
 * Created by don on 10/25/16.
 */

public class ComicPresenter implements ComicContract.Presenter {
  private final ComicContract.ViewModel mView;

  ComicPresenter(ComicContract.ViewModel view){
    this.mView = view;
  }
  public void initControl(){mView.initControl();}
  public void loadComicHtml(){}
}
