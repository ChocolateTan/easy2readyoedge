package com.don.easy2readyoedge.yoedgecomicinfo;

import android.text.TextUtils;
import android.view.View;
import com.don.easy2readyoedge.beans.BookBean;
import com.don.easy2readyoedge.configs.CacheConfigs;
import com.don.easy2readyoedge.data.DataListener;
import com.don.easy2readyoedge.data.source.local.BookInfoLocalDataSource;
import com.don.easy2readyoedge.data.source.remote.BookInfoRemoteDataSource;
import com.don.easy2readyoedge.utils.ACache;
import com.don.easy2readyoedge.yoedgecomicinfo.YoedgeComicInfoContract.ViewModule;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import javax.inject.Inject;
import retrofit2.Retrofit;

/**
 * Created by don on 10/31/16.
 */

public class YoedgeComicInfoPresenter implements YoedgeComicInfoContract.Presenter {
  private final ViewModule mView;

  @Inject Retrofit.Builder mRetrofitBuilder;
  @Inject ACache mACache;
  @Inject YoedgeComicInfoPresenter(ViewModule view){
    this.mView = view;
  }
  public void initControl() {
    mView.findControl();
  }

  public void loadWebView(String url) {
    mView.showWebContent(url);
  }

  public void addToBookMark(final View view, final ACache aCache, final String bookUrl) {
    BookInfoRemoteDataSource.getInstance().getBookInfo(bookUrl, new DataListener<BookBean>() {
      @Override
      public void onDataReceive(BookBean data) {
//        BookBean bookBean = new BookBean();
//        bookBean.setBookName(bookName);
        data.setBookUrl(bookUrl);
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
            String json = aCache.getAsString(CacheConfigs.BOOK_MARK_LIST);
            if (TextUtils.isEmpty(json)) {
              ArrayList<BookBean> list = new ArrayList<>();
              list.add(data);
              aCache.put(CacheConfigs.BOOK_MARK_LIST, new Gson().toJson(list));
            } else {
              Type type = new TypeToken<ArrayList<BookBean>>() {
              }.getType();
              ArrayList<BookBean> list = new Gson().fromJson(json, type);

              boolean isExist = false;
              for (int i = 0, size = list.size(); i < size; i++) {
                if (bookUrl.equals(list.get(i).getBookUrl())) {
                  isExist = true;
                  list.remove(i);
                  list.add(0, data);
                  break;
                }
              }
              if (isExist) {
                mView.showSnackbar(view, "已收藏");
              } else {
                list.add(0, data);
                mView.showSnackbar(view, "收藏成功");
                aCache.put(CacheConfigs.BOOK_MARK_LIST, new Gson().toJson(list));
              }
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