package com.don.easy2readyoedge.comic;

import com.orz.orzframework.mvp.MvpPresenter;
import com.orz.orzframework.mvp.MvpView;

/**
 * Created by DON on 17/02/21.
 */

public interface ComicContract {

  interface ViewModel extends MvpView {

    void initControl();

    void setComicTitle(String text);

    void setComicCover(String url);

    void addComicToList();

    void showProgressDialog(String text);

    void hideProgressDialog();
  }

  interface Presenter extends MvpPresenter {

    void initControl();

    void loadComicHtml();
  }
}
