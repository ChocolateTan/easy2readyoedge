package com.don.easy2readyoedge.comicinfo;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;

import com.don.easy2readyoedge.beans.BookBean;
import com.don.easy2readyoedge.comic.ComicContract;
import com.don.easy2readyoedge.configs.CacheConfigs;
import com.don.easy2readyoedge.data.DataListener;
import com.don.easy2readyoedge.data.source.local.BookInfoLocalDataSource;
import com.don.easy2readyoedge.data.source.remote.BookInfoRemoteDataSource;
import com.don.easy2readyoedge.utils.ACache;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by don on 10/30/16.
 */

public class ComicInfoPresenter implements ComicInfoContract.Presenter {
  private static final String TAG = ComicInfoPresenter.class.getSimpleName();
  private final ComicInfoContract.ViewModel mView;

  ComicInfoPresenter(ComicInfoContract.ViewModel view){
    this.mView = view;
  }

  @Override
  public void initControl() {
    mView.findControl();
  }

  @Override
  public void addBook(ACache aCache, View view, String bookName, String bookUrl) {
    BookBean bookBean = new BookBean();
    bookBean.setBookName(bookName);
    bookBean.setBookUrl(bookUrl);

    String json = aCache.getAsString(CacheConfigs.BOOK_MARK_LIST);
    if (TextUtils.isEmpty(json)) {
      ArrayList<BookBean> list = new ArrayList<>();
      list.add(bookBean);
      aCache.put(CacheConfigs.BOOK_MARK_LIST, new Gson().toJson(list));
    } else {
      Type type = new TypeToken<ArrayList<BookBean>>() {
      }.getType();
      ArrayList<BookBean> list = new Gson().fromJson(json, type);

      boolean isExist = false;
      for (int i = 0, size = list.size(); i < size; i++) {
        if (list.get(i).getBookUrl().equals(bookUrl)) {
          isExist = true;
          list.remove(i);
          list.add(0, bookBean);
          break;
        }
      }
      if (isExist) {
        mView.showSnackbar(view, "已收藏");
      } else {
        list.add(0, bookBean);
        mView.showSnackbar(view, "收藏成功");
        aCache.put(CacheConfigs.BOOK_MARK_LIST, new Gson().toJson(list));
      }
    }
  }

  @Override
  public void getRemoteBookInfo(final ACache aCache, final String bookUrl) {
    BookInfoRemoteDataSource.getInstance().getBookInfo(bookUrl, new DataListener<BookBean>() {
      @Override
      public void onDataReceive(BookBean data) {
        /**save book info**/
        if (null != data) {
          BookBean localBean = BookInfoLocalDataSource.getInstance().getBookInfo(aCache, bookUrl);
          if(null != localBean && null != localBean.getArticles() && localBean.getArticles().size() > 0){
//            for (int i = 0, size = localBean.getArticles().size(); i < size; i++){
//              localBean.getArticles().get(i).setIsRead(true);
//            }
//            localBean.getArticles().remove(0);
//            localBean.getArticles().remove(0);
//            localBean.getArticles().remove(0);
            /**本地数据与网络数据对比，筛选出网络更新的数据**/
            data.getArticles().removeAll(localBean.getArticles());
            if(null != data.getArticles()) {
              for (int i = 0, size = data.getArticles().size(); i < size; i++){
                data.getArticles().get(i).setIsRead(false);
              }
              data.getArticles().addAll(localBean.getArticles());
            }
          }
          BookInfoLocalDataSource.getInstance().saveBookInfo(aCache, bookUrl, data);
        }

        if (null != mView) {
          if (!TextUtils.isEmpty(data.getBookName())) {
            mView.setTitle(data.getBookName());
          } else {
            mView.setTitle("暂时无名");
          }
          if (data.getArticles() != null && data.getArticles().size() > 0) {
            mView.clearList();
            mView.addDataToList(data.getArticles());
          }
        }
      }

      @Override
      public void onDataNotAvailable() {

      }
    });
  }

  @Override
  public void getLocalBookInfo(ACache aCache, String bookUrl) {
    BookBean data = BookInfoLocalDataSource.getInstance().getBookInfo(aCache, bookUrl);
    if (null == data) return;
    if (!TextUtils.isEmpty(data.getBookName())) {
      mView.setTitle(data.getBookName());
    } else {
      mView.setTitle("暂时无名");
    }
    if (data.getArticles() != null && data.getArticles().size() > 0) {
      mView.clearList();
      mView.addDataToList(data.getArticles());
    }
  }

  @Override
  public void onClickArticle(Activity activity, ACache aCache, String bookUrl, String articleUrl) {
    BookInfoLocalDataSource.getInstance().updateBookArticleRead(aCache, bookUrl, articleUrl);
    mView.showComic(activity, articleUrl);
  }
}
