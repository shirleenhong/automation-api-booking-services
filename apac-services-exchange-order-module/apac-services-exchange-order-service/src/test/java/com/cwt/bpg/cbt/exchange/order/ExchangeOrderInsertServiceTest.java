package com.cwt.bpg.cbt.exchange.order;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.exchange.order.model.BaseExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.BaseProduct;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.Vendor;
import com.cwt.bpg.cbt.exchange.order.products.ProductService;

public class ExchangeOrderInsertServiceTest {

	public ExchangeOrderInsertService getService() {
		return service;
	}

	public void setService(ExchangeOrderInsertService service) {
		this.service = service;
	}

	public ExchangeOrderRepository getRepo() {
		return repo;
	}

	public void setRepo(ExchangeOrderRepository repo) {
		this.repo = repo;
	}

	public SequenceNumberService getSequenceNumberService() {
		return sequenceNumberService;
	}

	public void setSequenceNumberService(SequenceNumberService sequenceNumberService) {
		this.sequenceNumberService = sequenceNumberService;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	@InjectMocks
	private ExchangeOrderInsertService service;

	@Mock
	private ExchangeOrderRepository repo;

	@Mock
	private SequenceNumberService sequenceNumberService;

	@Mock
	private ProductService productService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		when(sequenceNumberService.getSequenceNumber(anyString())).thenReturn(1);
	}

	@Test
	public void shouldCallSaveExchangeOrder() {
		ExchangeOrder eo = createExchangeOrder(null);
		String newEoNumber = "0101112345";
		ExchangeOrder savedEo = createExchangeOrder(newEoNumber);
		BaseProduct product = createProduct();

		when(productService.getProductByCode(anyString(), anyString())).thenReturn(product);
		eo.setEoNumber(newEoNumber);
		when(repo.getExchangeOrder(anyString() , anyString())).thenReturn(eo);
		when(repo.save(savedEo)).thenReturn(newEoNumber);
		
		BaseExchangeOrder result = service.insert(savedEo);

		assertThat(result.getEoNumber(), is(equalTo(newEoNumber)));

		InOrder inOrder = inOrder(sequenceNumberService, productService, repo);
		inOrder.verify(sequenceNumberService, times(1)).getSequenceNumber(eo.getCountryCode());
		inOrder.verify(productService, times(1)).getProductByCode(eo.getCountryCode(), eo.getProductCode());
		inOrder.verify(repo, times(1)).save(savedEo);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionWhenProductDoesNotExist() {
		ExchangeOrder eo = createExchangeOrder(null);

		when(productService.getProductByCode(anyString(), anyString())).thenReturn(null);

		service.insert(eo);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionWhenVendorDoesNotExist() {
		BaseProduct product = createProduct();
		ExchangeOrder eo = new ExchangeOrder();
		eo.setEoNumber(null);
		eo.setCountryCode("HK");
		Vendor nonExistentVendor = createVendor("AAA");
		eo.setVendor(nonExistentVendor);

		when(productService.getProductByCode(anyString(), anyString())).thenReturn(product);

		service.insert(eo);
	}

	private ExchangeOrder createExchangeOrder(String eoNumber) {
		BaseProduct product = createProduct();
		ExchangeOrder eo = new ExchangeOrder();
		eo.setEoNumber(eoNumber);
		eo.setCountryCode("HK");
		eo.setProductCode("1");
		eo.setVendor(product.getVendors().get(0));
		return eo;
	}

	private BaseProduct createProduct() {
		BaseProduct product = new BaseProduct();
		Vendor vendor = createVendor("022000");
		product.setVendors(Collections.singletonList(vendor));
		return product;
	}

	private Vendor createVendor(String vendorCode) {
		Vendor vendor = new Vendor();
		vendor.setCode(vendorCode);
		return vendor;
	}
}
