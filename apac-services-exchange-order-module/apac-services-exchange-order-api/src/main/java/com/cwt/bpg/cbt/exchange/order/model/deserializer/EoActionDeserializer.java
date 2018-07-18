package com.cwt.bpg.cbt.exchange.order.model.deserializer;

import com.cwt.bpg.cbt.exchange.order.model.EoAction;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class EoActionDeserializer extends JsonDeserializer<EoAction> {;
	
	@Override
	public EoAction deserialize(JsonParser parser, DeserializationContext context)
			throws IOException {
		
		String value = parser.getText();
		
		return value != null
				? EoAction.getEoAction(value)
				: null;
		
	}
}
