package com.cwt.bpg.cbt.exchange.order.products;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.exchange.order.ProductsRepository;
import com.cwt.bpg.cbt.exchange.order.model.Product;
import com.cwt.bpg.cbt.exchange.order.model.Vendor;

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
	public void shouldGetProducts() {

		service.getProducts("HK");
		Mockito.verify(repo, Mockito.times(1)).getProducts("HK");
	}

	@Test
	public void canFilterProducts() {

		final String countryCode = "HK";
		List<Product> value = createListOfProducts();
		when(repo.getProducts(countryCode)).thenReturn(value);
		
		List<Product> products = service.getProducts(countryCode);
		
		assertEquals(1, products.size());
		Mockito.verify(repo, Mockito.times(1)).getProducts(countryCode);
	}

	private static List<Product> createListOfProducts() {
		Product product1 = new Product();
		List<Vendor> vendors = Arrays.asList(new Vendor() {
			{
				setVendorName("Pacific West Vendor");
				setVendorNumber("000012");
			}
		});
		product1.setVendors(vendors);
		Product product2 = new Product();
		return Arrays.asList(product1, product2);
	}

}
