package com.don.easy2readyoedge.core.self;

import android.view.View;

/**
 * Created by DON on 16/08/22.
 */
public interface IFFindView {
  public <T extends View> T find(int id);
}
