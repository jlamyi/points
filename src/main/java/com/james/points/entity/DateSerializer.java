package com.james.points.entity;

import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.core.JsonProcessingException;

public class DateSerializer extends StdSerializer<Date> {
    private SimpleDateFormat formatter
            = new SimpleDateFormat("MM/dd hha");

    public DateSerializer() {
        this(null);
    }

    public DateSerializer(Class clazz) {
        super(clazz);
    }

    @Override
    public void serialize (Date value, JsonGenerator gen, SerializerProvider arg2)
            throws IOException, JsonProcessingException {
        gen.writeString(formatter.format(value));
    }
}
