package com.don.easy2readyoedge.comicinfo;

import com.don.easy2readyoedge.appwebview.AppWebViewActivity;
import com.don.easy2readyoedge.utils.ACache;
import com.google.gson.Gson;

import com.don.easy2readyoedge.R;
import com.don.easy2readyoedge.adapters.BookActivityAdapter;
import com.don.easy2readyoedge.beans.ArticleBean;
import com.don.easy2readyoedge.beans.BookBean;
import com.don.easy2readyoedge.configs.ExtraConfigs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ComicInfoActivity extends AppCompatActivity
    implements ComicInfoContract.ViewModel {

  private static final String TAG = ComicInfoActivity.class.getSimpleName();
  private BookActivityAdapter mAdapter;
  private BookBean mBean;
  private TextView tvTitle;
  private ComicInfoPresenter mPresenter;

  public static void launchActivity(Context context, BookBean bookBean) {
    Intent intent = new Intent(context, ComicInfoActivity.class);
    intent.putExtra(ExtraConfigs.EX_BOOK_BEAN, new Gson().toJson(bookBean));
    context.startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_book);

    if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras()
        .containsKey(ExtraConfigs.EX_BOOK_BEAN)) {
      String json = getIntent().getExtras().getString(ExtraConfigs.EX_BOOK_BEAN);
      mBean = new Gson().fromJson(json, BookBean.class);
    } else {
      finish();
    }
    mPresenter = new ComicInfoPresenter(this);
    mPresenter.initControl();
  }

  @Override
  protected void onResume() {
    super.onResume();

    mPresenter.getLocalBookInfo(ACache.get(this), mBean.getBookUrl());
    mPresenter.getRemoteBookInfo(ACache.get(this), mBean.getBookUrl());
  }

  //  private BookActivityAdapter.BookAdapterListener getBookAdapter() {
  //    return new BookActivityAdapter.BookAdapterListener() {
  //      @Override
  //      public void onClickItem(ArticleBean articleBean) {
  ////        ComicActivity.launchActivity(ComicInfoActivity.this, articleBean);
  ////        AppWebViewActivity.launchActivity(ComicInfoActivity.this, articleBean.getArticleUrl());
  //        mPresenter.onClickArticle(ComicInfoActivity.this,
  //          ACache.get(ComicInfoActivity.this),
  //          mBean.getBookUrl(),
  //          articleBean.getArticleUrl());
  //      }
  //    };
  //  }

  @Override
  public void findControl() {
    tvTitle = (TextView) findViewById(R.id.tv_title);

    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    mAdapter = new BookActivityAdapter(mPresenter);
    GridLayoutManager manager = new GridLayoutManager(this, 5, LinearLayoutManager.VERTICAL, false);
    recyclerView.setLayoutManager(manager);
    recyclerView.setAdapter(mAdapter);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(view -> {
      if (!TextUtils.isEmpty(mBean.getBookName()) && !TextUtils.isEmpty(mBean.getBookUrl())) {
        mPresenter.addBook(ACache.get(ComicInfoActivity.this), view, mBean.getBookName(),
            mBean.getBookUrl());
      }
    });
  }

  @Override
  public void setTitle(String text) {
    if (!TextUtils.isEmpty(text)) {
      tvTitle.setText(text);
    }
  }

  @Override
  public void addDataToList(List<ArticleBean> list) {
    if (null != list && 0 < list.size()) {
      mAdapter.addItems(list);
      mAdapter.notifyDataSetChanged();
    }
  }

  @Override
  public void clearList() {
    mAdapter.clearItems();
    mAdapter.notifyDataSetChanged();
  }

  @Override
  public void showComicCover() {

  }

  @Override
  public void showSnackbar(android.view.View view, String text) {
    Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
    if (snackbar.getView() != null) {
      snackbar.getView().setBackgroundColor(Color.parseColor("#FFFFFF"));//修改view的背景色
      ((TextView) snackbar.getView().findViewById(R.id.snackbar_text))
          .setTextColor(Color.parseColor("#000000"));//获取Snackbar的message控件，修改字体颜色
    }
    snackbar.show();
  }

  @Override
  public void showComic(Activity activity, String url) {
    AppWebViewActivity.launchActivity(activity, url);
  }
}
