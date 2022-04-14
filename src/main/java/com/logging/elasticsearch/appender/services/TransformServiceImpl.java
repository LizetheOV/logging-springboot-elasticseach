package com.logging.elasticsearch.appender.services;

import com.logging.elasticsearch.appender.dto.request.TraceInput;
import com.logging.elasticsearch.appender.dto.response.GenericResponse;
import com.logging.elasticsearch.appender.utils.JSONUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TransformServiceImpl implements TransformService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogbackAggregationServiceImpl.class);

    @Override
    public GenericResponse transformTrace(TraceInput input) {
        GenericResponse response = new GenericResponse();
        try {
            Map<String, String> content = new HashMap();
            content.put("Arg1LowerCase", input.getArg1().toLowerCase());
            content.put("concatArg2Arg3", input.getArg2()+input.getArg3());
            response.setContent(content);
        } catch (Exception e) {
            Map<String, String> errors = new HashMap();
            errors.put("message", e.getMessage());
            if (e.getMessage() == null) {
                MDC.put("relatedObject", JSONUtils.getJSONObject(input).toString());
                LOGGER.error("Unexpected error on transformTrace", e);
            }
            response.setErrors(errors);
        }
        return response;
    }
}
