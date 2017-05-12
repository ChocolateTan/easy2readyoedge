package com.don.easy2readyoedge.data.source.local;

import com.don.easy2readyoedge.beans.BookBean;
import com.don.easy2readyoedge.configs.CacheConfigs;
import com.don.easy2readyoedge.core.self.SelfJson;
import com.don.easy2readyoedge.core.self.SelfLog;
import com.don.easy2readyoedge.utils.ACache;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by don on 11/27/16.
 */

public class BookInfoLocalDataSource {

  private static final String TAG = BookInfoLocalDataSource.class.getSimpleName();
  private static BookInfoLocalDataSource INSTANCE;

  public static BookInfoLocalDataSource getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new BookInfoLocalDataSource();
    }
    return INSTANCE;
  }

  public List<BookBean> getBookmarkList(ACache aCache) {
    String json = aCache.getAsString(CacheConfigs.BOOK_MARK_LIST);

    if (SelfJson.isJson(json)) {
      Type type = new TypeToken<List<BookBean>>() {
      }.getType();
      return SelfJson.fromJson(json, type);
    }

    return null;
  }

  public void saveBookInfo(ACache aCache, String urlName, BookBean bookBean) {
    String json = SelfJson.toJson(bookBean);
    SelfLog.i(TAG, "saveBookInfo=" + json);
    aCache.put(CacheConfigs.BOOK_INFO + "_" + urlName, json);
  }

  public BookBean getBookInfo(ACache aCache, String urlName) {
    String json = aCache.getAsString(CacheConfigs.BOOK_INFO + "_" + urlName);

    if (SelfJson.isJson(json)) {
      return SelfJson.fromJson(json, BookBean.class);
    }

    return null;
  }

  public void updateBookArticleRead(ACache aCache, String urlName, String urlArticle) {
    BookBean bean = getBookInfo(aCache, urlName);
    if (null == bean) return;
    if (null == bean.getArticles() || bean.getArticles().size() == 0) return;

    for (int i = 0, size = bean.getArticles().size(); i < size; i++) {
      if (bean.getArticles().get(i).getArticleUrl().equals(urlArticle)) {
        bean.getArticles().get(i).setIsRead(true);
        break;
      }
    }
    saveBookInfo(aCache, urlName, bean);
  }
}
