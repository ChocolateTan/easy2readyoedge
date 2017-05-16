package com.don.easy2readyoedge.core.self;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by DON on 16/08/22.
 */
public abstract class SelfBaseFragment extends Fragment implements IFFindView {
  public FragmentActivity mActivity;
  public Context mContext;
  private View mRootView;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mActivity = getActivity();
    mContext = getContext();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//    ViewModel view = inflater.inflate(R.layout.fragment_category, container, false);
    View view = setFragmentView(inflater, container, savedInstanceState);
    mRootView = view;
    return view;
//    return super.onCreateView(inflater, container, savedInstanceState);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    afterCreateView();
    load();
  }

  abstract public View setFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
  //inflater.inflate(R.layout.fragment_category, container, false);
  abstract public void afterCreateView();
  abstract public void load();

  @Override
  public <T extends View> T find(int id) {
    return (T)mRootView.findViewById(id);
  }
}
