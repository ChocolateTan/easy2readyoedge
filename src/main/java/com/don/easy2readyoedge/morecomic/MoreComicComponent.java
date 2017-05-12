package com.don.easy2readyoedge.morecomic;

import com.don.easy2readyoedge.ApplicationModule;
import com.don.easy2readyoedge.NetModule;
import com.don.easy2readyoedge.StorageDataModule;
import com.don.easy2readyoedge.huijicomic.HuiJiComicModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by DON on 17/03/02.
 */
@Component(modules = {MoreComicModule.class, ApplicationModule.class, StorageDataModule.class, NetModule.class})
@Singleton
public interface MoreComicComponent {
  void inject(MoreComicFragment moreComicFragment);
}
