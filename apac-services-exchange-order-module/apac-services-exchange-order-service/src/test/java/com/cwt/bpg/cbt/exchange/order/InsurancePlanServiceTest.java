package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.exchange.order.model.InsurancePlan;

public class InsurancePlanServiceTest {

	@Mock
	private InsurancePlanRepository insurancePlanRepository;
	
	
	@InjectMocks
	private InsurancePlanService service;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void canGetAllInsuranceType() {
		
		when(insurancePlanRepository.getAll()).thenReturn(new ArrayList<>());
		List<InsurancePlan> insuranceList = service.getAll();
		
		verify(insurancePlanRepository, times(1)).getAll();
		assertNotNull(insuranceList);
	}
	
	@Test
	public void canPutInsurance() {
		InsurancePlan insurance = new InsurancePlan();
		when(insurancePlanRepository.put(insurance)).thenReturn(insurance);
		
		service.putInsurancePlan(insurance);
		
		verify(insurancePlanRepository, times(1)).put(insurance);
	}
	
	@Test
	public void canRemoveInsurance() {
        
		final String removeObjectId = "5b2870d6284b8d1ac84300ad";
		when(insurancePlanRepository.remove(any(ObjectId.class))).thenReturn(removeObjectId);
		
		service.remove(removeObjectId);
		
		verify(insurancePlanRepository, times(1)).remove(any(ObjectId.class));
	}

}
