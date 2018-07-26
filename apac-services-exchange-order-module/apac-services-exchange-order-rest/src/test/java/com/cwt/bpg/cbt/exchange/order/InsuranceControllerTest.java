package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cwt.bpg.cbt.exchange.order.model.InsurancePlan;

public class InsuranceControllerTest {

	@Mock
	private InsurancePlanService service;
	
	@InjectMocks
	private InsurancePlanController controller = new InsurancePlanController();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void canPutInsurance() {
		InsurancePlan Insurance = new InsurancePlan();
		Mockito.when(service.putInsurancePlan(Insurance)).thenReturn(Insurance);
		
		ResponseEntity<InsurancePlan> result = controller.updateInsurancePlan(Insurance);

		assertNotNull(result.getBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	public void canGetInsuranceList() {
		List<InsurancePlan> insuranceList = new ArrayList<>();
		Mockito.when(service.getAll()).thenReturn(insuranceList);
		
		ResponseEntity<List<InsurancePlan>>result = controller.getInsurancePlanList();

		assertNotNull(result.getBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	
	@Test
	public void canRemoveInsurance() {
		final String type = "INV";
		Mockito.when(service.remove(type)).thenReturn("Success");
		
		ResponseEntity<String> result = controller.removeInsurancePlan(type);

		assertNotNull(result.getBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
}
