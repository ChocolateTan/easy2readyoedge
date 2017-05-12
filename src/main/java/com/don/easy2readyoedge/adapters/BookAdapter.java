package com.don.easy2readyoedge.adapters;

import com.don.easy2readyoedge.R;
import com.don.easy2readyoedge.beans.BookBean;
import com.don.easy2readyoedge.core.self.IFAdapter;
import com.don.easy2readyoedge.core.self.SelfBaseAdapter;
import com.don.easy2readyoedge.core.self.SelfBaseViewHolder;

import android.text.Html;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DON on 16/08/22.
 */
public class BookAdapter extends SelfBaseAdapter {
  public interface BookAdapterListener {
    void onClickItem(BookBean bookBean);
  }

  public BookAdapter(ArrayList<BookBean> data, final BookAdapterListener listener) {
    super(R.layout.item_category, data, new IFAdapter<BookBean>() {
      @Override
      public void onSelfBindViewHolder(SelfBaseViewHolder holder, final List<BookBean> data, final int position) {
        holder.setTextViewText(R.id.tv_title, Html.fromHtml(data.get(position).getBookName()));
        holder.getRelativeLayout(R.id.rly_item).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            if (null != listener) {
              listener.onClickItem(data.get(position));
            }
          }
        });
      }
    });
  }
}
