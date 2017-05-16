package com.don.easy2readyoedge.core.self;


import java.util.List;

/**
 * Created by DON on 16/08/23.
 */
public interface IFAdapter<T> {
  void onSelfBindViewHolder(SelfBaseViewHolder holder, List<T> data, int position);
}
