package com.don.easy2readyoedge.comic;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.webkit.HttpAuthHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.don.easy2readyoedge.R;
import com.don.easy2readyoedge.adapters.PageAdapter;
import com.don.easy2readyoedge.beans.ArticleBean;
import com.don.easy2readyoedge.beans.PageBean;
import com.don.easy2readyoedge.configs.ExtraConfigs;
import com.don.easy2readyoedge.core.self.IFAdapter;
import com.don.easy2readyoedge.core.self.SelfBaseViewHolder;
import com.don.easy2readyoedge.core.self.SelfLog;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComicActivity extends AppCompatActivity
        implements ComicContract.ViewModel {

  private static final String TAG = ComicActivity.class.getSimpleName();
  private ArticleBean mArticleBean;
  private WebView webView;
  private PageBean mPageBean;
//  private ViewPager viewPager;
  private PageAdapter mAdapter;
  private RecyclerView recyclerView;
  private ProgressDialog progressDialog;
  private ComicPresenter mPresenter;

  public static void launchActivity(Context context, ArticleBean articleBean) {
    Intent intent = new Intent(context, ComicActivity.class);
    intent.putExtra(ExtraConfigs.EX_ARTICLE_URL, new Gson().toJson(articleBean));
//    intent.putExtra(ExtraConfigs.EX_ARTICLE_URL, articleBean.getArticleUrl());
    context.startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_page);
    mPresenter = new ComicPresenter(this);
    mPresenter.initControl();
    if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey(ExtraConfigs.EX_ARTICLE_URL)) {
      String json = getIntent().getExtras().getString(ExtraConfigs.EX_ARTICLE_URL);
      mArticleBean = new Gson().fromJson(json, ArticleBean.class);

      webView.loadUrl(mArticleBean.getArticleUrl());
    } else {
      finish();
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    if (null != webView) {
      webView.onResume();
    }
  }

  @Override
  public void onPause() {
    super.onPause();
    if (null != webView) {
      webView.onPause();
    }
  }

  @Override
  public void onDestroy() {
    if (null != webView) {
      webView.destroy();
      webView = null;
    }
    super.onDestroy();
  }

  public WebViewClient getWebViewClient() {
    return new WebViewClient() {
      @Override
      public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        super.onReceivedHttpAuthRequest(view, handler, host, realm);
      }

      @Override
      public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
        Pattern pTitle = Pattern.compile("http://smp.yoedge.com/smp-app/(.*?)/shinmangaplayer/res/pages/(.*?)/(.*)");
        Matcher matcherTitle = pTitle.matcher(url);
        while (matcherTitle.find()) {
          SelfLog.i(TAG, "page image # url=" + view.getUrl());

          SelfLog.i(TAG, "onLoadResource # " + matcherTitle.group());
          SelfLog.i(TAG, "onLoadResource # " + matcherTitle.group(1));
          SelfLog.i(TAG, "onLoadResource #" + matcherTitle.group(2));
          SelfLog.i(TAG, "onLoadResource #" + matcherTitle.group(3));

          setFirstPage(view, url, matcherTitle.group(1), matcherTitle.group(2), matcherTitle.group(3));
          break;
        }
      }


      @Override
      public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
      }

      @Override
      public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        SelfLog.i(TAG, "onPageFinished # url=" + url);
      }

      @Override
      public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        SelfLog.i(TAG, "shouldOverrideUrlLoading # url=" + view.getUrl());
//        if(view.getUrl().equals("http://rd.gdatacube.net/dc/html5/sync")){
//          return true;
//        }
        return super.shouldOverrideUrlLoading(view, request);
      }

      @Override
      public WebResourceResponse shouldInterceptRequest(final WebView view, WebResourceRequest request) {
//        if(view != null && webView != null) {
//          runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//              System.out.println("shouldInterceptRequest # url=" + view.getUrl());
//            }
//          });
//        }
//        if(view.getUrl().equals("http://rd.gdatacube.net/dc/html5/sync")){
//        System.out.println("into shouldInterceptRequest # url=" + view.getUrl());
//          return null;
//        }
        return super.shouldInterceptRequest(view, request);
      }
    };
  }
  public WebChromeClient getWebChromeClient() {
    return new WebChromeClient() {

    };
  }
  private synchronized void setFirstPage(WebView webView, String url, String parent, String no, String name){
    if(mPageBean == null) {
      mPageBean = new PageBean();
      mPageBean.setPageUrl(url);
      mPageBean.setPageParent(parent);
      mPageBean.setPageNo(no);
      mPageBean.setPageName(name);
      requestPage(mPageBean);
    }
    webView.destroy();
  }
//int o=0;
  private synchronized void requestPage(final PageBean pageBean){
    SelfLog.i(TAG, "next #" + pageBean.getPageUrl());
//    Pattern pTitle = Pattern.compile("http://smp.yoedge.com/smp-app/(.*?)/shinmangaplayer/res/pages/(.*?)/(.*)");
//          Matcher matcherTitle = pTitle.matcher(pageBean.getPageUrl());
//          String pageUrl;
//          PageBean nextBean = new PageBean();
//
//          while (matcherTitle.find()) {
//
//            pageUrl = pageBean.getPageUrl().replace(matcherTitle.group(3), pageBean.getNextPageName());
//
////            System.out.println("next # matcherTitle.group(3) # " + matcherTitle.group(3));
//
//            nextBean.setPageUrl(pageUrl);
//            nextBean.setPageParent(matcherTitle.group(1));
//            nextBean.setPageNo(matcherTitle.group(2));
//            nextBean.setPageName(pageBean.getNextPageName());
//          }
//        if(o<=10) {
//          o=o+1;
//          requestPage(nextBean);
//        }
    showProgressDialog("wait");
      exists(pageBean.getPageUrl(), new CallBack() {
        @Override
        public void success() {
          runOnUiThread(new Runnable() {
            @Override
            public void run() {
              mAdapter.addData(pageBean);
            }
          });

          Pattern pTitle = Pattern.compile("http://smp.yoedge.com/smp-app/(.*?)/shinmangaplayer/res/pages/(.*?)/(.*)");
          Matcher matcherTitle = pTitle.matcher(pageBean.getPageUrl());
          String pageUrl;
          PageBean nextBean = new PageBean();

          while (matcherTitle.find()) {
            pageUrl = pageBean.getPageUrl().replace(matcherTitle.group(3), pageBean.getNextPageName());
            nextBean.setPageUrl(pageUrl);
            nextBean.setPageParent(matcherTitle.group(1));
            nextBean.setPageNo(matcherTitle.group(2));
            nextBean.setPageName(pageBean.getNextPageName());
          }

          requestPage(nextBean);
          runOnUiThread(new Runnable() {
            @Override
            public void run() {
              hideProgressDialog();
            }
          });
        }

        @Override
        public void fail() {
          SelfLog.i(TAG, "fail to get = " + pageBean.getPageUrl());
        }
      });
  }

  public IFAdapter getPageAdapter() {
    return new IFAdapter() {
      @Override
      public void onSelfBindViewHolder(SelfBaseViewHolder holder, List data, int position) {

      }
    };
  }

  @Override
  public void initControl() {
    webView = new WebView(this);
    webView.getSettings().setJavaScriptEnabled(true);
    webView.setWebViewClient(getWebViewClient());
    webView.setWebChromeClient(getWebChromeClient());
    webView.getSettings().setDomStorageEnabled(true);
    webView.getSettings().setAllowFileAccess(true);
    webView.getSettings().setAppCacheEnabled(true);
    webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

    recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    mAdapter = new PageAdapter(this, new ArrayList<PageBean>(), getPageAdapter());
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    recyclerView.setLayoutManager(linearLayoutManager);
    recyclerView.setAdapter(mAdapter);
  }

  @Override
  public void setComicTitle(String text) {

  }

  @Override
  public void setComicCover(String url) {

  }

  @Override
  public void addComicToList() {

  }

  @Override
  public void showProgressDialog(String text) {
    if(!TextUtils.isEmpty(text)){
      progressDialog = ProgressDialog.show(this, null, text, true, false);
    }
  }

  @Override
  public void hideProgressDialog() {
    if(progressDialog != null){
      progressDialog.dismiss();
      progressDialog = null;
    }
  }

  interface CallBack{
    void success();
    void fail();
  }
  void exists(final String urlName , final CallBack callBack) {
    new AsyncTask<Void, Void, Void>() {

      @Override
      protected Void doInBackground(Void... voids) {
        try {
          //设置此类是否应该自动执行 HTTP 重定向（响应代码为 3xx 的请求）。
          HttpURLConnection.setFollowRedirects(false);
          //到 URL 所引用的远程对象的连接
          HttpURLConnection con = (HttpURLConnection) new URL(urlName)
            .openConnection();
           /* 设置 URL 请求的方法， GET POST HEAD OPTIONS PUT DELETE TRACE 以上方法之一是合法的，具体取决于协议的限制。*/
          con.setRequestMethod("HEAD");
          //从 HTTP 响应消息获取状态码
          if(con.getResponseCode() == HttpURLConnection.HTTP_OK){
            callBack.success();
          }else{
            callBack.fail();
          }
        } catch (Exception e) {
          e.printStackTrace();
//          return false;
          callBack.fail();
          runOnUiThread(new Runnable() {
            @Override
            public void run() {
              Toast.makeText(ComicActivity.this, "解析不成功，请再次尝试或使用启用WebView浏览", Toast.LENGTH_LONG).show();
            }
          });
        }
        return null;
      }
    }.execute();
  }
}
