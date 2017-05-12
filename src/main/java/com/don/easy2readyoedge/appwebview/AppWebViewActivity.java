package com.don.easy2readyoedge.appwebview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.HttpAuthHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.don.easy2readyoedge.R;
import com.don.easy2readyoedge.configs.ExtraConfigs;
import com.don.easy2readyoedge.utils.CommonUtils;
import com.orz.orzframework.logger.ORZLog;

public class AppWebViewActivity extends AppCompatActivity implements AppWebContract.ViewModel {

  private static final String TAG = AppWebViewActivity.class.getSimpleName();
  private WebView webView;
  private String mUrl;
  private AppWebViewPresenter mPresenter;

  public static void launchActivity(Context context, String url) {
    Intent intent = new Intent(context, AppWebViewActivity.class);
    intent.putExtra(ExtraConfigs.EX_URL, url);
    context.startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_web_view);

    if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey(ExtraConfigs.EX_URL)) {
      mUrl = getIntent().getExtras().getString(ExtraConfigs.EX_URL);
    } else {
      finish();
    }
    mPresenter = new AppWebViewPresenter(this);
    mPresenter.initControl();
    mPresenter.loadWebView(mUrl);
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
      }


      @Override
      public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
      }

      @Override
      public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        ORZLog.i(TAG, "onPageFinished # url=" + url);
      }
    };
  }


  public WebChromeClient getWebChromeClient() {
    return new WebChromeClient() {

    };
  }

  @Override
  public void onBackPressed() {
    if (!webView.getUrl().equals(mUrl)) {
      webView.loadUrl(mUrl);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public void findControl() {
    webView = (WebView) findViewById(R.id.web_view);
    CommonUtils.setWebView(webView, getWebViewClient(), getWebChromeClient());
  }

  @Override
  public void showWebViewContent(String url) {
    webView.loadUrl(url);
  }
}
