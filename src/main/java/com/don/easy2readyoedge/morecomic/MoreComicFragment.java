package com.don.easy2readyoedge.morecomic;

import com.don.easy2readyoedge.ApplicationModule;
import com.don.easy2readyoedge.NetModule;
import com.don.easy2readyoedge.R;
import com.don.easy2readyoedge.StorageDataModule;
import com.don.easy2readyoedge.comicinfo.ComicInfoActivity;
import com.don.easy2readyoedge.core.self.SelfLog;
import com.don.easy2readyoedge.yoedgecomicinfo.YoedgeComicInfoActivity;
import com.don.easy2readyoedge.adapters.BookAdapter;
import com.don.easy2readyoedge.beans.BookBean;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static dagger.internal.Preconditions.checkNotNull;

public class MoreComicFragment extends Fragment
  implements MoreComicContract.ViewModel {
  private static final String TAG = YoedgeComicInfoActivity.class.getSimpleName();
  private OnFragmentInteractionListener mListener;
  private BookAdapter mAdapter;
  @Inject MoreComicPresenter mPresenter;
  private EditText editSearch;

  public static MoreComicFragment newInstance() {
    MoreComicFragment fragment = new MoreComicFragment();
    return fragment;
  }

  public MoreComicFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_more_comic, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    DaggerMoreComicComponent.builder()
      .applicationModule(new ApplicationModule(getActivity().getApplication()))
      .storageDataModule(new StorageDataModule())
      .netModule(new NetModule())
      .moreComicModule(new MoreComicModule(this))
      .build().inject(this);

    mPresenter.initControl();
    mPresenter.loadComic();
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

  public BookAdapter.BookAdapterListener getListAdapter() {
    return bookBean -> mPresenter.showComicDetail(bookBean);
  }

  @Override
  public void initControl() {

    RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);
    editSearch = (EditText) getView().findViewById(R.id.edt_search);
    editSearch.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (!TextUtils.isEmpty(charSequence)) {
          mPresenter.searchComic(charSequence.toString());
        } else {
          mPresenter.allComic();
        }
      }

      @Override
      public void afterTextChanged(Editable editable) {

      }
    });
    mAdapter = new BookAdapter(new ArrayList<BookBean>(), getListAdapter());
    GridLayoutManager manager = new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false);
    recyclerView.setLayoutManager(manager);
    recyclerView.setAdapter(mAdapter);
  }

  @Override
  public void showComicDetail(BookBean bookBean) {
    ComicInfoActivity.launchActivity(getActivity(), bookBean);
  }

  @Override
  public void addDataToList(List<BookBean> arrayList) {
    SelfLog.i(TAG, "arrayList=" + arrayList.size());
    mAdapter.addData(arrayList);
  }

  @Override
  public void showSnackbarInfo(String text) {
    Snackbar snackbar = Snackbar.make(getView(), text, Snackbar.LENGTH_LONG);
    View view = snackbar.getView();//获取Snackbar的view
    if (view != null) {
      view.setBackgroundColor(Color.parseColor("#000000"));//修改view的背景色
      ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(Color.parseColor("#FFFFFF"));//获取Snackbar的message控件，修改字体颜色
    }
    snackbar.show();
  }

  @Override
  public void showSnackbarInfo(int textRes) {
    Snackbar snackbar = Snackbar.make(getView(), textRes, Snackbar.LENGTH_LONG);
    View view = snackbar.getView();//获取Snackbar的view
    if (view != null) {
      view.setBackgroundColor(Color.parseColor("#000000"));//修改view的背景色
      ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(Color.parseColor("#FFFFFF"));//获取Snackbar的message控件，修改字体颜色
    }
    snackbar.show();
  }

  @Override
  public void clearData() {
    mAdapter.clearData();
  }

  @Override
  public void notifyList() {
    mAdapter.notifyDataSetChanged();
  }

//  @Override
//  public void setPresenter(MoreComicContract.Presenter presenter) {
//    mPresenter = checkNotNull(presenter);
//  }


  public interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
//    void onNavHomeClick();
  }
}
