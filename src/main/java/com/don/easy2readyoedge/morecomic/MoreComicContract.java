package com.don.easy2readyoedge.morecomic;

import com.don.easy2readyoedge.beans.BookBean;
import com.orz.orzframework.mvp.MvpPresenter;
import com.orz.orzframework.mvp.MvpView;
import java.util.List;

/**
 * Created by don on 12/22/16.
 */

public interface MoreComicContract {

  interface ViewModel extends MvpView {

    void initControl();

    void showComicDetail(BookBean bookBean);

    void addDataToList(List<BookBean> arrayList);

    void showSnackbarInfo(String text);

    void showSnackbarInfo(int textRes);

    void clearData();

    void notifyList();
  }

  interface Presenter extends MvpPresenter {

    void initControl();

    void loadComic();

    void allComic();

    void searchComic(String search);

    void addToList(String search);

    void showComicDetail(BookBean bookBean);
  }
}
