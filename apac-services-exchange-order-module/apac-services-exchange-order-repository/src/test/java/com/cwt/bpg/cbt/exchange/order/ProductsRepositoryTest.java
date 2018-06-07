package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.FieldEnd;
import org.mongodb.morphia.query.Query;

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
		Mockito.when(morphia.getDatastore()).thenReturn(dataStore);
		
		DefaultProduct prodA = new DefaultProduct();

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

		DefaultProduct prodB = new DefaultProduct();
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
		List<DefaultProduct> prodList = new ArrayList<>();
		prodList.add(prodA);
		prodList.add(prodB);
		productList.setProducts(prodList);
	}

	@Test
	public void shouldGetProductList() throws IOException {
		
		Query query = Mockito.mock(Query.class);
		FieldEnd fieldEnd = Mockito.mock(FieldEnd.class);
		Mockito.when(dataStore.createQuery(HkSgProductList.class)).thenReturn(query);
		Mockito.when(query.field(Mockito.anyString())).thenReturn(fieldEnd);
		Mockito.when(fieldEnd.equal("SG")).thenReturn(query);		
		Mockito.when(query.get()).thenReturn(productList);
		
		List<Product> products = repository.getProducts("SG");

		assertNotNull(products);
		assertEquals(2, products.size());

		assertEquals("AA SEGMENT BOOKING FEE", products.get(0).getDescription());
		assertEquals("BANK SETTLEMENT PLAN", products.get(0).getVendors().get(0).getVendorName());
		
		assertEquals("BSP Ticket And MPD", products.get(1).getDescription());
		assertEquals("BANK SETTLEMENT PLAN", products.get(1).getVendors().get(0).getVendorName());
	}

	@Test
	public void shouldGetNoProductList() {

		Query query = Mockito.mock(Query.class);
		FieldEnd fieldEnd = Mockito.mock(FieldEnd.class);
		Mockito.when(dataStore.createQuery(HkSgProductList.class)).thenReturn(query);
		Mockito.when(query.field(Mockito.anyString())).thenReturn(fieldEnd);
		Mockito.when(fieldEnd.equal("IN")).thenReturn(query);		
		Mockito.when(query.get()).thenReturn(null);
				
		List<Product> products = repository.getProducts("IN");

		assertNotNull(products);
		assertEquals(0, products.size());

	}
	
	@Test
	public void shouldGetNullINProductList() {

		Query query = Mockito.mock(Query.class);
		FieldEnd fieldEnd = Mockito.mock(FieldEnd.class);
				
		when(morphia.getDatastore()).thenReturn(dataStore);
		when(morphia.getDatastore().createQuery(InProductList.class)).thenReturn(query);
		when(morphia.getDatastore().createQuery(InProductList.class).field(Mockito.anyString())).thenReturn(fieldEnd);
		when(fieldEnd.equal("IN")).thenReturn(query);
		Mockito.when(query.get()).thenReturn(null);
		when(morphia.getDatastore().createQuery(InProductList.class)
				.field("countryCode").equal("IN").get()).thenReturn(null);
				
		List<Product> products = repository.getProducts("IN");

		assertNotNull(products);
		assertEquals(0, products.size());

	}
	
	@Test
	public void shouldGetNullHKProductList() {

		Query query = Mockito.mock(Query.class);
		FieldEnd fieldEnd = Mockito.mock(FieldEnd.class);

		when(morphia.getDatastore()).thenReturn(dataStore);
		when(morphia.getDatastore().createQuery(HkSgProductList.class)).thenReturn(query);
		when(morphia.getDatastore().createQuery(HkSgProductList.class).field(Mockito.anyString())).thenReturn(fieldEnd);
		when(fieldEnd.equal("HK")).thenReturn(query);
		Mockito.when(query.get()).thenReturn(null);
		when(morphia.getDatastore().createQuery(HkSgProductList.class).field("countryCode").equal("HK").get())
				.thenReturn(null);

		List<Product> products = repository.getProducts("HK");

		assertNotNull(products);
		assertEquals(0, products.size());

	}

	@Test
	public void shouldGetNoProductListWhenException() throws IOException {

		Query query = Mockito.mock(Query.class);
		FieldEnd fieldEnd = Mockito.mock(FieldEnd.class);
		Mockito.when(dataStore.createQuery(HkSgProductList.class)).thenReturn(query);
		Mockito.when(query.field(Mockito.anyString())).thenReturn(fieldEnd);
		Mockito.when(fieldEnd.equal("IN")).thenReturn(query);		
		Mockito.when(query.get()).thenThrow(IOException.class);
		
		List<Product> products = repository.getProducts("IN");

		assertNotNull(products);
		assertEquals(0, products.size());
	}
	
	@Test
	public void canGetIndiaProductList() {
		
		InProductList inProductList = new InProductList();
		inProductList.setCountryCode("IN");
		List<IndiaProduct> products = new ArrayList<>();
		products.add(new IndiaProduct());
		inProductList.setProducts(products);

		Query query = Mockito.mock(Query.class);
		FieldEnd fieldEnd = Mockito.mock(FieldEnd.class);
		Mockito.when(dataStore.createQuery(InProductList.class)).thenReturn(query);
		Mockito.when(query.field(Mockito.anyString())).thenReturn(fieldEnd);
		Mockito.when(fieldEnd.equal("IN")).thenReturn(query);		
		Mockito.when(query.get()).thenReturn(inProductList);
		
		List<Product> result = repository.getProducts("IN");

		assertNotNull(products);
		assertEquals(1, result.size());
		
	}
}
