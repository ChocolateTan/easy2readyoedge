package com.don.easy2readyoedge.main;

import com.don.easy2readyoedge.core.mvp.MvpView;

/**
 * Created by DON on 17/02/21.
 */

public interface MainActivityContract {
  interface Presenter {
    void initControl();

    void showExitAlert();

    void setToolbar();

    void showHome();

    void showHuiJiComic();

    void showMoreComic();

    void showBookMark();
  }
  interface ViewModel extends MvpView<Presenter> {

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
