package com.don.easy2readyoedge.yoedgecomicinfo;

import com.don.easy2readyoedge.ApplicationModule;
import com.don.easy2readyoedge.NetModule;
import com.don.easy2readyoedge.StorageDataModule;

import com.don.easy2readyoedge.R;
import com.don.easy2readyoedge.configs.ExtraConfigs;
import com.don.easy2readyoedge.utils.ACache;
import com.don.easy2readyoedge.utils.CommonUtils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.don.easy2readyoedge.yoedgecomicinfo.YoedgeComicInfoContract.ViewModule;
import com.orz.orzframework.logger.ORZLog;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

public class YoedgeComicInfoActivity extends AppCompatActivity implements ViewModule {

  private static final String TAG = YoedgeComicInfoActivity.class.getSimpleName();
  private WebView webView;
  private String mTitle;
  @Inject YoedgeComicInfoPresenter mPresenter;

  public static void launchActivity(Activity activity, String url) {
    Intent intent = new Intent(activity, YoedgeComicInfoActivity.class);
    intent.putExtra(ExtraConfigs.EX_URL, url);
    activity.startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_yoedge_comic_info);

    DaggerYoedgeComicInfoComponect.builder()
      .applicationModule(new ApplicationModule(getApplication()))
      .storageDataModule(new StorageDataModule())
      .netModule(new NetModule())
      .yoedgeComicInfoModule(new YoedgeComicInfoModule(this))
      .build().inject(this);

    mPresenter.initControl();

    if(getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey(ExtraConfigs.EX_URL)){
      mPresenter.loadWebView(getIntent().getStringExtra(ExtraConfigs.EX_URL));
    } else {
      finish();
    }

  }

  public WebViewClient getWebViewClient() {
    return new WebViewClient() {
      @Override
      public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
      }

      @Override
      public void onPageFinished(WebView view, final String url) {
        super.onPageFinished(view, url);
        ORZLog.i(TAG, "url=" + url);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //http://smp.yoedge.com/view/omnibus/1000491
        Pattern pTitle = Pattern.compile("http://smp.yoedge.com/view/omnibus/(.*)");
        Matcher matcherTitle = pTitle.matcher(url);
        if (matcherTitle.find()) {
          fab.show();
          fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//        http://smp.yoedge.com/view/omnibus/1000289
              mPresenter.addToBookMark(view, ACache.get(YoedgeComicInfoActivity.this), url);
            }
          });
        } else {
          fab.hide();
        }
      }
    };
  }

  public WebChromeClient getWebChromeClient() {
    return new WebChromeClient() {
      @Override
      public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);

        Pattern pTitle = Pattern.compile("http://smp.yoedge.com/view/omnibus/(.*)");
        Matcher matcherTitle = pTitle.matcher(webView.getUrl());
        if (matcherTitle.find()) {
          mTitle = title;
        }

      }
    };
  }

  @Override
  public void onBackPressed() {
    if (webView != null && webView.canGoBack()) {
      webView.goBack();
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public void findControl() {
    webView = (WebView) findViewById(R.id.web_view);
    CommonUtils.setWebView(webView, getWebViewClient(), getWebChromeClient());
    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.hide();
  }

  @Override
  public void showWebContent(String url) {
    webView.loadUrl(url);
  }

  @Override
  public void showSnackbar(View view, String text) {
    Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
    if (snackbar.getView() != null) {
      snackbar.getView().setBackgroundColor(Color.parseColor("#FFFFFF"));//修改view的背景色
      ((TextView) snackbar.getView().findViewById(R.id.snackbar_text)).setTextColor(Color.parseColor("#000000"));//获取Snackbar的message控件，修改字体颜色
    }
    snackbar.show();
  }
}
