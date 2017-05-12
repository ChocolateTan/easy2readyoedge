package com.don.easy2readyoedge.beans;

import android.text.TextUtils;

import java.text.DecimalFormat;

/**
 * Created by DON on 16/08/26.
 */
public class PageBean {
  private String pageUrl;
  private String pageParent;
  private String pageName;
  private String pageNo;

  public String getPageUrl() {
    return pageUrl;
  }

  public void setPageUrl(String pageUrl) {
    this.pageUrl = pageUrl;
  }

  public String getPageParent() {
    return pageParent;
  }

  public void setPageParent(String pageParent) {
    this.pageParent = pageParent;
  }

  public String getPageName() {
    return pageName;
  }

  public void setPageName(String pageName) {
    this.pageName = pageName;
  }

  public String getPageNo() {
    return pageNo;
  }

  public void setPageNo(String pageNo) {
    this.pageNo = pageNo;
  }

  public String getNextPageName(){
    String[] ary = getPageName().split("\\.");
    int aryLength = ary.length;
    int nextNum = Integer.parseInt(ary[0]) + 1;

    int nextLength = String.valueOf(nextNum).length();

    StringBuilder sb = new StringBuilder();
    for(int i = 0, size = ary[0].length(); i < size; i++){
      sb.append("0");
    }

    if(TextUtils.isEmpty(sb.toString())){
      return getPageName();
    }else {
      DecimalFormat df = new DecimalFormat(sb.toString());
      String str2 = df.format(nextNum);
      return str2 + "." + ary[1];
    }
  }
}
