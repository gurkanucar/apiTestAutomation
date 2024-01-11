package com.gucardev.apitestautomation.service;

import com.google.gson.*;

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

  public static String extractField(String jsonString, String fieldPath) {
    if (jsonString == null || fieldPath == null || fieldPath.isEmpty()) {
      return null;
    }

    Gson gson = new Gson();
    JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

    String[] fieldParts = fieldPath.split("\\.");
    JsonObject currentObject = jsonObject;
    String fieldValue = null;

    for (String part : fieldParts) {
      if (currentObject.has(part)) {
        if (part.equals(fieldParts[fieldParts.length - 1])) {
          fieldValue = currentObject.get(part).getAsString();
          break;
        } else {
          currentObject = currentObject.getAsJsonObject(part);
        }
      } else {
        return null;
      }
    }

    return fieldValue;
  }
}
