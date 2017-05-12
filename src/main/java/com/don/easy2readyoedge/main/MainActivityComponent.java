package com.don.easy2readyoedge.main;

import dagger.Component;

/**
 * Created by DON on 17/02/22.
 */
@Component(modules = MainModule.class)
public interface MainActivityComponent {
  void inject(MainActivity activity);
}
