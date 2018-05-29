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

import com.cwt.bpg.cbt.exchange.order.model.Insurance;

public class InsuranceControllerTest {

	@Mock
	private InsuranceService service;
	
	@InjectMocks
	private InsuranceController controller = new InsuranceController();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void canPutInsurance() {
		Insurance Insurance = new Insurance();
		Mockito.when(service.putInsurance(Insurance)).thenReturn(Insurance);
		
		ResponseEntity<Insurance> result = controller.updateInsurance(Insurance);

		assertNotNull(result.getBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	public void canGetInsuranceList() {
		List<Insurance> insuranceList = new ArrayList<>();
		Mockito.when(service.getAll()).thenReturn(insuranceList);
		
		ResponseEntity<List<Insurance>>result = controller.getInsuranceList();

		assertNotNull(result.getBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	
	@Test
	public void canRemoveInsurance() {
		final String type = "INV";
		Mockito.when(service.remove(type)).thenReturn("Success");
		
		ResponseEntity<String> result = controller.removeInsurance(type);

		assertNotNull(result.getBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
}
