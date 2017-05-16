package com.don.easy2readyoedge.beans;

import java.util.List;

/**
 * Created by DON on 16/08/30.
 */
public class VersionBean {

  /**
   * lastForceUpdate : 0
   * versionCode : 1
   * versionName : beta-0.0.9
   * updateInfo : [{"info":""}]
   */

  private int lastForceUpdate;
  private int versionCode;
  private String versionName;
  private String updateUrl;
  /**
   * info :
   */

  private List<UpdateInfoBean> updateInfo;

  public int getLastForceUpdate() {
    return lastForceUpdate;
  }

  public void setLastForceUpdate(int lastForceUpdate) {
    this.lastForceUpdate = lastForceUpdate;
  }

  public int getVersionCode() {
    return versionCode;
  }

  public void setVersionCode(int versionCode) {
    this.versionCode = versionCode;
  }

  public String getVersionName() {
    return versionName;
  }

  public void setVersionName(String versionName) {
    this.versionName = versionName;
  }

  public List<UpdateInfoBean> getUpdateInfo() {
    return updateInfo;
  }

  public void setUpdateInfo(List<UpdateInfoBean> updateInfo) {
    this.updateInfo = updateInfo;
  }

  public static class UpdateInfoBean {
    private String info;

    public String getInfo() {
      return info;
    }

    public void setInfo(String info) {
      this.info = info;
    }
  }

  public String getUpdateUrl() {
    return updateUrl;
  }

  public void setUpdateUrl(String updateUrl) {
    this.updateUrl = updateUrl;
  }


  public String getAllInfo(){
    if(getUpdateInfo() != null && getUpdateInfo().size() > 0){
      StringBuffer sb = new StringBuffer();
      for(int i = 0, size = getUpdateInfo().size(); i < size; i++){
        sb.append(getUpdateInfo().get(i).getInfo());
      }
      return sb.toString();
    }else{
      return "版本更新";
    }
  }
}
