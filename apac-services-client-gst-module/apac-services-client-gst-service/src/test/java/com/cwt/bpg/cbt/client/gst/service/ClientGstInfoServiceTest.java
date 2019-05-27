package com.cwt.bpg.cbt.client.gst.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;
import com.cwt.bpg.cbt.client.gst.repository.ClientGstInfoRepository;


@RunWith(MockitoJUnitRunner.class)
public class ClientGstInfoServiceTest {
	 
	@Mock
	private ClientGstInfoRepository respository;
	
	@InjectMocks
	private ClientGstInfoService service;
	
	@Test
	public void shouldReturnGstInfoByGstIn() {
		final String givenGstIn = "123456";
		
		when(respository.get(anyString())).thenReturn(new ClientGstInfo());
		ClientGstInfo response = respository.get(givenGstIn);
		
		assertNotNull(response);
	}
	
	@Test
	public void shouldReturnAllGstInfoByGstIn() {	
		when(respository.getAll()).thenReturn(Arrays.asList(new ClientGstInfo(), new ClientGstInfo()));
    
		List<ClientGstInfo> response = service.getAllClientGstInfo();
		
		assertNotNull(response);
	}
	
	@Test
	public void shouldSaveGstInfo() {	
		
		final ClientGstInfo givenInfo = new ClientGstInfo();
		givenInfo.setGstin("12345");
		
		when(respository.put(anyObject())).thenReturn(givenInfo);
		ClientGstInfo response = service.save(givenInfo);
		assertEquals(givenInfo.getGstin(), response.getGstin());
	}
	
	@Test
	public void shouldRemoveGstInfo() {	
		final String givenGstIn = "123456";
		
		when(respository.remove(anyString())).thenReturn(givenGstIn);
		String response = service.remove(givenGstIn);
		assertEquals(givenGstIn, response);
	}

}
