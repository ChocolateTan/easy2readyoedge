package com.don.easy2readyoedge.core.self;

import com.facebook.drawee.view.SimpleDraweeView;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by DON on 16/08/22.
 */
public interface IFViewHolderSetData {
  TextView getTextView(int id);
  void setTextViewText(int id, CharSequence text);

  LinearLayout getLinearLayout(int id);
  RelativeLayout getRelativeLayout(int id);

  SimpleDraweeView getSimpleDraweeView(int id);
  Button getButton(int id);

  ImageView getImageView(int id);
}
