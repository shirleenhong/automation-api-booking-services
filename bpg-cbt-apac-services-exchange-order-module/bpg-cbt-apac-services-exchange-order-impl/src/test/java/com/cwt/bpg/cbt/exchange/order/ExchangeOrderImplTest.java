package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.cwt.bpg.cbt.exchange.order.model.Product;
import com.cwt.bpg.cbt.exchange.order.model.ProductList;
import com.cwt.bpg.cbt.exchange.order.model.Vendor;
import com.cwt.bpg.cbt.mongodb.config.MongoDbConnection;
import com.cwt.bpg.cbt.mongodb.config.mapper.DBObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

@RunWith(MockitoJUnitRunner.class)
public class ExchangeOrderImplTest {
	
	@Mock
	private MongoDbConnection mongoDbConnection;

	@Mock
	private DBObjectMapper dBObjectMapper;
	
	@InjectMocks
	private ExchangeOrderApi exchangeOrderApi = new ExchangeOrderImpl();
	
	@SuppressWarnings("rawtypes")
	@Before
	public void setUp() throws Exception {
		FindIterable iterable = Mockito.mock(FindIterable.class);
		FindIterable iterableNoList = Mockito.mock(FindIterable.class);
		MongoCollection collection = Mockito.mock(MongoCollection.class);
		Document doc = Mockito.mock(Document.class);
		Mockito.when(mongoDbConnection.getCollection(Mockito.anyString())).thenReturn(collection);
		Mockito.when(collection.find(new Document("countryCode",Mockito.anyString()))).thenReturn(iterableNoList);
		Mockito.when(collection.find(new Document("countryCode","SG"))).thenReturn(iterable);
		Mockito.when(iterable.first()).thenReturn(doc);
		
		ProductList productList = new ProductList();
		
		Product prodA= new Product();
		
    	prodA.setCountryCode("SG");
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
    	
		Product prodB= new Product();
		prodB.setCountryCode("SG");
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
    	List<Product> prodList = new ArrayList<Product>();
    	prodList.add(prodA);
    	prodList.add(prodB);
    	productList.setProducts(prodList);
    	
		Mockito.when(dBObjectMapper.mapDocumentToBean(doc,ProductList.class)).thenReturn(productList);
	}
	
	@Test
	public void shouldGetProductList() throws IOException {
		
		
		List<Product> products = exchangeOrderApi.getProducts("SG");
		
		assertNotNull(products);
		assertEquals(2, products.size());
		
		assertEquals("BSP Ticket And MPD", products.get(0).getDescription());
		assertEquals("BANK SETTLEMENT PLAN", products.get(0).getVendors().get(0).getVendorName());
		
		assertEquals("AA SEGMENT BOOKING FEE", products.get(1).getDescription());
		assertEquals("BANK SETTLEMENT PLAN", products.get(1).getVendors().get(0).getVendorName());
		
	}
	
	@Test
	public void shouldGetNoProductList() {
		
		List<Product> products = exchangeOrderApi.getProducts("IN");
		
		assertNotNull(products);
		assertEquals(0, products.size());
		
	}
	
	@Test
	public void shouldGetNoProductListWhenException() throws IOException{
		
		Mockito.when(dBObjectMapper.mapDocumentToBean(Mockito.any(),Mockito.any())).thenThrow(IOException.class);
		
		List<Product> products = exchangeOrderApi.getProducts("IN");
		
		assertNotNull(products);
		assertEquals(0, products.size());
		
	}
}
