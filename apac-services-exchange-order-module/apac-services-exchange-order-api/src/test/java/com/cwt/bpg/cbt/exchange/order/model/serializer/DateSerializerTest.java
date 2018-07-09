package com.cwt.bpg.cbt.exchange.order.model.serializer;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.Instant;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import com.fasterxml.jackson.core.JsonGenerator;

public class DateSerializerTest {

	@InjectMocks
	private DateSerializer serializer = new DateSerializer();
	
	@Mock
	private JsonGenerator generator;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
		
	@Test
	public void testDeserializeNull() throws IOException {
		 
		serializer.serialize(null, generator, null);
		
		verify(generator, times(1)).writeString(anyString());
	}
	
	@Test
	public void testDeserialize() throws IOException {
				
		Instant instant = Instant.parse("2018-01-02T03:04:05.000Z");
		serializer.serialize(instant, generator, null);
		verify(generator, times(1)).writeString("2018-01-02T03:04:05.000Z");
	}
	
	@Test(expected=DateTimeException.class)
	public void testDeserializeWithException() throws IOException {
		
		Instant instant = Instant.parse("2018-13-02T03:04:05.00Z");
		
		serializer.serialize(instant, generator, null);
		
		verify(generator, times(1)).writeString("2018-13-02T03:04:05.00Z");
	}
}

