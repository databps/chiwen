package com.databps.bigdaf.admin.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class EchartsJsonUtil {

  public static JsonObject createEchartsJson(JsonArray categoryArray, JsonArray seriesArray) {
    JsonObject rootJson = new JsonObject();
    rootJson.add("categoryData", categoryArray);
    rootJson.add("seriesData", seriesArray);
    return rootJson;
  }
}
