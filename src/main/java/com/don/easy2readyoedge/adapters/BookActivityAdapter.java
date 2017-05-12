package com.don.easy2readyoedge.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.don.easy2readyoedge.R;
import com.don.easy2readyoedge.beans.ArticleBean;
import com.don.easy2readyoedge.comicinfo.ComicInfoPresenter;

import android.view.View;

import com.orz.orzframework.mvp.MvpAdapter;
import com.orz.orzframework.mvp.MvpViewHolder;

/**
 * Created by DON on 16/08/22.
 */
public class BookActivityAdapter extends MvpAdapter<ArticleBean> {

  private final ComicInfoPresenter mPresenter;

  public BookActivityAdapter(ComicInfoPresenter presenter){
    mPresenter = presenter;
  }

  @Override
  public MvpViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    return new MvpViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_article, parent, false));
  }

  @Override
  public void onBindViewHolder(MvpViewHolder holder, int position) {
    ArticleBean item = getItem(position);
    if(!item.getIsRead()) {
      holder.getImageView(R.id.iv_read).setVisibility(View.VISIBLE);
    }else{
      holder.getImageView(R.id.iv_read).setVisibility(View.GONE);
    }
    holder.setTextViewText(R.id.tv_no, item.getArticleName());
//    holder.getRelativeLayout(R.id.rly_item).setOnClickListener(view ->
//        mPresenter.onClickArticle(item));
  }
}
