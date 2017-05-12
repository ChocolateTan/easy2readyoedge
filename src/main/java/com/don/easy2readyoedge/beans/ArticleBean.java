package com.don.easy2readyoedge.beans;

/**
 * Created by DON on 16/08/22.
 */
public class ArticleBean {
  private String articleNo;
  private String articleName;
  private String articleUrl;
  private boolean isRead;

  public String getArticleNo() {
    return articleNo;
  }

  public void setArticleNo(String articleNo) {
    this.articleNo = articleNo;
  }

  public String getArticleName() {
    return articleName;
  }

  public void setArticleName(String articleName) {
    this.articleName = articleName;
  }

  public String getArticleUrl() {
    return articleUrl;
  }

  public void setArticleUrl(String articleUrl) {
    this.articleUrl = articleUrl;
  }

  public boolean getIsRead() {
    return isRead;
  }

  public void setIsRead(boolean isRead) {
    this.isRead = isRead;
  }

  @Override
  public boolean equals(Object obj) {
    if(obj instanceof ArticleBean){
      ArticleBean that = (ArticleBean) obj;
      return this.articleUrl.equals(that.getArticleUrl());
    }
    return super.equals(obj);
  }
}
