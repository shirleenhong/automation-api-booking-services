package com.cwt.bpg.cbt.utils;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.JsonNode;

@RunWith(MockitoJUnitRunner.class)
public class ObjectIdDeserializerTest {
	
	@Mock
    private JsonParser parser;

    @Test
    public void shouldDeserializeObjectId() throws IOException {
    	ObjectIdDeserializer serializer = new ObjectIdDeserializer();
        
        ObjectCodec oc = Mockito.mock(ObjectCodec.class);
		JsonNode node = Mockito.mock(JsonNode.class);
  
		when(parser.getCodec()).thenReturn(oc);
		when(oc.readTree(parser)).thenReturn(node);
		when(node.textValue()).thenReturn("5b88e9b6a43d37260826cbce");
		
		serializer.deserialize(parser, null);
		
        verify(parser, times(1)).getCodec();
        verify(oc, times(1)).readTree(parser);
        verify(node, times(1)).textValue();
    }
}