package com.cwt.bpg.cbt.exchange.order.model;

import java.io.IOException;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class ObjectIdDeserializer extends JsonDeserializer<ObjectId> {
	@Override
	public ObjectId deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException {
		ObjectCodec oc = jp.getCodec();
		JsonNode node = oc.readTree(jp);
		return new ObjectId(node.textValue());
	}
}	