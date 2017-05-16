package com.don.easy2readyoedge.huijicomic;

import dagger.Module;
import dagger.Provides;

/**
 * Created by DON on 17/02/23.
 */
@Module
public class HuiJiComicModule {
  private HuiJiComicContract.ViewModel view;
  //构造方法传递View 接口的实例化对象
  public HuiJiComicModule(HuiJiComicContract.ViewModel view){this.view = view;}
  //在容器中提供View接口的实例化对象
  @Provides
  public HuiJiComicContract.ViewModel providerView(){
    return view;
  }
}
