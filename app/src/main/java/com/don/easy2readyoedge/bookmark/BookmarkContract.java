package com.don.easy2readyoedge.bookmark;

import com.don.easy2readyoedge.beans.BookBean;
import com.don.easy2readyoedge.core.mvp.MvpView;
import com.don.easy2readyoedge.utils.ACache;


/**
 * Created by DON on 17/02/21.
 */

public interface BookmarkContract {


  interface ViewModel extends MvpView {
    void findControl();
    void updateData(BookBean bookBean);
    void showLoading();
    void hideLoading();
  }

  interface Presenter{
    void initController();
    void loadBookList();
    void updateBookList(ACache aCache);
  }
}
