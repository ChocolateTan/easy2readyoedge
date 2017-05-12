package com.don.easy2readyoedge.comicinfo;

import android.app.Activity;
import com.don.easy2readyoedge.beans.ArticleBean;
import com.don.easy2readyoedge.utils.ACache;
import com.orz.orzframework.mvp.MvpView;
import java.util.List;

/**
 * Created by don on 12/21/16.
 */

public interface ComicInfoContract {

  interface ViewModel extends MvpView {

    void findControl();

    void setTitle(String text);

    void addDataToList(List<ArticleBean> list);

    void clearList();

    void showComicCover();

    void showSnackbar(android.view.View view, String text);

    void showComic(Activity activity, String url);
  }

  interface Presenter extends MvpView {

    void initControl();

    void addBook(ACache aCache, android.view.View view, String bookName, String bookUrl);

    void getRemoteBookInfo(final ACache aCache, final String bookUrl);

    void getLocalBookInfo(final ACache aCache, final String bookUrl);

    void onClickArticle(Activity activity, ACache aCache, String bookUrl, String articleUrl);
  }
}
