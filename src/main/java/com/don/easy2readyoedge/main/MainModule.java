package com.don.easy2readyoedge.main;

import dagger.Module;
import dagger.Provides;

/**
 * Created by DON on 17/02/22.
 */
@Module
public class MainModule {
  private MainActivityContract.ViewModel view;
  //构造方法传递View 接口的实例化对象
  public MainModule(MainActivityContract.ViewModel view){
    this.view = view;
  }
  //在容器中提供View接口的实例化对象
  @Provides
  public MainActivityContract.ViewModel providerView(){
    return view;
  }
}
