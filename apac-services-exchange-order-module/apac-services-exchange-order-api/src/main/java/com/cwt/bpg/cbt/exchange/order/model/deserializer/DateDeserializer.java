package com.cwt.bpg.cbt.exchange.order.model.deserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class DateDeserializer extends JsonDeserializer<LocalDateTime> {

	private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public LocalDateTime deserialize(JsonParser parser, DeserializationContext ctxt)
			throws IOException {
		
		String date = parser.getText();
		
		if(date != null) {
			return LocalDateTime.parse(date, dateFormatter);	
		}
		
		return null;
		
	}
}
