package com.cwt.bpg.cbt.utils;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.core.JsonGenerator;

@RunWith(MockitoJUnitRunner.class)
public class ObjectIdSerializerTest {
	
	@Mock
    private JsonGenerator gen;

    @Test
    public void shouldSerializeObjectId() throws IOException {
    	ObjectIdSerializer serializer = new ObjectIdSerializer();
        serializer.serialize(new ObjectId("5b88e9b6a43d37260826cbce"), gen, null);

        String expectedOutput = "5b88e9b6a43d37260826cbce";
        verify(gen, times(1)).writeString(expectedOutput);
    }
}