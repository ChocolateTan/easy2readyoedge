package com.don.easy2readyoedge.yoedgecomicinfo;

import com.don.easy2readyoedge.ApplicationModule;
import com.don.easy2readyoedge.NetModule;
import com.don.easy2readyoedge.StorageDataModule;

import com.don.easy2readyoedge.yoedgecomicinfo.YoedgeComicInfoContract.ViewModule;
import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by DON on 17/03/02.
 */
@Component(modules = {YoedgeComicInfoModule.class, ApplicationModule.class, StorageDataModule.class, NetModule.class})
@Singleton
public interface YoedgeComicInfoComponect {
  void inject(ViewModule view);
}
