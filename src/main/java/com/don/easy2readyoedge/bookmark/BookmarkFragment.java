package com.don.easy2readyoedge.bookmark;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.don.easy2readyoedge.R;
import com.don.easy2readyoedge.comicinfo.ComicInfoActivity;
import com.don.easy2readyoedge.beans.BookBean;
import com.don.easy2readyoedge.configs.CacheConfigs;
import com.don.easy2readyoedge.utils.ACache;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class BookmarkFragment extends Fragment
implements BookmarkContract.ViewModel, SwipeRefreshLayout.OnRefreshListener {

  private OnFragmentInteractionListener mListener;
  private BookmarkAdapter mAdapter;
  private BookmarkFragmentPresenter mPresenter;
  private SwipeRefreshLayout swipeRefresh;

  public static BookmarkFragment newInstance() {
    BookmarkFragment fragment = new BookmarkFragment();
    return fragment;
  }

  public BookmarkFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_bookmark, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mPresenter = new BookmarkFragmentPresenter(this);
    mPresenter.initController();
    loadBookmark();
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
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  @Override
  public void onResume() {
    super.onResume();
    mAdapter.notifyDataSetChanged();
  }

  @Override
  public void findControl() {
    RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);
    swipeRefresh = (SwipeRefreshLayout) getView().findViewById(R.id.swipe_refresh);
    mAdapter = new BookmarkAdapter(ACache.get(getActivity()), new ArrayList<BookBean>(), getBookmarkAdapter());
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    recyclerView.setLayoutManager(linearLayoutManager);
    recyclerView.setAdapter(mAdapter);

    swipeRefresh.setOnRefreshListener(this);
  }

  @Override
  public void updateData(BookBean bookBean) {
    mAdapter.updateDataNotify(bookBean);
  }

  @Override
  public void showLoading() {
    swipeRefresh.post(new Runnable() {
      @Override
      public void run() {
        swipeRefresh.setRefreshing(true);
      }
    });
  }

  @Override
  public void hideLoading() {
    swipeRefresh.post(new Runnable() {
      @Override
      public void run() {
        swipeRefresh.setRefreshing(false);
      }
    });
  }

  @Override
  public void onRefresh() {
//    if (!swipeRefresh.isRefreshing()) {
      mPresenter.updateBookList(ACache.get(getActivity()));
//    }
  }

//  @Override
//  public void setPresenter(Object presenter) {
//
//  }

  public interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
//    void onNavBookmarkClick();
  }

  public BookmarkAdapter.BookmarkAdapterListener getBookmarkAdapter() {
    return new BookmarkAdapter.BookmarkAdapterListener() {
      @Override
      public void onClickItem(BookBean bookBean) {
        ComicInfoActivity.launchActivity(getActivity(), bookBean);
          ComicInfoActivity.launchActivity(getActivity(), bookBean);
      }

      @Override
      public void onClickDelete(BookBean bookBean) {
        deleteBookmark(bookBean);
      }
    };
  }

  private void loadBookmark() {
    String json = ACache.get(getActivity()).getAsString(CacheConfigs.BOOK_MARK_LIST);
    if(!TextUtils.isEmpty(json)) {
      Type type = new TypeToken<ArrayList<BookBean>>() {}.getType();
      ArrayList<BookBean> list = new Gson().fromJson(json, type);
      mAdapter.clearDataNotify();
      mAdapter.addData(list);
    }
  }

  @Override
  public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    if (!hidden) {
      loadBookmark();
    }
  }

  private void deleteBookmark(BookBean bookBean){
    String json = ACache.get(getActivity()).getAsString(CacheConfigs.BOOK_MARK_LIST);
    if(TextUtils.isEmpty(json)){
    }else{
      Type type = new TypeToken<ArrayList<BookBean>>(){}.getType();
      ArrayList<BookBean> list = new Gson().fromJson(json, type);

      for(int i = 0, size = list.size(); i < size; i++){
        if(list.get(i).getBookUrl().equals(bookBean.getBookUrl())){
          list.remove(i);
          mAdapter.removeData(i);
          mAdapter.notifyDataSetChanged();
          break;
        }
      }

//      list.add(bookBean);
      ACache.get(getActivity()).put(CacheConfigs.BOOK_MARK_LIST, new Gson().toJson(list));
    }
  }
}
