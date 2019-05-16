package com.cwt.bpg.cbt.exchange.order.products;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.ProductRepository;
import com.cwt.bpg.cbt.exchange.order.ProductRepositoryFactory;
import com.cwt.bpg.cbt.exchange.order.model.BaseProduct;
import com.cwt.bpg.cbt.exchange.order.model.Product;
import com.cwt.bpg.cbt.exchange.order.model.Vendor;

public class ProductServiceTest {

	@SuppressWarnings("rawtypes")
	@Mock
	private ProductRepository repo;
	
	@Mock
	private ProductRepositoryFactory productFactory;

	@InjectMocks
	private ProductService service;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldGetProducts() {

		service.getProducts("HK");
		verify(repo, Mockito.times(1)).getProducts("HK");
	}

	@Test
	public void canFilterProducts() {

		final String countryCode = "HK";
		List<BaseProduct> value = createListOfProducts();
		when(repo.getProducts(countryCode)).thenReturn(value);
		
		List<BaseProduct> baseProducts = service.getProducts(countryCode);
		
		assertEquals(1, baseProducts.size());
		verify(repo, Mockito.times(1)).getProducts(countryCode);
	}
	
	@Test
	public void canFilterProductsByCode() {

		final String countryCode = "HK";
		final String productCode = "ProductCode";
		List<BaseProduct> value = createListOfProducts();
		when(repo.getProducts(countryCode)).thenReturn(value);
		
		BaseProduct baseProduct = service.getProductByCode(countryCode, productCode);
		assertNotNull(baseProduct);
		
		verify(repo, Mockito.times(1)).getProducts(countryCode);
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
		
		verify(repo, Mockito.times(1)).getProducts(countryCode);
	}

	@Test
	public void shouldSaveUpdateProduct() {

		final String productCode = "ProductCode";
		when(productFactory.getProductRepository(anyString())).thenReturn(repo);

		when(productFactory.getProductRepository(anyString()).saveProduct(anyString(),
				any(BaseProduct.class), anyBoolean())).thenReturn(productCode);

		String product = service.saveProduct(Country.HONG_KONG.getCode(),
				Mockito.mock(BaseProduct.class), true);
		assertThat(product, is(equalTo(productCode)));

		verify(productFactory, Mockito.times(1))
				.getProductRepository(Country.HONG_KONG.getCode());
	}
	
	@Test
	public void shouldSaveUpdateVendor() {

		final String vendorCode = "VendorCode";
		final String productCode = "ProductCode";

		when(productFactory.getProductRepository(anyString())).thenReturn(repo);

		when(productFactory.getProductRepository(anyString()).saveVendor(anyString(),
				anyString(), any(Vendor.class), anyBoolean())).thenReturn(vendorCode);

		String vendor = service.saveVendor(Country.HONG_KONG.getCode(), productCode,
				Mockito.mock(Vendor.class), true);
		assertThat(vendor, is(equalTo(vendorCode)));

		verify(productFactory, Mockito.times(1))
				.getProductRepository(Country.HONG_KONG.getCode());
	}

	@Test
	public void shouldRemoveProduct() {

		final String productCode = "ProductCode";

		when(productFactory.getProductRepository(anyString())).thenReturn(repo);
		
		when(productFactory.getProductRepository(anyString()).removeProduct(anyString(),
				anyString())).thenReturn(productCode);

		String product = service.removeProduct(Country.HONG_KONG.getCode(), productCode);
		assertThat(product, is(equalTo(productCode)));

		verify(productFactory, Mockito.times(1))
				.getProductRepository(Country.HONG_KONG.getCode());

		verify(repo, Mockito.times(1)).removeProduct(Country.HONG_KONG.getCode(),
				productCode);
	}

	@Test
	public void shouldRemoveVendor() {

		final String vendorCode = "VendorCode";

		when(productFactory.getProductRepository(anyString())).thenReturn(repo);

		when(productFactory.getProductRepository(anyString()).removeVendor(anyString(),
				anyString())).thenReturn(vendorCode);

		String vendor = service.removeVendor(Country.HONG_KONG.getCode(), vendorCode);
		assertThat(vendor, is(equalTo(vendorCode)));

		verify(productFactory, Mockito.times(1))
				.getProductRepository(Country.HONG_KONG.getCode());
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
