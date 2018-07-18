package com.cwt.bpg.cbt.exchange.order.model.serializer;

import com.cwt.bpg.cbt.exchange.order.model.EoAction;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class EoActionSerializer extends JsonSerializer<EoAction> {

	@Override
	public void serialize(EoAction value, JsonGenerator gen,
			SerializerProvider serializers)
			throws IOException {
	
		gen.writeString(value != null 
				? value.toString()
				: null);			
	}
}
