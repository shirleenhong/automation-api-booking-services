package com.cwt.bpg.cbt.client.gst.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;
import com.cwt.bpg.cbt.client.gst.model.ClientGstInfoResponse;
import com.cwt.bpg.cbt.client.gst.model.GstAirline;
import com.cwt.bpg.cbt.client.gst.repository.ClientGstInfoRepository;
import com.cwt.bpg.cbt.client.gst.repository.GstAirlineRepository;


@RunWith(MockitoJUnitRunner.class)
public class ClientGstInfoServiceTest {
	 
	@Mock
	private ClientGstInfoRepository clientGstInfoRepository;
	
	@Mock
	private GstAirlineRepository gstAirlineRepository;
	
	@InjectMocks
	private ClientGstInfoService service;
	
	@Test
	public void shouldReturnAllGstInfoByGstIn() {	
		when(clientGstInfoRepository.getAll()).thenReturn(Arrays.asList(new ClientGstInfo(), new ClientGstInfo()));
    
		List<ClientGstInfo> response = service.getAllClientGstInfo();
		
		assertNotNull(response);
	}
	
	@Test
	public void shouldReturnGstInfoByGstInWithAirlines() {
		final String givenGstIn = "123456";
				
		final GstAirline xsAirline = new GstAirline();
		xsAirline.setCode("XS");
		
		final GstAirline cxAirline = new GstAirline();
		cxAirline.setCode("CX");

		when(gstAirlineRepository.getAll()).thenReturn(Arrays.asList(xsAirline, cxAirline));
		when(clientGstInfoRepository.get(anyString())).thenReturn(new ClientGstInfo());
				
		final ClientGstInfoResponse response = service.getClientGstInfo(givenGstIn, Arrays.asList("XS", "CX", "LH"));
		
		assertNotNull(response.getAirlineCodes());
		assertNotNull(response.getClientGstInfo());
		
	}
	
	@Test
	public void shouldHandleNullGstInfo() {
		final String givenGstIn = "123456";
				
		final GstAirline xsAirline = new GstAirline();
		xsAirline.setCode("XS");
		
		final GstAirline cxAirline = new GstAirline();
		cxAirline.setCode("CX");

		when(gstAirlineRepository.getAll()).thenReturn(Arrays.asList(xsAirline, cxAirline));
		when(clientGstInfoRepository.get(anyString())).thenReturn(null);
				
		final ClientGstInfoResponse response = service.getClientGstInfo(givenGstIn, Arrays.asList("XS", "CX", "LH"));
		
		assertNull(response);
	}
	
	@Test
	public void shouldSaveGstInfo() {	
		final ClientGstInfo givenInfo = new ClientGstInfo();
		givenInfo.setGstin("12345");
		
		when(clientGstInfoRepository.put(anyObject())).thenReturn(givenInfo);
		final ClientGstInfo response = service.save(givenInfo);
		
		assertEquals(givenInfo.getGstin(), response.getGstin());
	}
	
	@Test
	public void shouldRemoveGstInfo() {	
		final String givenGstIn = "123456";
		
		when(clientGstInfoRepository.remove(anyString())).thenReturn(givenGstIn);
		final String response = service.remove(givenGstIn);
		
		assertEquals(givenGstIn, response);
	}

}
