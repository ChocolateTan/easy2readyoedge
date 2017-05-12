package com.don.easy2readyoedge;

import android.app.Application;
import com.facebook.drawee.backends.pipeline.Fresco;

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
