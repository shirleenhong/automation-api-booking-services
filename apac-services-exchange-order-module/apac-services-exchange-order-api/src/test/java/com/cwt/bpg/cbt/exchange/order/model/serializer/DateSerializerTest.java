package com.cwt.bpg.cbt.exchange.order.model.serializer;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDateTime;

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
		LocalDateTime dateTime = LocalDateTime
				.of(2018, 1, 2, 3, 4, 5);

		serializer.serialize(dateTime, generator, null);
		verify(generator, times(1)).writeString("2018-01-02 03:04:05");
	}
	
	@Test(expected=DateTimeException.class)
	public void testDeserializeWithException() throws IOException {
		LocalDateTime dateTime = LocalDateTime
				.of(2018, 13, 1, 3, 4, 5);

		serializer.serialize(dateTime, generator, null);
	}
}


