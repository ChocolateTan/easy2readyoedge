package com.don.easy2readyoedge.data.source.remote;

import com.don.easy2readyoedge.apis.ApiService;
import com.don.easy2readyoedge.beans.ArticleBean;
import com.don.easy2readyoedge.beans.BookBean;
import com.don.easy2readyoedge.configs.ApiConfigs;
import com.don.easy2readyoedge.data.DataListener;
import com.don.easy2readyoedge.utils.CommonUtils;

import com.orz.orzframework.logger.ORZLog;
import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by don on 11/27/16.
 */

public class BookInfoRemoteDataSource {
  private static final String TAG = BookInfoRemoteDataSource.class.getSimpleName();
  private static BookInfoRemoteDataSource INSTANCE;

  public static BookInfoRemoteDataSource getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new BookInfoRemoteDataSource();
    }
    return INSTANCE;
  }

  public void getBookInfo(String bookUrl, final DataListener<BookBean> listener){
    Retrofit retrofit = new Retrofit.Builder()
      .baseUrl(ApiConfigs.YOEDGE)
//      .baseUrl(urlStart + url.getHost() + "/")
//      .addConverterFactory(GsonConverterFactory.create())
//      .addConverterFactory(ScalarsConverterFactory.create())
      .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
      .build();

    ApiService apiService = retrofit.create(ApiService.class);
    apiService.getArticle(bookUrl)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new Subscriber<ResponseBody>() {
        @Override
        public void onCompleted() {
          ORZLog.i(TAG, "Get Completed");
        }

        @Override
        public void onError(Throwable e) {
          e.printStackTrace();
          listener.onDataNotAvailable();
        }

        @Override
        public void onNext(final ResponseBody responseBody) {
            save(responseBody, listener);
        }
      });
  }

  private void save(final ResponseBody responseBody, final DataListener<BookBean> listener) {
    Observable.just(responseBody.byteStream())
      .map(new Func1<InputStream, BookBean>() {
        @Override
        public BookBean call(InputStream inputStream) {
          BookBean bookBean = new BookBean();
          InputStream is = responseBody.byteStream();
          BufferedInputStream bis = new BufferedInputStream(is);
          try {
            byte[] buffer = new byte[1024];
            int len;

            StringBuilder stringBuilder = new StringBuilder();

            while ((len = bis.read(buffer)) != -1) {
              stringBuilder.append(new String(buffer, 0, len));
            }

            Pattern pTitle = Pattern.compile("<title>(.*?)</title>");
            Pattern pArticle = Pattern.compile("<a class=\"am-btn am-btn-secondary am-radius am-btn-sm\" style=\"min-width:100%;display:block;\" href=\"(.*?)\">(.*?)</a>");

            Matcher matcherTitle = pTitle.matcher(stringBuilder);
            while (matcherTitle.find()) {
              ORZLog.i(TAG, "ss=" +CommonUtils.delHTMLTag(matcherTitle.group(1)));
              bookBean.setBookName(CommonUtils.delHTMLTag(matcherTitle.group(1)));
            }

            Matcher matcherArticle = pArticle.matcher(stringBuilder);
            ArrayList<ArticleBean> articles = new ArrayList<>();
            ArticleBean articleBean;
            while (matcherArticle.find()) {
              articleBean = new ArticleBean();
              articleBean.setArticleName(matcherArticle.group(2));
              articleBean.setArticleNo(matcherArticle.group(2));
              articleBean.setArticleUrl(matcherArticle.group(1));
              articles.add(0, articleBean);

              bookBean.setArticles(articles);
            }
          } catch (IOException e) {
            e.printStackTrace();
            listener.onDataNotAvailable();
          } finally {
            IOUtils.closeQuietly(bis);
            IOUtils.closeQuietly(is);
          }
          return bookBean;
        }
      })
      .subscribe(new Action1<BookBean>() {
        @Override
        public void call(BookBean bookBean) {
          listener.onDataReceive(bookBean);
        }
      });
  }
}
