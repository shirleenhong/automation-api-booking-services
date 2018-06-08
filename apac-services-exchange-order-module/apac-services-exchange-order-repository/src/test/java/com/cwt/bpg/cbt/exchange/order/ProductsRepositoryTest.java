package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.FieldEnd;
import org.mongodb.morphia.query.Query;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.model.*;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ProductsRepositoryTest {

	@Mock
	private Datastore dataStore;

	@Mock
	private MorphiaComponent morphia;

	@InjectMocks
	private ProductRepository repository;
	
	private HkSgProductList productList = new HkSgProductList();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		when(morphia.getDatastore()).thenReturn(dataStore);
		
		Product prodA = new Product();

		prodA.setProductCode("00");
		prodA.setDescription("BSP Ticket And MPD");
		prodA.setGst(0D);
		prodA.setMi(false);
		prodA.setSortKey("00");
		prodA.setTicketNumber(false);
		prodA.setTicketPrefix("");
		prodA.setType("BT");

		Vendor vendorA = new Vendor();
		vendorA.setCountry("SG");
		vendorA.setVendorNumber("022000");
		vendorA.setAddress1("");
		vendorA.setAddress2("");
		vendorA.setCity("SINGAPORE");
		vendorA.setContactNo("");
		vendorA.setCountry("SINGAPORE");
		vendorA.setEmail("");
		vendorA.setFaxNumber("");
		vendorA.setMisc(false);
		vendorA.setSortKey("BSP");
		vendorA.setVendorName("BANK SETTLEMENT PLAN");

		prodA.getVendors().add(vendorA);

		Product prodB = new Product();
		prodB.setProductCode("01");
		prodB.setDescription("AA SEGMENT BOOKING FEE");
		prodB.setGst(0D);
		prodB.setMi(false);
		prodB.setSortKey("01");
		prodB.setTicketNumber(false);
		prodB.setTicketPrefix("");
		prodB.setType("BT");

		Vendor vendorB = new Vendor();
		vendorB.setCountryCode("SG");
		vendorB.setVendorNumber("022000");
		vendorB.setAddress1("");
		vendorB.setAddress2("");
		vendorB.setCity("SINGAPORE");
		vendorB.setContactNo("");
		vendorB.setCountry("SINGAPORE");
		vendorB.setEmail("");
		vendorB.setFaxNumber("");
		vendorB.setMisc(false);
		vendorB.setSortKey("BSP");
		vendorB.setVendorName("BANK SETTLEMENT PLAN");
		prodB.getVendors().add(vendorB);
		List<Product> prodList = new ArrayList<>();
		prodList.add(prodA);
		prodList.add(prodB);
		productList.setProducts(prodList);
	}

	@Test
	public void shouldGetProductList() throws IOException {
		
		Query query = mock(Query.class);
		FieldEnd fieldEnd = mock(FieldEnd.class);
		when(dataStore.createQuery(HkSgProductList.class)).thenReturn(query);
		when(query.field(anyString())).thenReturn(fieldEnd);
		when(fieldEnd.equal("SG")).thenReturn(query);
		when(query.get()).thenReturn(productList);
		
		List<BaseProduct> baseProducts = repository.getProducts("SG");

		assertNotNull(baseProducts);
		assertEquals(2, baseProducts.size());

		assertEquals("AA SEGMENT BOOKING FEE", baseProducts.get(0).getDescription());
		assertEquals("BANK SETTLEMENT PLAN", baseProducts.get(0).getVendors().get(0).getVendorName());
		
		assertEquals("BSP Ticket And MPD", baseProducts.get(1).getDescription());
		assertEquals("BANK SETTLEMENT PLAN", baseProducts.get(1).getVendors().get(0).getVendorName());
	}

	@Test
	public void shouldGetNoProductList() {

		Query query = mock(Query.class);
		FieldEnd fieldEnd = mock(FieldEnd.class);
		when(dataStore.createQuery(HkSgProductList.class)).thenReturn(query);
		when(query.field(anyString())).thenReturn(fieldEnd);
		when(fieldEnd.equal(Country.INDIA.getCode())).thenReturn(query);
		when(query.get()).thenReturn(null);

        List<BaseProduct> baseProducts = repository.getProducts(Country.INDIA.getCode());

		assertNotNull(baseProducts);
		assertEquals(0, baseProducts.size());

	}
	
	@Test
	public void shouldGetNullINProductList() {

		Query query = mock(Query.class);
		FieldEnd fieldEnd = mock(FieldEnd.class);
				
		when(morphia.getDatastore()).thenReturn(dataStore);
		when(morphia.getDatastore().createQuery(InProductList.class)).thenReturn(query);
		when(morphia.getDatastore().createQuery(InProductList.class).field(anyString())).thenReturn(fieldEnd);
		when(fieldEnd.equal(Country.INDIA.getCode())).thenReturn(query);
		when(query.get()).thenReturn(null);
		when(morphia.getDatastore().createQuery(InProductList.class)
				.field("countryCode").equal(Country.INDIA.getCode()).get()).thenReturn(null);

        List<BaseProduct> baseProducts = repository.getProducts(Country.INDIA.getCode());

		assertNotNull(baseProducts);
		assertEquals(0, baseProducts.size());

	}
	
	@Test
	public void shouldGetNullHKProductList() {

		Query query = mock(Query.class);
		FieldEnd fieldEnd = mock(FieldEnd.class);

		when(morphia.getDatastore()).thenReturn(dataStore);
		when(morphia.getDatastore().createQuery(HkSgProductList.class)).thenReturn(query);
		when(morphia.getDatastore().createQuery(HkSgProductList.class).field(anyString())).thenReturn(fieldEnd);
		when(fieldEnd.equal("HK")).thenReturn(query);
		when(query.get()).thenReturn(null);
		when(morphia.getDatastore().createQuery(HkSgProductList.class).field("countryCode").equal("HK").get())
				.thenReturn(null);

		List<BaseProduct> baseProducts = repository.getProducts("HK");

		assertNotNull(baseProducts);
		assertEquals(0, baseProducts.size());

	}

	@Test
	public void shouldGetNoProductListWhenException() throws IOException {

		Query query = mock(Query.class);
		FieldEnd fieldEnd = mock(FieldEnd.class);
		when(dataStore.createQuery(HkSgProductList.class)).thenReturn(query);
		when(query.field(anyString())).thenReturn(fieldEnd);
		when(fieldEnd.equal(Country.INDIA.getCode())).thenReturn(query);
		when(query.get()).thenThrow(IOException.class);

        List<BaseProduct> baseProducts = repository.getProducts(Country.INDIA.getCode());

		assertNotNull(baseProducts);
		assertEquals(0, baseProducts.size());
	}
	
	@Test
	public void canGetIndiaProductList() {
		
		InProductList inProductList = new InProductList();
		inProductList.setCountryCode(Country.INDIA.getCode());
		List<IndiaProduct> products = new ArrayList<>();
		products.add(new IndiaProduct());
		inProductList.setProducts(products);

		Query query = mock(Query.class);
		FieldEnd fieldEnd = mock(FieldEnd.class);
		when(dataStore.createQuery(InProductList.class)).thenReturn(query);
		when(query.field(anyString())).thenReturn(fieldEnd);
		when(fieldEnd.equal(Country.INDIA.getCode())).thenReturn(query);
		when(query.get()).thenReturn(inProductList);

        List<BaseProduct> result = repository.getProducts(Country.INDIA.getCode());

		assertNotNull(products);
		assertEquals(1, result.size());
		
	}
}
