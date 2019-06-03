package com.cwt.bpg.cbt.mongodb.mapper;

import java.time.Instant;
import java.util.Date;

import com.fasterxml.jackson.databind.module.SimpleModule;

public class CustomModule extends SimpleModule {

    private static final long serialVersionUID = 5081995315634411033L;

    public CustomModule() {
        addSerializer(Date.class, new DateToJsonSerializer());
        addSerializer(Instant.class, new BsonDateInstantSerializer());
        addDeserializer(Date.class, new BsonDateDeserializer());
        addDeserializer(Instant.class, new BsonDateInstantDeserializer());
    }
}
