package com.cwt.bpg.cbt.exchange.order.model.serializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@Component
public class DateSerializer extends JsonSerializer<LocalDateTime> {
	
	private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@Override
	public void serialize(LocalDateTime value, JsonGenerator gen, 
			SerializerProvider serializers)
			throws IOException {
	
		gen.writeString(value != null 
				? value.format(dateFormatter)
				: null);
			
	}
}