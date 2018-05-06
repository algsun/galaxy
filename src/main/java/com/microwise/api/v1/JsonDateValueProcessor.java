package com.microwise.api.v1;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xiedeng
 * @date 14-9-4
 */
public class JsonDateValueProcessor extends JsonSerializer<Date> implements JsonValueProcessor {

    private final String datePattern = "yyyy-MM-dd HH:mm:ss";

    @Override
    public void serialize(Date date, JsonGenerator gen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
        gen.writeString(sdf.format(date));
    }

    @Override
    public Object processArrayValue(Object o, JsonConfig jsonConfig) {
        return process(o);
    }

    @Override
    public Object processObjectValue(String s, Object o, JsonConfig jsonConfig) {
        return process(o);
    }

    private Object process(Object value) {
        if (value instanceof Date) {
            SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
            return sdf.format(value);
        }
        return value == null ? "" : value.toString();
    }
}
