package com.don.easy2readyoedge.yoedgecomicinfo;

import dagger.Module;
import dagger.Provides;

/**
 * Created by DON on 17/03/02.
 */
@Module
public class YoedgeComicInfoModule {
  private YoedgeComicInfoContract.YoedgeComicInfoView view;
  public YoedgeComicInfoModule(YoedgeComicInfoContract.YoedgeComicInfoView view){this.view = view;}
  @Provides
  public YoedgeComicInfoContract.YoedgeComicInfoView providerView(){
    return view;
  }
}
