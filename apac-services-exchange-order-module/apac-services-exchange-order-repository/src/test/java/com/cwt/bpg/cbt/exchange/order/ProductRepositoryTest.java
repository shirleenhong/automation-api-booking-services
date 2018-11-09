package com.cwt.bpg.cbt.exchange.order;

import static com.cwt.bpg.cbt.exchange.order.ProductRepository.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.FieldEnd;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.model.*;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;
import com.mongodb.BasicDBObject;
import com.mongodb.WriteResult;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ProductRepositoryTest
{

	@Mock
	private Datastore dataStore;

	@Mock
	private MorphiaComponent morphia;

	@Mock
	private UpdateOperations<InProductList> inUpdateOps;

	@Mock
	private UpdateOperations<HkSgProductList> hkSgUpdateOps;

    @Mock
    private Query<HkSgProductList> hkSgQuery;

    @Mock
    private Query<InProductList> inQuery;

    @Mock
    private UpdateResults updateResults;

    @Mock
    private FieldEnd fieldEnd;

    @InjectMocks
	private ProductRepository repository;

    private HkSgProductList productList = new HkSgProductList();


    @Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		when(morphia.getDatastore()).thenReturn(dataStore);

        when(dataStore.createQuery(HkSgProductList.class)).thenReturn(hkSgQuery);
        when(dataStore.createUpdateOperations(HkSgProductList.class)).thenReturn(hkSgUpdateOps);
        when(dataStore.update(hkSgQuery, hkSgUpdateOps)).thenReturn(updateResults);
        when(hkSgQuery.field(anyString())).thenReturn(fieldEnd);
        when(hkSgQuery.filter(anyString(), anyObject())).thenReturn(hkSgQuery);
        when(fieldEnd.equalIgnoreCase(Country.HONG_KONG.getCode())).thenReturn(hkSgQuery);
        when(fieldEnd.equalIgnoreCase(Country.SINGAPORE.getCode())).thenReturn(hkSgQuery);

        when(dataStore.createQuery(InProductList.class)).thenReturn(inQuery);
        when(dataStore.createUpdateOperations(InProductList.class)).thenReturn(inUpdateOps);
        when(dataStore.update(inQuery, inUpdateOps)).thenReturn(updateResults);
        when(inQuery.field(anyString())).thenReturn(fieldEnd);
        when(inQuery.filter(anyString(), anyObject())).thenReturn(inQuery);
        when(fieldEnd.equalIgnoreCase(Country.INDIA.getCode())).thenReturn(inQuery);
		
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
		when(hkSgQuery.get()).thenReturn(productList);
		
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
		when(inQuery.get()).thenReturn(null);

        List<BaseProduct> baseProducts = repository.getProducts(Country.INDIA.getCode());

		assertNotNull(baseProducts);
		assertEquals(0, baseProducts.size());
	}
	
	@Test
	public void shouldGetNullHKProductList() {
		when(hkSgQuery.get()).thenReturn(null);

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

		when(inQuery.get()).thenReturn(inProductList);

        List<BaseProduct> result = repository.getProducts(Country.INDIA.getCode());

		assertNotNull(products);
		assertEquals(1, result.size());
	}

	@Test
	public void shouldRemoveProductNoResult() {
		String productCode = "91";

		String result = repository.removeProduct(Country.INDIA.getCode(), productCode);
		assertEquals(NO_RESULT, result);

		InOrder inOrder = verifyCheckIfExists(Country.INDIA.getCode());
        inOrder.verify(inQuery).filter(PRODUCTS_PRODUCTCODE, productCode);
	}

	@Test
	public void shouldRemoveProduct() {
		String productCode = "01";

		InProductList inProductList = new InProductList();
		inProductList.setCountryCode(Country.INDIA.getCode());
		List<InProductList> queryList = new ArrayList<>();
		queryList.add(inProductList);

		when(inQuery.asList()).thenReturn(queryList);
		when(inUpdateOps.disableValidation()).thenReturn(inUpdateOps);
        when(inUpdateOps.removeAll(anyString(), any(BasicDBObject.class))).thenReturn(inUpdateOps);

		String result = repository.removeProduct(Country.INDIA.getCode(), productCode);

		assertEquals(productCode, result);

		InOrder inOrder = verifyCheckIfExists(Country.INDIA.getCode());
		inOrder.verify(inQuery).filter(PRODUCTS_PRODUCTCODE, productCode);
        verifyRemoveQuery(inOrder, Country.INDIA.getCode());
	}

	@Test
	public void shouldRemoveProductHkSg() {
		String productCode = "01";

		HkSgProductList inProductList = new HkSgProductList();
		inProductList.setCountryCode(Country.HONG_KONG.getCode());

		List<HkSgProductList> queryList = new ArrayList<>();
		queryList.add(inProductList);

		when(hkSgQuery.asList()).thenReturn(queryList);
		when(hkSgUpdateOps.disableValidation()).thenReturn(hkSgUpdateOps);
        when(hkSgUpdateOps.removeAll(anyString(), any(BasicDBObject.class))).thenReturn(hkSgUpdateOps);

		String result = repository.removeProduct(Country.HONG_KONG.getCode(), productCode);

		assertEquals(productCode, result);

		InOrder inOrder = verifyCheckIfExists(Country.HONG_KONG.getCode());
		inOrder.verify(hkSgQuery).filter(PRODUCTS_PRODUCTCODE, productCode);
        verifyRemoveQuery(inOrder, Country.HONG_KONG.getCode());
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

		when(inQuery.asList()).thenReturn(queryList);
		when(inUpdateOps.disableValidation()).thenReturn(inUpdateOps);
        when(inUpdateOps.removeAll(anyString(), any(BasicDBObject.class))).thenReturn(inUpdateOps);

		String result = repository.removeVendor(Country.INDIA.getCode(), vendorCode);

		assertEquals(vendorCode, result);

		InOrder inOrder = verifyCheckIfExists(Country.INDIA.getCode());
		inOrder.verify(inQuery).filter(PRODUCTS_VENDORS_CODE, vendorCode);
		verifyRemoveQuery(inOrder, Country.INDIA.getCode());
	}

    @Test
    public void shouldReturnNoResultWhenThereIsNoVendorToRemove() {
        String vendorCode = "01";
        when(inQuery.asList()).thenReturn(Collections.emptyList());
        when(inUpdateOps.disableValidation()).thenReturn(inUpdateOps);
        when(inUpdateOps.removeAll(anyString(), any(BasicDBObject.class))).thenReturn(inUpdateOps);

        String result = repository.removeVendor(Country.INDIA.getCode(), vendorCode);

        assertEquals(NO_RESULT, result);

        InOrder inOrder = verifyCheckIfExists(Country.INDIA.getCode());
        inOrder.verify(inQuery).filter(PRODUCTS_VENDORS_CODE, vendorCode);
    }

    @Test
    public void shouldUpdateProductNoResult() {
        String productCode = "01";
        IndiaProduct inProduct = new IndiaProduct();
        inProduct.setProductCode(productCode);

        when(inQuery.asList()).thenReturn(Collections.emptyList());
        when(inUpdateOps.disableValidation()).thenReturn(inUpdateOps);

        String result = repository.saveProduct(Country.INDIA.getCode(), inProduct, false);

        assertEquals(NO_RESULT, result);
        verifyCheckIfExists(Country.INDIA.getCode());
        verifyNoMoreInteractions(morphia, dataStore);
    }

    @Test
    public void shouldUpdateProduct() {
        String productCode = "01";
        IndiaProduct inProduct = new IndiaProduct();
        inProduct.setProductCode(productCode);

        InProductList inProductList = new InProductList();
        inProductList.setCountryCode(Country.INDIA.getCode());

        List<InProductList> queryList = new ArrayList<>();
        queryList.add(inProductList);

        when(inQuery.asList()).thenReturn(queryList);
        when(inUpdateOps.disableValidation()).thenReturn(inUpdateOps);
        when(inUpdateOps.set(PRODUCTS_DOLLAR, inProduct)).thenReturn(inUpdateOps);

        String result = repository.saveProduct(Country.INDIA.getCode(), inProduct, false);

        assertEquals(productCode, result);
        verifyCheckIfExists(Country.INDIA.getCode());
        InOrder inOrder = inOrder(morphia, dataStore, inQuery, inUpdateOps);
        inOrder.verify(morphia).getDatastore();
        inOrder.verify(dataStore).createQuery(any());
        inOrder.verify(inQuery).filter(COUNTRY_CODE, Country.INDIA.getCode());
        inOrder.verify(inQuery).filter(PRODUCTS_PRODUCTCODE, productCode);
        inOrder.verify(dataStore).createUpdateOperations(InProductList.class);
        inOrder.verify(inUpdateOps).set(PRODUCTS_DOLLAR, inProduct);
        inOrder.verify(dataStore).update(inQuery, inUpdateOps);
    }

    @Test
    public void shouldUpdateVendorNoResult() {
        String productCode = "01";
        Vendor vendor = new Vendor();
        vendor.setCode("01");

        when(inUpdateOps.disableValidation()).thenReturn(inUpdateOps);

        String result = repository.saveVendor(Country.INDIA.getCode(), productCode, vendor, false);

        assertEquals(NO_RESULT, result);
        verifyCheckIfExists(Country.INDIA.getCode());
    }

    @Test
    public void shouldInsertVendor() {
        String productCode = "01";
        Vendor vendor = new Vendor();
        vendor.setCode("01");

        List<Vendor> vendors = new ArrayList<>();
        vendors.add(vendor);

        IndiaProduct indiaProduct = new IndiaProduct();
        indiaProduct.setVendors(vendors);

        List<IndiaProduct> indiaProducts = new ArrayList<>();
        indiaProducts.add(indiaProduct);

        InProductList inProductList = new InProductList();
        inProductList.setCountryCode(Country.INDIA.getCode());
        inProductList.setProducts(indiaProducts);

        List<InProductList> queryList = new ArrayList<>();
        queryList.add(inProductList);

        when(inQuery.asList()).thenReturn(queryList);
        when(inUpdateOps.push(PRODUCTS_DOLLAR_VENDORS, vendor)).thenReturn(inUpdateOps);
        when(inUpdateOps.disableValidation()).thenReturn(inUpdateOps);

        String result = repository.saveVendor(Country.INDIA.getCode(), productCode, vendor, true);

        assertEquals(vendor.getCode(), result);
        InOrder inOrder = inOrder(morphia, dataStore, inQuery, inUpdateOps);
        inOrder.verify(morphia).getDatastore();
        inOrder.verify(dataStore).createQuery(any());
        inOrder.verify(inQuery).filter(COUNTRY_CODE, Country.INDIA.getCode());
        inOrder.verify(inQuery).filter(PRODUCTS_PRODUCTCODE, productCode);
        inOrder.verify(dataStore).createUpdateOperations(InProductList.class);
        inOrder.verify(inUpdateOps).push(PRODUCTS_DOLLAR_VENDORS, vendor);
        inOrder.verify(dataStore).update(inQuery, inUpdateOps);
    }

    @Test
    public void shouldUpdateVendor() {
        String productCode = "01";
        Vendor vendor = new Vendor();
        vendor.setCode("01");

        List<Vendor> vendors = new ArrayList<>();
        vendors.add(vendor);

        IndiaProduct indiaProduct = new IndiaProduct();
        indiaProduct.setVendors(vendors);

        List<IndiaProduct> indiaProducts = new ArrayList<>();
        indiaProducts.add(indiaProduct);

        InProductList inProductList = new InProductList();
        inProductList.setCountryCode(Country.INDIA.getCode());
        inProductList.setProducts(indiaProducts);

        List<InProductList> queryList = new ArrayList<>();
        queryList.add(inProductList);

        when(inQuery.asList()).thenReturn(queryList);
        when(inUpdateOps.disableValidation()).thenReturn(inUpdateOps);

        String result = repository.saveVendor(Country.INDIA.getCode(), productCode, vendor, false);

        assertEquals(vendor.getCode(), result);
        verifyCheckIfExists(Country.INDIA.getCode());
        InOrder inOrder = inOrder(morphia, dataStore, inQuery, inUpdateOps);
        inOrder.verify(morphia).getDatastore();
        inOrder.verify(dataStore).createQuery(any());
        inOrder.verify(inQuery).filter(COUNTRY_CODE, Country.INDIA.getCode());inOrder.verify(dataStore).createQuery(any());
        inOrder.verify(inQuery).filter(PRODUCTS_VENDORS_CODE, productCode);
    }

    @Test
    public void shouldInsertProduct() {
        Product product = new Product();
        String expected = "00";
        product.setProductCode(expected);
        String countryCode = "HK";

        when(hkSgUpdateOps.push(PRODUCTS, product)).thenReturn(hkSgUpdateOps);
        when(updateResults.getWriteResult()).thenReturn(mock(WriteResult.class));

        String actual = repository.saveProduct(countryCode, product, true);

        assertThat(actual, equalTo(expected));
        InOrder inOrder = inOrder(morphia, dataStore, hkSgQuery, hkSgUpdateOps, updateResults);
        inOrder.verify(morphia).getDatastore();
        inOrder.verify(dataStore).createQuery(any());
        inOrder.verify(hkSgQuery).filter(COUNTRY_CODE, countryCode);
        inOrder.verify(dataStore).createUpdateOperations(any());
        inOrder.verify(hkSgUpdateOps).push(PRODUCTS, product);
        inOrder.verify(dataStore).update(hkSgQuery, hkSgUpdateOps);
    }

	private InOrder verifyCheckIfExists(String countryCode) {

		boolean isIndia = Country.INDIA.getCode().equalsIgnoreCase(countryCode);

		InOrder inOrder = inOrder(morphia, dataStore, inQuery, hkSgQuery, hkSgUpdateOps,
				inUpdateOps);

		inOrder.verify(morphia).getDatastore();
		inOrder.verify(dataStore).createQuery(any());
		inOrder.verify(isIndia ? inQuery
				: hkSgQuery).filter(COUNTRY_CODE, countryCode);

		return inOrder;
	}


	private void verifyRemoveQuery(InOrder inOrder, String countryCode) {

		boolean isIndia = Country.INDIA.getCode().equalsIgnoreCase(countryCode);

		inOrder.verify(dataStore).createUpdateOperations(any());
        inOrder.verify(isIndia ? inUpdateOps
				: hkSgUpdateOps).disableValidation();
        inOrder.verify(isIndia ? inUpdateOps
				: hkSgUpdateOps).removeAll(anyString(), any(BasicDBObject.class));
        if(isIndia) {
        	inOrder.verify(dataStore).update(inQuery, inUpdateOps);
        }else {
        	inOrder.verify(dataStore).update(hkSgQuery, hkSgUpdateOps);
        }
	}
}
