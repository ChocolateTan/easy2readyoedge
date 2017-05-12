package com.don.easy2readyoedge.bookmark;

import com.don.easy2readyoedge.R;
import com.don.easy2readyoedge.beans.ArticleBean;
import com.don.easy2readyoedge.beans.BookBean;
import com.don.easy2readyoedge.core.self.IFAdapter;
import com.don.easy2readyoedge.core.self.SelfBaseAdapter;
import com.don.easy2readyoedge.core.self.SelfBaseViewHolder;
import com.don.easy2readyoedge.data.source.local.BookInfoLocalDataSource;
import com.don.easy2readyoedge.utils.ACache;

import android.text.Html;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DON on 16/08/30.
 */
public class BookmarkAdapter extends SelfBaseAdapter<BookBean> {
  public interface BookmarkAdapterListener {
    void onClickItem(BookBean bookBean);

    void onClickDelete(BookBean bookBean);
  }

  public BookmarkAdapter(final ACache aCache, ArrayList<BookBean> data, final BookmarkAdapterListener listener) {
    super(R.layout.item_bookmark, data, new IFAdapter<BookBean>() {
      @Override
      public void onSelfBindViewHolder(SelfBaseViewHolder holder, final List<BookBean> data, final int position) {
        holder.setTextViewText(R.id.tv_title, Html.fromHtml(data.get(position).getBookName()));
        BookBean bookBean = BookInfoLocalDataSource.getInstance().getBookInfo(aCache, data.get(position).getBookUrl());
        if (null != bookBean && null != bookBean.getArticles() && bookBean.getArticles().size() > 0) {
          String newText = bookBean.getArticles().get(0).getArticleName();
          holder.setTextViewText(R.id.tv_new, newText);

          if (bookBean.getArticles().get(0).getIsRead()) {
            holder.getImageView(R.id.iv_read).setVisibility(View.INVISIBLE);
          } else {
            holder.getImageView(R.id.iv_read).setVisibility(View.VISIBLE);
          }

        } else {
          holder.getTextView(R.id.tv_new).setVisibility(View.INVISIBLE);
          holder.getImageView(R.id.iv_read).setVisibility(View.INVISIBLE);
        }

        holder.getRelativeLayout(R.id.rly_item).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            listener.onClickItem(data.get(position));
          }
        });
        holder.getButton(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            listener.onClickDelete(data.get(position));
          }
        });
      }
    });
  }

  public synchronized void updateDataNotify(BookBean data) {
    if (null != getData() && getData().size() > 0) {
      List<BookBean> list = getData();
      for (int i = 0, size = list.size(); i < size; i++) {
        if (getData().get(i).getBookUrl().equals(data.getBookUrl())) {
          super.updateData(i, data);
          notifyDataSetChanged();
          break;
        }
      }
    }
  }
}
