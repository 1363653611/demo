package com.zbcn.demo.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

/**
 * Created by kingphang on 16/12/16.
 */
public class JodaDateTimeDeserializer extends StdDeserializer<DateTime> {

    private static final long serialVersionUID = -2200516268909850973L;

    public JodaDateTimeDeserializer() {
        super(DateTime.class);
    }

    @Override
    public DateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
        String s = jsonParser.getValueAsString();
        if (s != null) {
            return formatter.parseDateTime(s);
        }
        return null;
    }


}
