package com.don.easy2readyoedge.apis;

import com.don.easy2readyoedge.beans.BaseBean;
import com.don.easy2readyoedge.beans.BookBean;
import com.don.easy2readyoedge.beans.VersionBean;
import com.don.easy2readyoedge.configs.ApiConfigs;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by DON on 16/08/23.
 */
public interface ApiService {
  @GET(ApiConfigs.API_CATEGORY_URL_PATH)
  Observable<BaseBean<ArrayList<BookBean>>> getCategory();

  @GET
  Observable<ResponseBody> getArticle(@Url String url);

  @GET(ApiConfigs.API_RI_MAN_HUI_URL_PATH)
  Observable<ResponseBody> getRiManHui();

  @GET(ApiConfigs.API_VERSION)
  Observable<BaseBean<VersionBean>> getVersion();

  @GET(ApiConfigs.API_RE_XUE)
  Observable<ResponseBody> getHot();
  @GET(ApiConfigs.API_SHAO_NV)
  Observable<ResponseBody> getLady();
  @GET(ApiConfigs.API_FU_XIANG)
  Observable<ResponseBody> getBL();
  @GET(ApiConfigs.API_KONG_BU)
  Observable<ResponseBody> getScarm();
}
