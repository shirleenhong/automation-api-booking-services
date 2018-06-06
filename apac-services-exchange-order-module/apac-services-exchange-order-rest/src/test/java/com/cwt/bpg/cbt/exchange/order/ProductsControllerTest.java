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

import com.cwt.bpg.cbt.exchange.order.model.Product;
import com.cwt.bpg.cbt.exchange.order.products.ProductService;

public class ProductsControllerTest {

	@Mock
	private ProductService service;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@InjectMocks
	private ProductsController controller = new ProductsController();

	@Test
	public void shouldReturnProduct() {
						
		ResponseEntity<List<Product>> result = controller.getProducts("HK");
		
		Mockito.when(service.getProducts("HK")).thenReturn(new ArrayList<>());
		assertNotNull(result.getBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

}
