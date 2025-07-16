package com.example.pk_pl.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class UtcLocalDateTimeSerializer extends StdSerializer<LocalDateTime> {

    public UtcLocalDateTimeSerializer() {
        super(LocalDateTime.class);
    }

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        String isoUtc = value.atOffset(ZoneOffset.UTC).toInstant().toString(); // ISO with Z
        gen.writeString(isoUtc);
    }
}