package com.don.easy2readyoedge.yoedgecomicinfo;

import com.don.easy2readyoedge.ApplicationModule;
import com.don.easy2readyoedge.NetModule;
import com.don.easy2readyoedge.StorageDataModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;

/**
 * Created by DON on 17/03/02.
 */
@Component(modules = {YoedgeComicInfoModule.class, ApplicationModule.class, StorageDataModule.class, NetModule.class})
@Singleton
public interface YoedgeComicInfoComponect {
  void inject(YoedgeComicInfoContract.YoedgeComicInfoView view);
}
