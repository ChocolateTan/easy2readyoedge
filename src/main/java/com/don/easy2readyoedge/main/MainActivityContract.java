package com.don.easy2readyoedge.main;

import com.orz.orzframework.mvp.MvpPresenter;
import com.orz.orzframework.mvp.MvpView;

/**
 * Created by DON on 17/02/21.
 */

public interface MainActivityContract extends MvpPresenter {

  interface Presenter {

    void initControl();

    void showExitAlert();

    void setToolbar();

    void showHome();

    void showHuiJiComic();

    void showMoreComic();

    void showBookMark();
  }

  interface ViewModel extends MvpView {

    void initControl();

    void showHomePage();

    void showHuiJiComicPage();

    void showMoreComicPage();

    void showBookMarkPage();

    void hideHomePage();

    void hideHuiJiComicPage();

    void hideMoreComicPage();

    void hideBookMarkPage();

    void showExitAlert();

    void setToolbar();
  }
}
