package com.cwt.bpg.cbt.exchange.order.products;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.exchange.order.ProductsRepository;

public class ProductsServiceTest {
	
	@Mock
	private ProductsRepository repo;
	
	@InjectMocks
	private ProductsService service;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldGetProduct() {
		
		service.getProducts("HK");
		Mockito.verify(repo, Mockito.times(1)).getProducts("HK");
	}
	

}
