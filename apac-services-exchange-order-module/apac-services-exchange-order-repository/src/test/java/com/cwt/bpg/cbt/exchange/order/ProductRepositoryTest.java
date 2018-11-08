package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.FieldEnd;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.model.BaseProduct;
import com.cwt.bpg.cbt.exchange.order.model.HkSgProductList;
import com.cwt.bpg.cbt.exchange.order.model.InProductList;
import com.cwt.bpg.cbt.exchange.order.model.IndiaProduct;
import com.cwt.bpg.cbt.exchange.order.model.Product;
import com.cwt.bpg.cbt.exchange.order.model.Vendor;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ProductRepositoryTest
{

	@Mock
	private Datastore dataStore;

	@Mock
	private MorphiaComponent morphia;
	
	@Mock
	private UpdateOperations<InProductList> ops;
	
	@Mock
	private UpdateOperations<HkSgProductList> opsHkSg;

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
		prodA.setGstPercent(0D);
		prodA.setMi(false);
		prodA.setSortKey("00");
		prodA.setTicketNumber(false);
		prodA.setTicketPrefix("");
		prodA.setType("BT");

		Vendor vendorA = new Vendor();
		vendorA.setCountryCode("SG");
		vendorA.setCode("022000");
		vendorA.setAddress1("");
		vendorA.setAddress2("");
		vendorA.setCity("SINGAPORE");
		vendorA.setMisc(false);
		vendorA.setSortKey("BSP");
		vendorA.setName("BANK SETTLEMENT PLAN");

		prodA.getVendors().add(vendorA);

		Product prodB = new Product();
		prodB.setProductCode("01");
		prodB.setDescription("AA SEGMENT BOOKING FEE");
		prodB.setGstPercent(0D);
		prodB.setMi(false);
		prodB.setSortKey("01");
		prodB.setTicketNumber(false);
		prodB.setTicketPrefix("");
		prodB.setType("BT");

		Vendor vendorB = new Vendor();
		vendorB.setCountryCode("SG");
		vendorB.setCode("022000");
		vendorB.setAddress1("");
		vendorB.setAddress2("");
		vendorB.setCity("SINGAPORE");
		vendorB.setMisc(false);
		vendorB.setSortKey("BSP");
		vendorB.setName("BANK SETTLEMENT PLAN");
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
		when(fieldEnd.equalIgnoreCase("SG")).thenReturn(query);
		when(query.get()).thenReturn(productList);
		
		List<BaseProduct> baseProducts = repository.getProducts("SG");

		assertNotNull(baseProducts);
		assertEquals(2, baseProducts.size());

		assertEquals("AA SEGMENT BOOKING FEE", baseProducts.get(0).getDescription());
		assertEquals("BANK SETTLEMENT PLAN", baseProducts.get(0).getVendors().get(0).getName());
		
		assertEquals("BSP Ticket And MPD", baseProducts.get(1).getDescription());
		assertEquals("BANK SETTLEMENT PLAN", baseProducts.get(1).getVendors().get(0).getName());
	}
	
	@Test
	public void shouldGetNullINProductList() {

		Query query = mock(Query.class);
		FieldEnd fieldEnd = mock(FieldEnd.class);
				
		when(morphia.getDatastore()).thenReturn(dataStore);
		when(morphia.getDatastore().createQuery(InProductList.class)).thenReturn(query);
		when(morphia.getDatastore().createQuery(InProductList.class).field(anyString())).thenReturn(fieldEnd);
		when(fieldEnd.equalIgnoreCase(Country.INDIA.getCode())).thenReturn(query);
		when(query.get()).thenReturn(null);
		when(morphia.getDatastore().createQuery(InProductList.class)
				.field("countryCode").equalIgnoreCase(Country.INDIA.getCode()).get()).thenReturn(null);

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
		when(fieldEnd.equalIgnoreCase("HK")).thenReturn(query);
		when(query.get()).thenReturn(null);
		when(morphia.getDatastore().createQuery(HkSgProductList.class).field("countryCode").equalIgnoreCase("HK").get())
				.thenReturn(null);

		List<BaseProduct> baseProducts = repository.getProducts("HK");

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
		when(fieldEnd.equalIgnoreCase(Country.INDIA.getCode())).thenReturn(query);
		when(query.get()).thenReturn(inProductList);

        List<BaseProduct> result = repository.getProducts(Country.INDIA.getCode());

		assertNotNull(products);
		assertEquals(1, result.size());
		
	}
	
	@Test
	public void shouldRemoveProductNoResult() {
		
		String productCode = "91";
		Query query = mock(Query.class);
		
		when(dataStore.createQuery(InProductList.class)).thenReturn(query);
		when(query.filter(anyString(), anyObject())).thenReturn(query);
		
		when(dataStore.createUpdateOperations(InProductList.class)).thenReturn(ops);
        when(ops.set(anyString(), anyObject())).thenReturn(ops);
        when(ops.removeAll(anyString(), anyObject())).thenReturn(ops);
        final UpdateResults results = mock(UpdateResults.class);
        when(results.getUpdatedExisting()).thenReturn(true);
        
        when(dataStore.update(query, ops)).thenReturn(results);
		
		String result = repository.removeProduct(Country.INDIA.getCode(), productCode);
		
		assertEquals("", result);
	}
	
	@Test
	public void shouldRemoveProduct() {
		
		String productCode = "01";
		
		InProductList inProductList = new InProductList();
		inProductList.setCountryCode(Country.INDIA.getCode());
		List<InProductList> queryList = new ArrayList<>();
		queryList.add(inProductList);
		
		Query query = mock(Query.class);
		
		when(dataStore.createQuery(InProductList.class)).thenReturn(query);
		when(query.filter(anyString(), anyObject())).thenReturn(query);
		when(query.asList()).thenReturn(queryList);
		
		when(dataStore.createUpdateOperations(InProductList.class)).thenReturn(ops);
		when(ops.disableValidation()).thenReturn(ops);
        when(ops.removeAll(anyString(), anyObject())).thenReturn(ops);
        final UpdateResults results = mock(UpdateResults.class);
        when(results.getUpdatedExisting()).thenReturn(true);
        
        when(dataStore.update(query, ops)).thenReturn(results);
		
		String result = repository.removeProduct(Country.INDIA.getCode(), productCode);
		
		assertEquals(productCode, result);
	}
	
	@Test
	public void shouldRemoveProductHkSg() {
		
		String productCode = "01";
		
		HkSgProductList inProductList = new HkSgProductList();
		inProductList.setCountryCode(Country.HONG_KONG.getCode());
		
		List<HkSgProductList> queryList = new ArrayList<>();
		queryList.add(inProductList);
		
		Query query = mock(Query.class);
		
		when(dataStore.createQuery(HkSgProductList.class)).thenReturn(query);
		when(query.filter(anyString(), anyObject())).thenReturn(query);
		when(query.asList()).thenReturn(queryList);
		
		when(dataStore.createUpdateOperations(HkSgProductList.class)).thenReturn(opsHkSg);
		when(opsHkSg.disableValidation()).thenReturn(opsHkSg);
        when(opsHkSg.removeAll(anyString(), anyObject())).thenReturn(opsHkSg);
        final UpdateResults results = mock(UpdateResults.class);
        when(results.getUpdatedExisting()).thenReturn(true);
        
        when(dataStore.update(query, opsHkSg)).thenReturn(results);
		
		String result = repository.removeProduct(Country.HONG_KONG.getCode(), productCode);
		
		assertEquals(productCode, result);
	}
	
	
	@Test
	public void shouldRemoveVendor() {
		
		String productCode = "01";
		String vendorCode = "01";
		
		InProductList inProductList = new InProductList();
		inProductList.setCountryCode(Country.INDIA.getCode());
		
		IndiaProduct inProduct = new IndiaProduct();
		inProduct.setProductCode(productCode);
		
		List<IndiaProduct> indiaProducts = new ArrayList<>();
		indiaProducts.add(inProduct);
		inProductList.setProducts(indiaProducts);
		
		List<InProductList> queryList = new ArrayList<>();
		queryList.add(inProductList);
		Query query = mock(Query.class);
		
		when(dataStore.createQuery(InProductList.class)).thenReturn(query);
		when(query.filter(anyString(), anyObject())).thenReturn(query);
		when(query.asList()).thenReturn(queryList);
		
		when(dataStore.createUpdateOperations(InProductList.class)).thenReturn(ops);
		when(ops.disableValidation()).thenReturn(ops);
        when(ops.removeAll(anyString(), anyObject())).thenReturn(ops);
        final UpdateResults results = mock(UpdateResults.class);
        when(results.getUpdatedExisting()).thenReturn(true);
        
        when(dataStore.update(query, ops)).thenReturn(results);
		
		String result = repository.removeVendor(Country.INDIA.getCode(), vendorCode);
		
		assertEquals(vendorCode, result);
	}
}
