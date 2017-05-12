package com.don.easy2readyoedge.comic;

import com.don.easy2readyoedge.core.mvp.MvpView;

/**
 * Created by DON on 17/02/21.
 */

public interface ComicContract {

  interface Presenter{
    void initControl();
    void loadComicHtml();
  }

  interface ViewModel {
    void initControl();
    void setComicTitle(String text);
    void setComicCover(String url);
    void addComicToList();
    void showProgressDialog(String text);
    void hideProgressDialog();
  }
}
