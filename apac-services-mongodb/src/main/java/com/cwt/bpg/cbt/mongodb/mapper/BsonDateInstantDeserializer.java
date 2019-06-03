package com.cwt.bpg.cbt.mongodb.mapper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class BsonDateInstantDeserializer extends JsonDeserializer<Instant> {

    private final DateTimeFormatter dateFormatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .withZone(ZoneId.of("UTC"));

    @Override
    public Instant deserialize(JsonParser parser, DeserializationContext context)
            throws IOException {

        String dateStr = parser.getText();

        return dateStr != null
                ? Instant.from(dateFormatter.parse(dateStr))
                : null;
    }
}