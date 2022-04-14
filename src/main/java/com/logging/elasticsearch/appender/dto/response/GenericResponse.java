package com.logging.elasticsearch.appender.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class GenericResponse {
    private Object content;
    private Object errors;

    public GenericResponse(Object content, Object errors) {
        this.content = content;
        this.errors = errors;
    }

    public Object getContent() {
        return content;
    }

    public Object getErrors() {
        return errors;
    }

    public void setErrors(Object errors) {
        this.errors = errors;
    }

    public GenericResponse setContent(Object content) {
        if (content != null) {
            this.content = content;
        } else {
            Map data = new HashMap();
            this.content = data;
        }
        return this;
    }
}
