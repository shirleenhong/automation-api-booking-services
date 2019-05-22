package com.cwt.bpg.cbt.mongodb.mapper;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class DateToJsonSerializer extends StdSerializer<Date>
{
	private static final long serialVersionUID = 2934854050852316123L;

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
