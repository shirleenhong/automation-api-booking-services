package com.cwt.bpg.cbt.exchange.order.model.deserializer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import com.fasterxml.jackson.core.JsonParser;

public class DateDeserializerTest {

	@Mock
	private JsonParser parser;
	
	@InjectMocks
	private DateDeserializer deserializer = new DateDeserializer();
		
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testDeserializeNull() throws IOException {
		when(parser.getText()).thenReturn(null);
		assertNull(deserializer.deserialize(parser, null));
	}
	
	@Test
	public void testDeserialize() throws IOException {
		when(parser.getText()).thenReturn("2018-01-02T03:04:05.000Z");
		Instant result = deserializer.deserialize(parser, null);

        LocalDateTime ldt = LocalDateTime.ofInstant(result, ZoneOffset.UTC);
		assertEquals(2018, ldt.getYear());
		assertEquals(1, ldt.getMonthValue());
		assertEquals(2, ldt.getDayOfMonth());
		assertEquals(3, ldt.getHour());
		assertEquals(4, ldt.getMinute());
		assertEquals(5, ldt.getSecond());
	}

}
