package com.don.easy2readyoedge.adapters;

import com.don.easy2readyoedge.R;
import com.don.easy2readyoedge.beans.ArticleBean;
import com.don.easy2readyoedge.core.self.IFAdapter;
import com.don.easy2readyoedge.core.self.SelfBaseAdapter;
import com.don.easy2readyoedge.core.self.SelfBaseViewHolder;

import android.view.View;

import java.util.List;

/**
 * Created by DON on 16/08/22.
 */
public class BookActivityAdapter extends SelfBaseAdapter {
  public interface BookAdapterListener{
    void onClickItem(ArticleBean articleBean);
  }
  public BookActivityAdapter(List<ArticleBean> data, final BookAdapterListener listener) {
    super(R.layout.item_article, data, new IFAdapter<ArticleBean>() {
      @Override
      public void onSelfBindViewHolder(SelfBaseViewHolder holder, final List<ArticleBean> data, final int position) {
        final ArticleBean item = data.get(position);
        if(!item.getIsRead()) {
          holder.getImageView(R.id.iv_read).setVisibility(View.VISIBLE);
        }else{
          holder.getImageView(R.id.iv_read).setVisibility(View.GONE);
        }
        holder.setTextViewText(R.id.tv_no, item.getArticleName());
        holder.getRelativeLayout(R.id.rly_item).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            listener.onClickItem(item);
          }
        });
      }
    });
  }
}
