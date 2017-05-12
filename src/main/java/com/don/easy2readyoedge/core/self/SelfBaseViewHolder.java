package com.don.easy2readyoedge.core.self;

import com.facebook.drawee.view.SimpleDraweeView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by DON on 16/08/22.
 */
public class SelfBaseViewHolder extends RecyclerView.ViewHolder implements IFFindView, IFViewHolderSetData {
  public SelfBaseViewHolder(View itemView) {
    super(itemView);
  }

  @Override
  public <T extends View> T find(int id) {
    return (T) itemView.findViewById(id);
  }

  @Override
  public void setTextViewText(int id, CharSequence text) {
    getTextView(id).setText(text);
  }

  @Override
  public LinearLayout getLinearLayout(int id) {
    return (LinearLayout)find(id);
  }

  @Override
  public RelativeLayout getRelativeLayout(int id) {
    return (RelativeLayout)find(id);
  }

  @Override
  public SimpleDraweeView getSimpleDraweeView(int id) {
    return (SimpleDraweeView)find(id);
  }

  @Override
  public Button getButton(int id) {
    return (Button)find(id);
  }

  @Override
  public ImageView getImageView(int id) {
    return find(id);
  }

  @Override
  public TextView getTextView(int id) {
    return (TextView)find(id);
  }

}
