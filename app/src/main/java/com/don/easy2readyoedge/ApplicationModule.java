package com.don.easy2readyoedge;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by DON on 17/02/22.
 */

@Module
public class ApplicationModule {

  private Application mApplication;

  public ApplicationModule(Application application) {
    mApplication = application;
  }

  @Provides
  @Singleton
  Application providesApplication() {
    return mApplication;
  }
}
