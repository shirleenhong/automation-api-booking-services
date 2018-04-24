package com.cwt.bpg.cbt.mongodb.config.mapper;

import java.util.Date;

import com.fasterxml.jackson.databind.module.SimpleModule;

public class CustomModule extends SimpleModule
{

    private static final long serialVersionUID = 1L;

    public CustomModule()
    {
        addSerializer(Date.class, new DateToJsonSerializer());
        addDeserializer(Date.class, new BsonDateDeserializer());
    }
}
