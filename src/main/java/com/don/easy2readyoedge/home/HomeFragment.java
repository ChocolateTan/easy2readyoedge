package com.don.easy2readyoedge.home;

import com.don.easy2readyoedge.R;
import com.don.easy2readyoedge.yoedgecomicinfo.YoedgeComicInfoActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.orz.orzframework.logger.ORZLog;

public class HomeFragment extends Fragment implements HomeContract.ViewModel {
  private static final String TAG = HomeFragment.class.getSimpleName();
  private OnFragmentInteractionListener mListener;
  WebView webView;
  private HomeFragmentPresenter mPresenter;

  public static HomeFragment newInstance() {
    HomeFragment fragment = new HomeFragment();
    return fragment;
  }

  public HomeFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mPresenter = new HomeFragmentPresenter(this);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_home, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mPresenter.initControl();
    mPresenter.showWebView();
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnFragmentInteractionListener) {
      mListener = (OnFragmentInteractionListener) context;
    } else {
      throw new RuntimeException(context.toString()
        + " must implement OnFragmentInteractionListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  @Override
  public void findControl() {
    webView = (WebView) getView().findViewById(R.id.web_view);
  }

  @Override
  public void initWebView() {
    webView.getSettings().setJavaScriptEnabled(true);
    webView.setWebViewClient(getWebViewClient());
    webView.setWebChromeClient(getWebChromeClient());
    webView.getSettings().setDomStorageEnabled(true);
    webView.getSettings().setAllowFileAccess(true);
    webView.getSettings().setAppCacheEnabled(true);
    webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
  }

  @Override
  public void loadWebView(String url) {
    webView.loadUrl(url);
  }

  @Override
  public void showComicDetail() {

  }

  public interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
//    void onNavHomeClick();
  }

  public WebViewClient getWebViewClient() {
    return new WebViewClient() {
      @Override
      public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
      }

      @Override
      public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        ORZLog.i(TAG, "url=" + url);
      }

      @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if(url.startsWith("http://smp.yoedge.com/view/omnibus/")) {
          YoedgeComicInfoActivity.launchActivity(getActivity(), url);
          return true;
        }else{
          return false;
        }
      }
    };
  }

  public WebChromeClient getWebChromeClient() {
    return new WebChromeClient() {

    };
  }
}
