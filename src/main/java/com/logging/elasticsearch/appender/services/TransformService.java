package com.logging.elasticsearch.appender.services;

import com.logging.elasticsearch.appender.dto.request.TraceInput;
import com.logging.elasticsearch.appender.dto.response.GenericResponse;

public interface TransformService {
    GenericResponse transformTrace(TraceInput input);
}
