package com.cwt.bpg.cbt.exchange.order.products;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.*;

import java.util.Arrays;
import java.util.List;

import com.cwt.bpg.cbt.exchange.order.model.BaseProduct;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.exchange.order.ProductRepository;
import com.cwt.bpg.cbt.exchange.order.model.Product;
import com.cwt.bpg.cbt.exchange.order.model.Vendor;

public class ProductsServiceTest {

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
		assertThat(vendor.getVendorNumber(), is(vendorCode));
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
				setVendorName("Pacific West Vendor");
				setVendorNumber("000012");
			}
		});
		baseProduct1.setVendors(vendors);
		baseProduct1.setProductCode(productCode);
		BaseProduct baseProduct2 = new Product();
		return Arrays.asList(baseProduct1, baseProduct2);
	}

}
