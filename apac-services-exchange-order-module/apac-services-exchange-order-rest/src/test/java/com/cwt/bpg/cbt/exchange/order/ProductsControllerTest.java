package com.cwt.bpg.cbt.exchange.order;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriComponentsBuilder;

import com.cwt.bpg.cbt.exchange.order.model.BaseProduct;
import com.cwt.bpg.cbt.exchange.order.model.Product;
import com.cwt.bpg.cbt.exchange.order.model.Vendor;
import com.cwt.bpg.cbt.exchange.order.products.ProductService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class ProductsControllerTest {
	
	@Mock
	private ProductService service;

	@InjectMocks
	private ProductsController controller;
	
	private MockMvc mockMvc;
	
	private String getAllProductsUrl;
	private String putProductsUrl;
	private String putIndiaProductsUrl;
	private String deleteProductsUrl;
	private String deleteVendorsUrl;
	private String putVendorsUrl;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		
		getAllProductsUrl = "/products/sg";
		putProductsUrl = "/products/sg";
		putIndiaProductsUrl = "/products/in";
		deleteProductsUrl = "/products/sg/00";
		deleteVendorsUrl = "/vendors/sg/00";
		putVendorsUrl = "/vendors/sg";
	}

	private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Test
	public void shouldReturnProduct() throws Exception {

		List<BaseProduct> products = new ArrayList<>();

		when(service.getProducts(anyString())).thenReturn(products);

        mockMvc.perform(get(getAllProductsUrl).contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

		verify(service, times(1)).getProducts(anyString());
	}

	@Test
	public void shouldSaveUpdateProducts() throws Exception {
		
		final String uri = UriComponentsBuilder.newInstance().path(putProductsUrl)
				.queryParam("insert", true)
				.build().toUriString();
		
		List<BaseProduct> products = new ArrayList<>();
		when(service.getProducts(anyString())).thenReturn(products);

		when(service.saveProduct(anyString(), any(Product.class), anyBoolean())).thenReturn("updatedResult");;
		
    	mockMvc.perform(put(uri)
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(new Product())))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
    	
		verify(service, times(1)).saveProduct(anyString(), any(Product.class), anyBoolean());
	}
	
	@Test
	public void shouldSaveUpdateIndiaProducts() throws Exception {
		
		final String uri = UriComponentsBuilder.newInstance().path(putIndiaProductsUrl)
				.queryParam("insert", true)
				.build().toUriString();
		
		List<BaseProduct> products = new ArrayList<>();
		when(service.getProducts(anyString())).thenReturn(products);

		when(service.saveProduct(anyString(), any(Product.class), anyBoolean())).thenReturn("updatedResult");;
		
    	mockMvc.perform(put(uri)
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(new Product())))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
    	
		verify(service, times(1)).saveProduct(anyString(), any(Product.class), anyBoolean());
	}
	
	@Test
	public void shouldSaveUpdateVendors() throws Exception {
		
		final String uri = UriComponentsBuilder.newInstance().path(putVendorsUrl)
				.queryParam("productCode", "00")
				.queryParam("insert", true)
				.build().toUriString();
		
		List<BaseProduct> products = new ArrayList<>();
		when(service.getProducts(anyString())).thenReturn(products);

		when(service.saveVendor(anyString(), anyString(), any(Vendor.class), anyBoolean())).thenReturn("updatedResult");
		
    	mockMvc.perform(put(uri)
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(new Vendor())))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
    	
		verify(service, times(1)).saveVendor(anyString(), anyString(), any(Vendor.class), anyBoolean());
	}
	
	@Test
	public void shouldDeleteProducts() throws Exception {

		List<BaseProduct> products = new ArrayList<>();
		when(service.getProducts(anyString())).thenReturn(products);

		when(service.removeProduct(anyString(), anyString())).thenReturn("deleteResult");
		
    	mockMvc.perform(delete(deleteProductsUrl)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
    	
		verify(service, times(1)).removeProduct(anyString(), anyString());
	}
	
	@Test
	public void shouldDeleteVendors() throws Exception {

		List<BaseProduct> products = new ArrayList<>();
		when(service.getProducts(anyString())).thenReturn(products);

		when(service.removeVendor(anyString(), anyString())).thenReturn("deleteResult");
		
    	mockMvc.perform(delete(deleteVendorsUrl)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
    	
		verify(service, times(1)).removeVendor(anyString(), anyString());
	}
	
	private static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}
	
}
