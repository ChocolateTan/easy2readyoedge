package com.don.easy2readyoedge;

import com.don.easy2readyoedge.core.self.SelfLog;
import com.facebook.drawee.backends.pipeline.Fresco;

import android.app.Application;

/**
 * Created by DON on 16/08/25.
 */
public class EZReadApplication extends Application {
  @Override
  public void onCreate() {
    super.onCreate();

//    SelfLog.close();
    Fresco.initialize(this);
  }
}
