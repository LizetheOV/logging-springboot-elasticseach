package com.logging.elasticsearch.appender.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TraceInput {
    private String arg1;
    private String arg2;
    private String arg3;

    public TraceInput(String arg1, String arg2, String arg3) {
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.arg3 = arg3;
    }
}
