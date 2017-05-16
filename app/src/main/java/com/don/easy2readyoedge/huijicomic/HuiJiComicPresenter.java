package com.don.easy2readyoedge.huijicomic;

import android.content.Context;

import com.don.easy2readyoedge.apis.ApiService;
import com.don.easy2readyoedge.beans.BaseBean;
import com.don.easy2readyoedge.beans.BookBean;
import com.don.easy2readyoedge.configs.ApiConfigs;
import com.don.easy2readyoedge.configs.CacheConfigs;
import com.don.easy2readyoedge.core.self.SelfLog;
import com.don.easy2readyoedge.utils.ACache;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by don on 10/23/16.
 */

public class HuiJiComicPresenter implements HuiJiComicContract.Presenter {
  private static final String TAG = HuiJiComicPresenter.class.getSimpleName();
  private final HuiJiComicContract.ViewModel mView;
  @Inject Retrofit.Builder mRetrofitBuilder;
  @Inject ACache mACache;
  @Inject HuiJiComicPresenter(HuiJiComicContract.ViewModel view){
    this.mView = view;
  }

  @Override
  public void initControl() {
    mView.initControl();
  }

  @Override
  public void loadData() {
    String json = mACache.getAsString(CacheConfigs.CATEGORY_LIST);
    Type type = new TypeToken<List<BookBean>>() {
    }.getType();
    List<BookBean> list = new Gson().fromJson(json, type);

    if (list != null && list.size() > 0) {
      mView.setListData(list);
      mView.updateListStatus();
    }

    Retrofit retrofit = mRetrofitBuilder
      .baseUrl(ApiConfigs.API_BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
//      .addConverterFactory(ScalarsConverterFactory.create())
      .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
      .build();

    ApiService apiService = retrofit.create(ApiService.class);
    apiService.getCategory()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new Subscriber<BaseBean<ArrayList<BookBean>>>() {
        @Override
        public void onCompleted() {
          SelfLog.i(TAG, "Get Completed");
        }

        @Override
        public void onError(Throwable e) {
          e.printStackTrace();
        }

        @Override
        public void onNext(BaseBean<ArrayList<BookBean>> arrayListBaseBean) {
          if (mView != null) {
            if (arrayListBaseBean.getStatus() == 1) {
              mACache.put(CacheConfigs.CATEGORY_LIST, new Gson().toJson(arrayListBaseBean.getData()));
              mView.setListData(arrayListBaseBean.getData());
              mView.updateListStatus();
            }
          }
        }
      });
  }
  @Override
  public void onClickComicItem(BookBean bookBean) {
    mView.showBookDetail(bookBean);
  }
}
