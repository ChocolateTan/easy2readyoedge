package com.don.easy2readyoedge.morecomic;

import com.don.easy2readyoedge.beans.BookBean;
import com.don.easy2readyoedge.core.mvp.MvpView;

import android.content.Context;

import java.util.List;

/**
 * Created by don on 12/22/16.
 */

public interface MoreComicContract {
  /**
   * Created by don on 10/24/16.
   */

  interface ViewModel extends MvpView<MoreComicContract.Presenter> {
    void initControl();
    void showComicDetail(BookBean bookBean);
    void addDataToList(List<BookBean> arrayList);

    void showSnackbarInfo(String text);
    void showSnackbarInfo(int textRes);
    void clearData();
    void notifyList();
  }

  interface Presenter{

    void initControl();

    void loadComic();

    void allComic();

    void searchComic(String search);

    void addToList(String search);

    void showComicDetail(BookBean bookBean);
  }
}
