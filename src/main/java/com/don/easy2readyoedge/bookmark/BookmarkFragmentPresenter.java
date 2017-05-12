package com.don.easy2readyoedge.bookmark;

import android.text.TextUtils;
import com.don.easy2readyoedge.beans.BookBean;
import com.don.easy2readyoedge.configs.CacheConfigs;
import com.don.easy2readyoedge.data.DataListener;
import com.don.easy2readyoedge.data.source.local.BookInfoLocalDataSource;
import com.don.easy2readyoedge.data.source.remote.BookInfoRemoteDataSource;
import com.don.easy2readyoedge.utils.ACache;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orz.orzframework.logger.ORZLog;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by don on 10/23/16.
 */

public class BookmarkFragmentPresenter implements BookmarkContract.Presenter {

  private static final String TAG = "BookmarkFragmentPresenter";
  BookmarkContract.ViewModel mView;
  BookmarkFragmentPresenter(BookmarkContract.ViewModel view){
    this.mView = view;
  }
  @Override
  public void initController(){
    mView.findControl();
  }

  @Override
  public void loadBookList() {

  }

  @Override
  public void updateBookList(ACache aCache) {
    String json = aCache.getAsString(CacheConfigs.BOOK_MARK_LIST);
    if (!TextUtils.isEmpty(json)) {
      Type type = new TypeToken<ArrayList<BookBean>>() {
      }.getType();
      ArrayList<BookBean> list = new Gson().fromJson(json, type);
      ORZLog.i(TAG, "list=" + list.size());
      for (int i = 0, size = list.size(); i < size; i++) {
        if (i == size - 1) {
          request(aCache, list.get(i).getBookUrl(), true);
        } else {
          request(aCache, list.get(i).getBookUrl(), false);
        }
      }
    }
  }

  private void request(final ACache aCache, final String bookUrl, final boolean isHide) {
    BookInfoRemoteDataSource.getInstance().getBookInfo(bookUrl, new DataListener<BookBean>() {
      @Override
      public void onDataReceive(BookBean data) {
        /**save book info**/
        if (null != data) {
          BookBean localBean = BookInfoLocalDataSource.getInstance().getBookInfo(aCache, bookUrl);
          if (null != localBean && null != localBean.getArticles() && localBean.getArticles().size() > 0) {
//            for (int i = 0, size = localBean.getArticles().size(); i < size; i++){
//              localBean.getArticles().get(i).setIsRead(true);
//            }
//            localBean.getArticles().remove(0);
//            localBean.getArticles().remove(0);
//            localBean.getArticles().remove(0);
            /**本地数据与网络数据对比，筛选出网络更新的数据**/
            data.getArticles().removeAll(localBean.getArticles());
            if (null != data.getArticles()) {
              for (int i = 0, size = data.getArticles().size(); i < size; i++) {
                data.getArticles().get(i).setIsRead(false);
              }
              data.getArticles().addAll(localBean.getArticles());
            }
          }
          BookInfoLocalDataSource.getInstance().saveBookInfo(aCache, bookUrl, data);
          if (null != mView) {
            mView.updateData(data);
            if(isHide) {
              mView.hideLoading();
              ORZLog.i(TAG, "hideLoading");
            }
          }
        }
      }

      @Override
      public void onDataNotAvailable() {

      }
    });
  }
}
