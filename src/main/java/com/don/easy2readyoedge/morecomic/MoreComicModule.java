package com.don.easy2readyoedge.morecomic;

import dagger.Module;
import dagger.Provides;

/**
 * Created by DON on 17/03/02.
 */
@Module
public class MoreComicModule {
  private MoreComicContract.ViewModel view;
  public MoreComicModule(MoreComicContract.ViewModel viewModel){this.view = viewModel;}
  @Provides
  public MoreComicContract.ViewModel providerView(){
    return view;
  }
}
