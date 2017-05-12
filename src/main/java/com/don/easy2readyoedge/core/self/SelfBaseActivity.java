package com.don.easy2readyoedge.core.self;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by DON on 16/08/19.
 */
public abstract class SelfBaseActivity extends AppCompatActivity implements IFFindView {
  public Activity mActivity;
  public Context mContext;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mActivity = this;
    mContext = this;
  }

  @Override
  public void setContentView(@LayoutRes int layoutResID) {
    super.setContentView(layoutResID);
    afterCreateView();
    load();
  }

  public abstract void afterCreateView();

  public abstract void load();

  @Override
  public <T extends View> T find(int id) {
    return (T) findViewById(id);
  }
}
