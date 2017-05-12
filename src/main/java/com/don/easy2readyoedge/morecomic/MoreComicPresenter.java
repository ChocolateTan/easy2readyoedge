package com.don.easy2readyoedge.morecomic;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.don.easy2readyoedge.apis.ApiService;
import com.don.easy2readyoedge.beans.BookBean;
import com.don.easy2readyoedge.configs.ApiConfigs;
import com.don.easy2readyoedge.configs.CacheConfigs;
import com.don.easy2readyoedge.core.self.SelfLog;
import com.don.easy2readyoedge.utils.ACache;

import com.don.easy2readyoedge.utils.CommonUtils;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by don on 10/24/16.
 */

public class MoreComicPresenter implements MoreComicContract.Presenter {
  private static final String TAG = MoreComicPresenter.class.getSimpleName();
  private List<BookBean> allList = new ArrayList<>();
  private final MoreComicContract.ViewModel mView;

  @Inject Retrofit.Builder mRetrofitBuilder;
  @Inject ACache mACache;
  @Inject MoreComicPresenter(MoreComicContract.ViewModel view) {
    this.mView = view;
  }

  @Override
  public void initControl() {
    mView.initControl();
  }

  @Override
  public void loadComic() {
    requestFuXiang();
    requestReXue();
    requestShaoNv();
    requestKongBu();
  }

  @Override
  public void allComic() {
    addToList(null);
  }

  @Override
  public void searchComic(String search) {
    addToList(search);
  }

  @Override
  public void addToList(String search) {
    mView.clearData();
    if (null != allList && allList.size() > 0 && !TextUtils.isEmpty(search)) {
      List<BookBean> list = new ArrayList<>();
      for (int i = allList.size() - 1; i >= 0; i--) {
        SelfLog.i(TAG, "textView=" + allList.get(i).getBookName());
        if (allList.get(i).getBookName().contains(search)) {
          list.add(allList.get(i));
        }
      }
      SelfLog.i(TAG, "search=" + list.size());
      if (null != list && list.size() > 0) {
        mView.addDataToList(list);
      }
      mView.notifyList();
    } else {
      mView.addDataToList(allList);
    }
  }

  private void requestReXue() {
    Retrofit retrofit = mRetrofitBuilder
      .baseUrl(ApiConfigs.API_BASE_SEARCH)
      .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
      .build();

    ApiService apiService = retrofit.create(ApiService.class);
    apiService.getHot()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new Subscriber<ResponseBody>() {
        @Override
        public void onCompleted() {
          SelfLog.i(TAG, "Get Completed");
        }

        @Override
        public void onError(Throwable e) {
          e.printStackTrace();
        }

        @Override
        public void onNext(final ResponseBody responseBody) {
          new AsyncTask<Void, Void, List<BookBean>>() {

            @Override
            protected List<BookBean> doInBackground(Void... params) {
              return setData(responseBody.byteStream());
            }

            @Override
            protected void onPostExecute(List<BookBean> bookBeen) {
              super.onPostExecute(bookBeen);
              if (null != bookBeen && bookBeen.size() > 0) {
                allList.addAll(bookBeen);
                mView.addDataToList(bookBeen);
                mView.addDataToList(bookBeen);
              }
            }
          }.execute();
        }
      });
  }

  private void requestShaoNv() {
    Retrofit retrofit = mRetrofitBuilder
      .baseUrl(ApiConfigs.API_BASE_SEARCH)
      .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
      .build();

    ApiService apiService = retrofit.create(ApiService.class);
    apiService.getLady()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new Subscriber<ResponseBody>() {
        @Override
        public void onCompleted() {
          SelfLog.i(TAG, "Get Completed");
        }

        @Override
        public void onError(Throwable e) {
          e.printStackTrace();
        }

        @Override
        public void onNext(final ResponseBody responseBody) {
          new AsyncTask<Void, Void, List<BookBean>>() {

            @Override
            protected List<BookBean> doInBackground(Void... params) {
              return setData(responseBody.byteStream());
            }

            @Override
            protected void onPostExecute(List<BookBean> bookBeen) {
              super.onPostExecute(bookBeen);
              if (null != bookBeen && bookBeen.size() > 0) {
                allList.addAll(bookBeen);
                mView.addDataToList(bookBeen);
                mView.addDataToList(bookBeen);
              }
            }
          }.execute();
        }
      });
  }

  private void requestFuXiang() {
    Retrofit retrofit = mRetrofitBuilder
      .baseUrl(ApiConfigs.API_BASE_SEARCH)
      .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
      .build();

    ApiService apiService = retrofit.create(ApiService.class);
    apiService.getBL()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new Subscriber<ResponseBody>() {
        @Override
        public void onCompleted() {
          SelfLog.i(TAG, "Get Completed");
        }

        @Override
        public void onError(Throwable e) {
          e.printStackTrace();
        }

        @Override
        public void onNext(final ResponseBody responseBody) {
          new AsyncTask<Void, Void, List<BookBean>>() {

            @Override
            protected List<BookBean> doInBackground(Void... params) {
              return setData(responseBody.byteStream());
            }

            @Override
            protected void onPostExecute(List<BookBean> bookBeen) {
              super.onPostExecute(bookBeen);
              if (null != bookBeen && bookBeen.size() > 0) {
                allList.addAll(bookBeen);
                mView.addDataToList(bookBeen);
                mView.addDataToList(bookBeen);
              }
            }
          }.execute();
        }
      });
  }

  private void requestKongBu() {
    Retrofit retrofit = mRetrofitBuilder
      .baseUrl(ApiConfigs.API_BASE_SEARCH)
      .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
      .build();

    ApiService apiService = retrofit.create(ApiService.class);
    apiService.getScarm()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new Subscriber<ResponseBody>() {
        @Override
        public void onCompleted() {
          SelfLog.i(TAG, "Get Completed");
        }

        @Override
        public void onError(Throwable e) {
          e.printStackTrace();
        }

        @Override
        public void onNext(final ResponseBody responseBody) {
          new AsyncTask<Void, Void, List<BookBean>>() {

            @Override
            protected List<BookBean> doInBackground(Void... params) {
              return setData(responseBody.byteStream());
            }

            @Override
            protected void onPostExecute(List<BookBean> bookBeen) {
              super.onPostExecute(bookBeen);
              if (null != bookBeen && bookBeen.size() > 0) {
                allList.addAll(bookBeen);
                mView.addDataToList(bookBeen);
                mView.addDataToList(bookBeen);
              }
            }
          }.execute();
        }
      });
  }

  private List<BookBean> setData(InputStream inputStream) {
    InputStream is = null;
    BufferedInputStream bis = null;
    StringBuilder stringBuilder = new StringBuilder();
    try {
      is = inputStream;
      bis = new BufferedInputStream(is);
      byte[] buffer = new byte[1024];
      int len;


      while ((len = bis.read(buffer)) != -1) {
        stringBuilder.append(new String(buffer, 0, len));
      }
      bis.close();
      is.close();

      mACache.put(CacheConfigs.REXUE_LIST, stringBuilder.toString());

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      IOUtils.closeQuietly(is);
      IOUtils.closeQuietly(bis);
    }
    String html = stringBuilder.toString();
    Pattern p = Pattern.compile("\n");
    Matcher m = p.matcher(html);
    html = m.replaceAll("");
//    <p style="font-size:16px;text-align:justify;">
//    <span style="font-family:Microsoft YaHei;">海贼王 &nbsp; </span><span style="color:#003399;"><a href="http://smp.yoedge.com/view/omnibus/1000289" target="_blank"><span style="font-family:Microsoft YaHei;">【点这里看漫画】</span></a></span>
//    </p>
//
//    new Thread(new Runnable() {
//      @Override
//      public void run() {
//        <p style="font-size:16px;text-align:justify;">
//        <span style="font-family:Microsoft YaHei;">海贼王 &nbsp; </span><span style="color:#003399;"><a href="http://smp.yoedge.com/view/omnibus/1000289" target="_blank"><span style="font-family:Microsoft YaHei;">【点这里看漫画】</span></a></span>
//        </p>

//    <span style="font-size:16px;">请倾听死者的声音 &nbsp; &nbsp;&nbsp;<span style="color:#337FE5;font-size:16px;line-height:24px;"><a href="http://smp.yoedge.com/view/omnibus/1000610" target="_blank">【点这里看漫画】</a></span></span>
//    <span style="font-size:16px;">恋上危险校医 &nbsp; &nbsp; &nbsp; &nbsp;<a href="http://smp.yoedge.com/view/omnibus/1000964" target="_blank"><span style="color:#003399;">【点这里看漫画】</span><span style="color:#003399;"></span></a><br />

//      Pattern item = Pattern.compile("<span style=\"font-family:Microsoft YaHei;\">(.*?)</span><span style=\"color:#003399;\"><a href=\"(.*?)\" target=\"_blank\"><span style=\"font-family:Microsoft YaHei;\">");
//    Pattern itemLink = Pattern.compile("<a href=\"http://smp.yoedge.com/view/omnibus/1000289\" target=\"_blank\">");

    /**尋找漫畫記錄**/
//    Pattern item = Pattern.compile("<p(.*?)</p>");
    Pattern item = Pattern.compile("<p(.*?)>(.*?)</p>");
    Pattern itemHref = Pattern.compile("<a href=\"(.*?)\"(.*?)</a>");
    Pattern itemHrefRemove = Pattern.compile("<a(.*?)</a>");

    final ArrayList<BookBean> arrayList = new ArrayList<>();
    Matcher matcherItem = item.matcher(html);
    while (matcherItem.find()) {
//      Spanned sp;
//      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//        sp = Html.fromHtml(matcherItem.group(2), Html.FROM_HTML_MODE_LEGACY);
//      } else {
//        sp = Html.fromHtml(matcherItem.group(2));
//      }
      Matcher matcherItemHref = itemHref.matcher(matcherItem.group(2));
      Matcher matcherItemHrefRemove = itemHrefRemove.matcher(matcherItem.group(2));
      while (matcherItemHref.find()) {

        BookBean bookBean = new BookBean();//.replace("<a" + matcherItemHrefRemove.group(1) + "</a>","")
        while (matcherItemHrefRemove.find()) {
          SelfLog.v(TAG, TAG + " # " + matcherItem.group(2).replace(matcherItemHrefRemove.group(0), "") + " # " + matcherItemHref.group(1));
//          SelfLog.v(TAG, TAG + " remove # " + matcherItemHrefRemove.group(0) + " # ");
          String name = matcherItem.group(2).replace(matcherItemHrefRemove.group(0), "");
          name = CommonUtils.delHTMLTag(name);
//          SelfLog.i(TAG, "ss=" + name.replace("&nbsp;", "").trim());
          bookBean.setBookName(name.replace("&nbsp;", "").trim());
          bookBean.setBookUrl(matcherItemHref.group(1));
          arrayList.add(bookBean);
        }
      }
    }
//    allList.addAll(arrayList);
//    mView.addDataToList(arrayList);
//    mView.addDataToList(arrayList);
    return arrayList;
  }
  @Override
  public void showComicDetail(BookBean bookBean) {
    mView.showComicDetail(bookBean);
  }
}
