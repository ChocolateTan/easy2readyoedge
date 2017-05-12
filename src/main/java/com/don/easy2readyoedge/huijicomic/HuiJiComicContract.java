package com.don.easy2readyoedge.huijicomic;

import com.don.easy2readyoedge.beans.BookBean;
import com.don.easy2readyoedge.core.mvp.MvpView;

import android.content.Context;

import java.util.List;

/**
 * Created by DON on 17/02/21.
 */

public interface HuiJiComicContract {
  interface Presenter{

    void initControl();

    void loadData();

    void onClickComicItem(BookBean bookBean);
  }

  interface ViewModel {
    void initControl();
    void setListData(List<BookBean> bookBeen);
    void updateListStatus();
    void showWebViewToReadBookDetail(BookBean bookBean);
    void showBookDetail(BookBean bookBean);
  }
}
