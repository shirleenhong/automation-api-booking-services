package com.cwt.bpg.cbt.tpromigration.mongodb.mapper;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class DateToJsonSerializer extends StdSerializer<Date>
{

    public DateToJsonSerializer()
    {
        super(Date.class);
    }

    @Override
    public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException
    {
        jgen.writeStartObject();
        jgen.writeNumberField("$date", value.getTime());
        jgen.writeEndObject();
    }

}
