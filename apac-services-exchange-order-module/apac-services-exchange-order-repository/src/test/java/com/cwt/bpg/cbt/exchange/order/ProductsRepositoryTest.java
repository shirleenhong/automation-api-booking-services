package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
	private ProductsRepository repository;
	
	private HkSgProductList productList = new HkSgProductList();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		Mockito.when(morphia.getDatastore()).thenReturn(dataStore);
		
		HkSgProduct prodA = new HkSgProduct();

		prodA.setProductCode("00");
		prodA.setDescription("BSP Ticket And MPD");
		prodA.setGst(0);
		prodA.setMi(false);
		prodA.setSortKey("00");
		prodA.setTktNo(false);
		prodA.setTktPrefix("");
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

		HkSgProduct prodB = new HkSgProduct();
		prodB.setProductCode("01");
		prodB.setDescription("AA SEGMENT BOOKING FEE");
		prodB.setGst(0);
		prodB.setMi(false);
		prodB.setSortKey("01");
		prodB.setTktNo(false);
		prodB.setTktPrefix("");
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
		List<HkSgProduct> prodList = new ArrayList<>();
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
}
