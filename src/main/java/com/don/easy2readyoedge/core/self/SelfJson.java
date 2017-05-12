package com.don.easy2readyoedge.core.self;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by don on 12/5/16.
 */

public class SelfJson {
  private SelfJson() {
  }

  public static <T> T fromJson(String json, Class<T> clazz) throws JsonSyntaxException {
    return (new Gson()).fromJson(json, clazz);
  }

  public static <T> T fromJson(String json, Type type) throws JsonSyntaxException {
    return (new Gson()).fromJson(json, type);
  }

  public static String toJson(Object src) {
    return (new Gson()).toJson(src);
  }

  public static boolean isJson(String json) {
    if(TextUtils.isEmpty(json)) {
      return false;
    } else {
      try {
        new JSONObject(json);
        return true;
      } catch (Exception var2) {
        return false;
      }
    }
  }
}
