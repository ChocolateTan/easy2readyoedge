package com.don.easy2readyoedge;

import com.don.easy2readyoedge.utils.ACache;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by DON on 17/02/23.
 */
@Module
public class StorageDataModule {
  //  private Application application;
  public StorageDataModule() {
  }

  // Dagger will only look for methods annotated with @Provides
  @Provides
  @Singleton
  // Application reference must come from AppModule.class
  SharedPreferences providesSharedPreferences(Application application) {
    return PreferenceManager.getDefaultSharedPreferences(application);
  }

  // Dagger will only look for methods annotated with @Provides
  @Provides
  @Singleton
  // Application reference must come from AppModule.class
  ACache providesACache(Application application) {
    return ACache.get(application);
  }
}
