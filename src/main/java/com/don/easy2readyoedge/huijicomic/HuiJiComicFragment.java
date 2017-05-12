package com.don.easy2readyoedge.huijicomic;

import com.don.easy2readyoedge.ApplicationModule;
import com.don.easy2readyoedge.StorageDataModule;
import com.don.easy2readyoedge.appwebview.AppWebViewActivity;

import com.don.easy2readyoedge.R;
import com.don.easy2readyoedge.comicinfo.ComicInfoActivity;
import com.don.easy2readyoedge.adapters.BookAdapter;
import com.don.easy2readyoedge.beans.BookBean;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HuiJiComicFragment extends Fragment
  implements HuiJiComicContract.ViewModel, CompoundButton.OnCheckedChangeListener {

  private OnFragmentInteractionListener mListener;
  private BookAdapter mAdapter;
  private RecyclerView recyclerView;
  @Inject
  HuiJiComicPresenter mPresenter;
//  @Inject Application mApplication;

  public static HuiJiComicFragment newInstance() {
    HuiJiComicFragment fragment = new HuiJiComicFragment();
    return fragment;
  }

  public HuiJiComicFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_huiji_comic, container, false);
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
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    DaggerHuiJiComicComponent.builder()
      .huiJiComicModule(new HuiJiComicModule(this))
      .applicationModule(new ApplicationModule(getActivity().getApplication()))
      .storageDataModule(new StorageDataModule())
      .build()
      .inject(this);

    mPresenter.initControl();
    mPresenter.loadData();
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  public BookAdapter.BookAdapterListener getCategoryAdapter() {
    return (bookBean) -> mPresenter.onClickComicItem(bookBean);
  }

  @Override
  public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//    mPresenter.setIsUserWebView(mActivity, b);
  }

  @Override
  public void initControl() {
    recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);
    GridLayoutManager manager = new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false);
    mAdapter = new BookAdapter(new ArrayList<BookBean>(), getCategoryAdapter());
    recyclerView.setLayoutManager(manager);
    recyclerView.setAdapter(mAdapter);
  }

  @Override
  public void setListData(List<BookBean> bookBeen) {
    mAdapter.clearDataNotify();
    mAdapter.addData(bookBeen);
  }

  @Override
  public void updateListStatus() {
    mAdapter.notifyDataSetChanged();
  }

  @Override
  public void showWebViewToReadBookDetail(BookBean bookBean) {
    AppWebViewActivity.launchActivity(getActivity(), bookBean.getBookUrl());
  }

  @Override
  public void showBookDetail(BookBean bookBean) {
    ComicInfoActivity.launchActivity(getActivity(), bookBean);
  }


  public interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
//    void onNavBookClick();
  }
}
