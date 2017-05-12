package com.don.easy2readyoedge.data;

/**
 * Created by don on 11/30/16.
 */

public interface DataListener<T> {
  void onDataReceive(T data);
  void onDataNotAvailable();
}
