package com.don.easy2readyoedge;

import com.don.easy2readyoedge.configs.ApiConfigs;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by DON on 17/03/02.
 */
@Module
public class NetModule {
  @Provides
  @Singleton
  Retrofit.Builder providesRetrofit() {
    return new Retrofit.Builder();
  }
}
