package com.zbcn.demo.serializer;

import org.springframework.core.convert.converter.Converter;

import java.time.Instant;
import java.time.format.DateTimeParseException;

/**
 * TODO
 *
 * @author Administrator
 * @date 2018/12/19 11:22
 */
public enum  StringToInstantConverter implements Converter<String, Instant> {
    INSTANCE;

    @Override
    public Instant convert(String source) {
        try {
            return Instant.parse(source);
        } catch(DateTimeParseException ex) {
        }
        return null;
    }
}
