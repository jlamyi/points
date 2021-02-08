package com.james.points.entity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateHandler extends StdDeserializer<Date> {
    public DateHandler() {
        this(null);
    }
    public DateHandler(Class<?> clazz) {
        super(clazz);
    }
    @Override
    public Date deserialize(JsonParser jsonparser, DeserializationContext context) throws IOException {
        String date = jsonparser.getText();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd hha");
            return sdf.parse(date);
        } catch (Exception e) {
            return null;
        }
    }
}
