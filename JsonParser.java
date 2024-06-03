package com.fedor;

import org.json.*;

public class JsonParser {
    public static String jmain(String args) {
        String jsonStr = args;

        JSONObject jsonObject = new JSONObject(jsonStr);

        JSONObject response = jsonObject.getJSONObject("response");
        boolean result = response.getBoolean("result");
        JSONArray errorsArray = response.getJSONArray("errors");

        JSONObject firstError = errorsArray.getJSONObject(0);
        String id = firstError.getString("id");
        int offset = firstError.getInt("offset");
        int length = firstError.getInt("length");
        JSONObject description = firstError.getJSONObject("description");
        String descriptionEn = description.getString("en");
        String bad = firstError.getString("bad");
        JSONArray betterArray = firstError.getJSONArray("better");
        String type = firstError.getString("type");
        String parsedData = "В вашем тексте есть ошибки \nОписание: " + descriptionEn + "\n" + "Плохо: " + bad + "\n" + "Лучше: ";
        for (int i = 0; i < betterArray.length(); i++) {
            parsedData += betterArray.getString(i) + " ";
        }

        return parsedData;
    }
}