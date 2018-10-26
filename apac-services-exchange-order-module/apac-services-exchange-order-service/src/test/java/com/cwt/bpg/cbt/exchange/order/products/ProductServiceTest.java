package com.cwt.bpg.cbt.exchange.order.products;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.exchange.order.ProductRepository;
import com.cwt.bpg.cbt.exchange.order.model.BaseProduct;
import com.cwt.bpg.cbt.exchange.order.model.Product;
import com.cwt.bpg.cbt.exchange.order.model.Vendor;

public class ProductServiceTest {

	@Mock
	private ProductRepository repo;

	@InjectMocks
	private ProductService service;

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
		List<BaseProduct> value = createListOfProducts();
		when(repo.getProducts(countryCode)).thenReturn(value);
		
		List<BaseProduct> baseProducts = service.getProducts(countryCode);
		
		assertEquals(1, baseProducts.size());
		Mockito.verify(repo, Mockito.times(1)).getProducts(countryCode);
	}
	
	@Test
	public void canFilterProductsByCode() {

		final String countryCode = "HK";
		final String productCode = "ProductCode";
		List<BaseProduct> value = createListOfProducts();
		when(repo.getProducts(countryCode)).thenReturn(value);
		
		BaseProduct baseProduct = service.getProductByCode(countryCode, productCode);
		assertNotNull(baseProduct);
		
		Mockito.verify(repo, Mockito.times(1)).getProducts(countryCode);
	}
	
	@Test
	public void shouldGetVendorByProductCodeVendorCode() {
		final String countryCode = "HK";
		final String productCode = "ProductCode";
		final String vendorCode = "000012";
		List<BaseProduct> value = createListOfProducts();
		when(repo.getProducts(countryCode)).thenReturn(value);
		
		Vendor vendor = service.getVendor(countryCode, productCode, vendorCode);
		assertNotNull(vendor);
		assertThat(vendor.getCode(), is(vendorCode));
	}
	
	@Test
	public void shouldGetVendorByProductCodeVendorCodeNull() {
		final String countryCode = "HK";
		final String productCode = "ProductCode1";
		final String vendorCode = "000012";
		List<BaseProduct> value = createListOfProducts();
		when(repo.getProducts(countryCode)).thenReturn(value);
		
		Vendor vendor = service.getVendor(countryCode, productCode, vendorCode);
		assertNull(vendor);
	}
	
	@Test
	public void canFilterProductsByCodeNull() {

		final String countryCode = "HK";
		final String productCode = "P";
		List<BaseProduct> value = createListOfProducts();
		when(repo.getProducts(countryCode)).thenReturn(value);
		
		BaseProduct baseProduct = service.getProductByCode(countryCode, productCode);
		assertNull(baseProduct);
		
		Mockito.verify(repo, Mockito.times(1)).getProducts(countryCode);
	}

	private static List<BaseProduct> createListOfProducts() {
		BaseProduct baseProduct1 = new Product();
		String productCode = "ProductCode";
		List<Vendor> vendors = Arrays.asList(new Vendor() {
			private static final long serialVersionUID = -6678379343715153605L;
			{
				setName("Pacific West Vendor");
				setCode("000012");
			}
		});
		baseProduct1.setVendors(vendors);
		baseProduct1.setProductCode(productCode);
		BaseProduct baseProduct2 = new Product();
		return Arrays.asList(baseProduct1, baseProduct2);
	}

}
