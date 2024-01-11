package com.gucardev.apitestautomation.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class JsonUtil {

  public static String simplify(String json) {
    Gson gson = new GsonBuilder().create();

    JsonElement el = JsonParser.parseString(json);
    return gson.toJson(el);
  }

  public static String beautify(String json) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    JsonElement el = JsonParser.parseString(json);
    return gson.toJson(el);
  }
}
