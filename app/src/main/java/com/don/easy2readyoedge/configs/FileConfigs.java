package com.don.easy2readyoedge.configs;

import android.os.Environment;

/**
 * Created by DON on 16/08/23.
 */
public class FileConfigs {
  public static final String ROOT_PATH = Environment.getExternalStorageDirectory().getPath();
  public static final String API_FILE_PATH = ROOT_PATH + "/HuiJi/api_file/";
  public static final String API_CATEGORY_TXT = "api_category.txt";
}
