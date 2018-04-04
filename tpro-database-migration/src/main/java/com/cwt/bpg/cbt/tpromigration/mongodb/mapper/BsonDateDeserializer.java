package com.cwt.bpg.cbt.tpromigration.mongodb.mapper;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class BsonDateDeserializer extends StdDeserializer<Date>
{

//    private static final Logger LOGGER = LoggerFactory.getLogger(BsonDateDeserializer.class);
    private static final long serialVersionUID = 1L;

    public BsonDateDeserializer()
    {
        super(Date.class);
    }

    @Override
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException
    {
        Date date = null;
        if (jp.getCurrentToken() == JsonToken.VALUE_STRING)
        {
            date = parseStringValue(jp.getText());
        }
        else
        {
            ObjectCodec oc = jp.getCodec();
            JsonNode node = oc.readTree(jp);
            date = parseJsonNode(node.get("$date"));
        }
        return date;
    }

    private Date parseStringValue(final String str)
    {

        if (StringUtils.isNotBlank(str))
        {
            try
            {
                return Date.from(Instant.parse(str.trim()));
            }
            catch (Exception e)
            {
//                LOGGER.error("Incorrect datetime format. {}", e);
            }
        }

        return null;
    }

    private Date parseJsonNode(JsonNode node)
    {

        if (node.isLong())
        {
            return new Date(node.asLong());
        }
        return parseStringValue(node.asText());
    }
}
