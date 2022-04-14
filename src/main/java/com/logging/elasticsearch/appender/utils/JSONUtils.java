package com.logging.elasticsearch.appender.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

public class JSONUtils {
    public static JSONObject getJSONObject(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "{}";
        try {
            jsonString = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new JSONObject(jsonString);
    }
}
