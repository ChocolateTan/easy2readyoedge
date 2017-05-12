package com.don.easy2readyoedge.beans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by DON on 16/08/22.
 */
public class BookBean implements Serializable {
  private String bookName;
  private String bookUrl;
  /**
   * articleNo : 1
   * articleName : 月光下的异世界之旅1
   */

  private ArrayList<ArticleBean> articles;

  public String getBookName() {
    return bookName;
  }

  public void setBookName(String bookName) {
    this.bookName = bookName;
  }

  public String getBookUrl() {
    return bookUrl;
  }

  public void setBookUrl(String bookUrl) {
    this.bookUrl = bookUrl;
  }

  public ArrayList<ArticleBean> getArticles() {
    return articles;
  }

  public void setArticles(ArrayList<ArticleBean> articles) {
    this.articles = articles;
  }

  @Override
  public boolean equals(Object obj) {
    if(obj instanceof BookBean){
      BookBean that = (BookBean) obj;
      return this.bookUrl.equals(that.getBookUrl());
    }
    return super.equals(obj);
  }
}
