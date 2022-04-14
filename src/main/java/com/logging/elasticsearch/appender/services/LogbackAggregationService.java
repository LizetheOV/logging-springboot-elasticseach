package com.logging.elasticsearch.appender.services;

import java.util.List;

public interface LogbackAggregationService {
    void startLogging(String identifier, Object input, List<String> fromInput);
    void extraLogging(Object extra, List<String> fromExtra, String identifier);
    void endLogging(String message, Object output, List<String> fromOutput);
}
