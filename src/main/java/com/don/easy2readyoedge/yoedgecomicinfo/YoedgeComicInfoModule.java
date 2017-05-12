package com.don.easy2readyoedge.yoedgecomicinfo;

import com.don.easy2readyoedge.yoedgecomicinfo.YoedgeComicInfoContract.ViewModule;
import dagger.Module;
import dagger.Provides;

/**
 * Created by DON on 17/03/02.
 */
@Module
public class YoedgeComicInfoModule {
  private ViewModule view;
  public YoedgeComicInfoModule(ViewModule view){this.view = view;}
  @Provides
  public ViewModule providerView(){
    return view;
  }
}
