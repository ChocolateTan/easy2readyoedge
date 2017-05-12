package com.don.easy2readyoedge.utils;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DON on 16/08/30.
 */
public class CommonUtils {
  public static void setWebView(WebView webView, WebViewClient webViewClient, WebChromeClient webChromeClient){
    webView.getSettings().setJavaScriptEnabled(true);
    webView.setWebViewClient(webViewClient);
    webView.setWebChromeClient(webChromeClient);
    webView.getSettings().setDomStorageEnabled(true);
    webView.getSettings().setAllowFileAccess(true);
    webView.getSettings().setAppCacheEnabled(true);
    webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
  }

  public static String delHTMLTag(String htmlStr){
    String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
    String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
    String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式

    Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
    Matcher m_script=p_script.matcher(htmlStr);
    htmlStr=m_script.replaceAll(""); //过滤script标签

    Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
    Matcher m_style=p_style.matcher(htmlStr);
    htmlStr=m_style.replaceAll(""); //过滤style标签

    Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
    Matcher m_html=p_html.matcher(htmlStr);
    htmlStr=m_html.replaceAll(""); //过滤html标签

    return htmlStr.trim(); //返回文本字符串
  }
}
