package com.don.easy2readyoedge.core.self;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DON on 16/08/22.
 */
public class SelfBaseAdapter<T> extends RecyclerView.Adapter<SelfBaseViewHolder> {

  private final IFAdapter mIfAdapter;
  private List<T> mData;
  private final int mId;

  SelfBaseAdapter(List<T> data, IFAdapter ifAdapter) {
    this(0, data, ifAdapter);
  }

  public SelfBaseAdapter(int id, List<T> data, IFAdapter ifAdapter) {
    mId = id;
    mData = data;
    mIfAdapter = ifAdapter;
  }

  @Override
  public SelfBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = View.inflate(parent.getContext(), mId, null);
    return new SelfBaseViewHolder(view);
  }

  @Override
  public void onBindViewHolder(SelfBaseViewHolder holder, int position) {
    mIfAdapter.onSelfBindViewHolder(holder, mData, position);
  }

//  @Override
//  public void onBindViewHolder(SelfBaseViewHolder holder, int position, List<Object> payloads) {
//    super.onBindViewHolder(holder, position, payloads);
//  }

  @Override
  public int getItemCount() {
    if (mData == null) {
//      System.out.println("mData.size=0");
      return 0;
    }
//    System.out.println("mData.size="+mData.size());
    return mData.size();
  }

  public synchronized void addData(List<T> data) {
    if (mData == null) {
      mData = new ArrayList<>();
    }

    mData.addAll(data);
    notifyDataSetChanged();
  }

  public synchronized void addData(int position, List<T> data) {
    if (mData == null) {
      mData = new ArrayList<>();
    }

    mData.addAll(position, data);
    notifyDataSetChanged();
  }

  public synchronized void addData(T data) {
    if (mData == null) {
      mData = new ArrayList<>();
    }

    mData.add(data);
    notifyDataSetChanged();
  }

  public synchronized void addData(int position, T data) {
    if (mData == null) {
      mData = new ArrayList<>();
    }

    mData.add(position, data);
    notifyDataSetChanged();
  }

  public synchronized void removeData(int position) {
    mData.remove(position);
    notifyDataSetChanged();
  }

  public synchronized void clearData() {
    if (mData != null) {
      mData.clear();
    }
  }

  public synchronized void clearDataNotify() {
    if (mData != null) {
      mData.clear();
    }
    notifyDataSetChanged();
  }

  public synchronized List<T> getData() {
    return mData;
  }

  public synchronized void updateData(int position, T data) {
    mData.remove(position);
    mData.add(position, data);
  }
}
