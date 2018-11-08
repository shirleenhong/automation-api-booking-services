package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.model.HkSgProductList;
import com.cwt.bpg.cbt.exchange.order.model.InProductList;

public class ProductRepositoryFactoryTest
{
	@InjectMocks
	private ProductRepositoryFactory factory;
	
	@Mock
    private ProductRepository<InProductList> indiaRepository;

	@Mock
    private ProductRepository<HkSgProductList> hkSgRepository;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void shouldGetProductRepositoryIndia() {
		
		String countryCode = Country.INDIA.getCode();
		ProductRepository repo = factory.getProductRepository(countryCode);
		assertEquals(indiaRepository, repo);
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void shouldGetProductRepositoryHkSg() {
		
		String countryCode = Country.HONG_KONG.getCode();
		ProductRepository repo = factory.getProductRepository(countryCode);
		assertEquals(hkSgRepository, repo);
	}
}
