package com.don.easy2readyoedge.huijicomic;

import com.don.easy2readyoedge.ApplicationModule;
import com.don.easy2readyoedge.NetModule;
import com.don.easy2readyoedge.StorageDataModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by DON on 17/02/23.
 */
//@Component(modules = {HuiJiComicPresenterModule.class, ApplicationModule.class})
@Component(modules = {HuiJiComicModule.class, ApplicationModule.class, StorageDataModule.class, NetModule.class})
@Singleton
public interface HuiJiComicComponent {
  void inject(HuiJiComicFragment fragment);
}
