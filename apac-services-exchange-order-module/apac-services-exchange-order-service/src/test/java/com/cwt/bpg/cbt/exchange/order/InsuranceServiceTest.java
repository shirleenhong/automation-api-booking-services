package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.exchange.order.model.Insurance;

public class InsuranceServiceTest {

	@Mock
	private InsuranceRepository insuranceRepository;
	
	
	@InjectMocks
	private InsuranceService service;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void canGetAllInsuranceType() {
		
		when(insuranceRepository.getAll()).thenReturn(new ArrayList<>());
		List<Insurance> insuranceList = service.getAll();
		
		verify(insuranceRepository, times(1)).getAll();
		assertNotNull(insuranceList);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void canPutInsurance() {
		Insurance insurance = new Insurance();
		when(insuranceRepository.put(insurance)).thenReturn(insurance);
		
		Insurance putInsurance = service.putInsurance(insurance);
		
		verify(insuranceRepository, times(1)).put(insurance);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void canRemoveInsurance() {
		Insurance insurance = new Insurance();
		final String removeObjectId = "Test";
		when(insuranceRepository.remove(removeObjectId)).thenReturn(removeObjectId);
		
		String remove = service.remove(removeObjectId);
		
		verify(insuranceRepository, times(1)).remove(removeObjectId);
	}

}
