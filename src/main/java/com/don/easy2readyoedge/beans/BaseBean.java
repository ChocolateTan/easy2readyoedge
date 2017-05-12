package com.don.easy2readyoedge.beans;

import java.io.Serializable;

/**
 * Created by DON on 16/08/23.
 */
public class BaseBean<T> implements Serializable{
  private int status;
  private String info;

  private T data;

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

}
