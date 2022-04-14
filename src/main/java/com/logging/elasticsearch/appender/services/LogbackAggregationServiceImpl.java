package com.logging.elasticsearch.appender.services;

import com.logging.elasticsearch.appender.utils.JSONUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class LogbackAggregationServiceImpl implements LogbackAggregationService {
    @Value("${app.name}")
    private String appName;

    private static final Logger LOGGER = LoggerFactory.getLogger(LogbackAggregationServiceImpl.class);

    public void startLogging(String identifier, Object input, List<String> fromInput) {
        MDC.put("appName", appName);
        MDC.put("code", identifier);
        MDC.put("startTime", LocalDateTime.now().toString());
        setLogger(fromInput, input, "input");
    }

    public void extraLogging(Object extra, List<String> fromExtra, String identifier) {
        setLogger(fromExtra, extra, identifier);
    }

    public void endLogging(String message, Object output, List<String> fromOutput) {
        MDC.put("endTime", LocalDateTime.now().toString());
        setLogger(fromOutput, output, "output");
        LOGGER.trace(message);
        MDC.clear();
    }

    private void setLogger(List<String> keys, Object object, String identifier) {
        String className = object.getClass().getCanonicalName();
        MDC.put("className", className);
        try {
            if (className.contains("String")) {
                MDC.put(identifier, object.toString());
            } else if (className.contains("List")) {
                List objects = new ArrayList<>((Collection<?>) object);
                List list = new ArrayList();
                for (Object obj : objects) {
                    list.add(JSONUtils.getJSONObject(obj));
                }
                MDC.put(identifier, list.toString());
            } else {
                JSONObject jsonObject = JSONUtils.getJSONObject(object);
                MDC.put(identifier, jsonObject.toString());
                for (String key : keys) {
                    MDC.put(key, jsonObject.get(key).toString());
                }
            }
        } catch (Exception e) {
            MDC.put("relatedObject", object.toString());
            LOGGER.error(e.getMessage(), e);
        }

    }
}