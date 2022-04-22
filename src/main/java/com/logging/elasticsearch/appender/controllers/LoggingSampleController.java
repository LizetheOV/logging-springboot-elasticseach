package com.logging.elasticsearch.appender.controllers;

import com.logging.elasticsearch.appender.dto.request.TraceInput;
import com.logging.elasticsearch.appender.dto.response.GenericResponse;
import com.logging.elasticsearch.appender.services.LogbackAggregationService;
import com.logging.elasticsearch.appender.services.TransformService;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping(value = "logging/")
public class LoggingSampleController {

    @Autowired
    LogbackAggregationService logService;

    @Autowired
    TransformService transformService;

    @RequestMapping(value = "trace/{id}", method = RequestMethod.POST)
    public GenericResponse testLoggingTrace(@PathVariable String id, @RequestBody TraceInput input) {
        logService.startLogging(id, input, Arrays.asList("arg2", "arg3"));
        GenericResponse trans1 = transformService.transformTrace(input);
        logService.extraLogging(id, new ArrayList<>(), "Id");
        logService.endLogging("Trace Logging", trans1, Arrays.asList("content", "errors"));
        return trans1;
    }
}
