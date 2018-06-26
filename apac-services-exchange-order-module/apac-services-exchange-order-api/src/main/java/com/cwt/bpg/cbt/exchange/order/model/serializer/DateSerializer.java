package com.cwt.bpg.cbt.exchange.order.model.serializer;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@Component
public class DateSerializer extends JsonSerializer<Instant> {
	
	private final DateTimeFormatter dateFormatter = DateTimeFormatter
				.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
				.withZone(ZoneId.of("UTC"));

	@Override
	public void serialize(Instant value, JsonGenerator gen, 
			SerializerProvider serializers)
			throws IOException {
	
		gen.writeString(value != null 
				? dateFormatter.format(value)
				: null);			
	}
}
