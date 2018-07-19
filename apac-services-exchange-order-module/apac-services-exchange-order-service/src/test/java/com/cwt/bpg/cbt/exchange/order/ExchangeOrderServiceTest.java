package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;

import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.test.util.ReflectionTestUtils;

import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderNoContentException;
import com.cwt.bpg.cbt.exchange.order.model.*;
import com.cwt.bpg.cbt.exchange.order.products.ProductService;
import com.cwt.bpg.cbt.utils.ServiceUtils;

public class ExchangeOrderServiceTest {

	@Mock
	private ExchangeOrderRepository repo;
	
	@Mock
	private SequenceNumberRepository sequentNumberRepo;
	
	@InjectMocks
	private ExchangeOrderService service;

	@Mock
	private ProductService productService;
	
	@Mock
	private ScaleConfig scaleConfig;
	
	@Mock
	private ServiceUtils serviceUtils;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(service, "maxRetryCount", 3);
		
		Mockito.when(scaleConfig.getScale(Mockito.eq("SG"))).thenReturn(2);
		Mockito.when(scaleConfig.getScale(Mockito.eq("HK"))).thenReturn(0);
		
		doNothing().when(serviceUtils).modifyTargetObject(anyObject(), anyObject());
	
	}
  
	@Test
	public void shouldCallSaveOrUpdateNew() throws ExchangeOrderNoContentException {
		ExchangeOrder eo = new ExchangeOrder();
		eo.setCountryCode("HK");

		BaseProduct baseProduct = new BaseProduct();
		Vendor vendor = new Vendor();
		vendor.setCode("022000");
		eo.setVendor(vendor);
		baseProduct.setVendors(Arrays.asList(vendor));
		when(productService.getProductByCode(anyString(),anyString())).thenReturn(baseProduct);

		service.saveExchangeOrder(eo);
		verify(repo, times(1)).save(eo);
	}
	
	@Test
	public void shouldCallSaveExchangeOrder() throws ExchangeOrderNoContentException {
		ExchangeOrder eo = new ExchangeOrder();
		eo.setEoNumber(null);
		eo.setCountryCode("HK");

		BaseProduct baseProduct = new BaseProduct();
		Vendor vendor = new Vendor();
		vendor.setCode("022000");
		eo.setVendor(vendor);
		baseProduct.setVendors(Arrays.asList(vendor));
		when(productService.getProductByCode(anyString(),anyString())).thenReturn(baseProduct);

		when(repo.getExchangeOrder(eo.getEoNumber())).thenReturn(eo);
		when(repo.save(eo)).thenReturn(eo.getEoNumber());
		
		ExchangeOrder result = service.saveExchangeOrder(eo);		
		verify(repo, times(1)).save(eo);
		assertEquals(null, result.getEoNumber());		
	}
	
	@Test
	public void shouldCallUpdateExistingExchangeOrder() throws ExchangeOrderNoContentException {
		ExchangeOrder eo = new ExchangeOrder();
		eo.setEoNumber("eoNumber");
		eo.setCountryCode("HK");

		BaseProduct baseProduct = new BaseProduct();
		Vendor vendor = new Vendor();
		vendor.setCode("022000");
		eo.setVendor(vendor);
		baseProduct.setVendors(Arrays.asList(vendor));
		when(productService.getProductByCode(anyString(),anyString())).thenReturn(baseProduct);

		when(repo.getExchangeOrder(eo.getEoNumber())).thenReturn(eo);
		when(repo.update(eo)).thenReturn(eo);
		
		ExchangeOrder result = service.saveExchangeOrder(eo);		
		verify(repo, times(1)).update(eo);
		assertEquals(eo.getEoNumber(), result.getEoNumber());		
	}
	
	@Test(expected = ExchangeOrderNoContentException.class)
	public void shouldNotUpdateIfEoNumberIsNotExisting() throws ExchangeOrderNoContentException {
		ExchangeOrder eo = new ExchangeOrder();
		eo.setEoNumber("1122334455");
		eo.setCountryCode("HK");
		service.saveExchangeOrder(eo);		
	}
	
	@Test
	public void shouldCallSaveOrUpdateUpdated() throws ExchangeOrderNoContentException {
		
		SequenceNumber sn = mock(SequenceNumber.class);
		when(sequentNumberRepo.get(anyString())).thenReturn(Arrays.asList(sn));
		
		ExchangeOrder order = new ExchangeOrder();
		order.setCountryCode("HK");

		BaseProduct baseProduct = new BaseProduct();
		Vendor vendor = new Vendor();
		vendor.setCode("022000");
		order.setVendor(vendor);
		baseProduct.setVendors(Arrays.asList(vendor));
		when(productService.getProductByCode(anyString(),anyString())).thenReturn(baseProduct);

		service.saveExchangeOrder(order);
		verify(sn, times(1)).getValue();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void resetSgHkSequenceNumber() {
		
		SequenceNumber sn = mock(SequenceNumber.class);
		List list = new ArrayList<SequenceNumber>();
		list.add(sn);
		list.add(sn);
				
		when(sequentNumberRepo.get("SG","HK")).thenReturn(list);
		
		service.resetHkSgSequenceNumber();
		
		verify(sn, times(2)).setValue(0);
		verify(sequentNumberRepo, times(1)).save(list);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void resetIndiaSequenceNumber() {
		
		SequenceNumber sn = mock(SequenceNumber.class);
		List list = new ArrayList<SequenceNumber>();
		list.add(sn);
				
		when(sequentNumberRepo.get("IN")).thenReturn(list);
		
		service.resetIndiaSequenceNumber();
		
		verify(sn, times(1)).setValue(0);
		verify(sequentNumberRepo, times(1)).save(list);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldThrowException() {
		SequenceNumber sn = mock(SequenceNumber.class);
		when(sequentNumberRepo.get(anyString())).thenReturn(Arrays.asList(sn));		
		when(sequentNumberRepo.save(eq(sn))).thenThrow(ConcurrentModificationException.class);
		service.getSequenceNumber("HK");
		verify(sequentNumberRepo, times(3)).save(sn);
	}
	@Test
	public void shouldCallGetExchangeOrder() {
		
		String eoNumber = "1806100005";
		service.getExchangeOrder(eoNumber);
		verify(repo, times(1)).getExchangeOrder(eoNumber);
	}
		
	@Test
	public void testResetIndiaSchedule(){
	    
		Calendar today = Calendar.getInstance();
	    
	    Date nextExecutionTime = getNextExecutionTime(today, "0 30 11 1 * ?");

	    Calendar result = Calendar.getInstance();	    
	    result.setTime(nextExecutionTime);
	    
	    assertEquals(1, result.get(Calendar.DAY_OF_MONTH));
	    assertEquals(11, result.get(Calendar.HOUR_OF_DAY));
	    assertEquals(30, result.get(Calendar.MINUTE));
	}
	
	@Test
	public void testResetSgHkSchedule(){
	    
	    Calendar today = Calendar.getInstance();
	    Date nextExecutionTime = getNextExecutionTime(today, "0 0 9 1 * ?");
	    
	    Calendar result = Calendar.getInstance();
	    result.setTime(nextExecutionTime);
	    
	    assertEquals(1, result.get(Calendar.DAY_OF_MONTH));
	    assertEquals(9, result.get(Calendar.HOUR_OF_DAY));

	}

	private Date getNextExecutionTime(
			Calendar today, String schedule) {
		
		CronTrigger trigger = new CronTrigger(schedule);
		
		final Date firstOfMonth = today.getTime();
	    
	    Date nextExecutionTime = trigger.nextExecutionTime(
	        new TriggerContext() {

	            @Override
	            public Date lastScheduledExecutionTime() {
	                return firstOfMonth;
	            }

	            @Override
	            public Date lastActualExecutionTime() {
	                return firstOfMonth;
	            }

	            @Override
	            public Date lastCompletionTime() {
	                return firstOfMonth;
	            }
	        });
		return nextExecutionTime;
	}
}
