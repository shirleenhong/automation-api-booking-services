package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.test.util.ReflectionTestUtils;

import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderException;
import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderNotFoundException;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.SequenceNumber;

public class ExchangeOrderServiceTest {

	@Mock
	private ExchangeOrderRepository repo;
	
	@Mock
	private SequenceNumberRepository sequentNumberRepo;
	
	@InjectMocks
	private ExchangeOrderService service;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(service, "maxRetryCount", 3);
	}
  
	@Test
	public void shouldCallSaveOrUpdateNew() throws ExchangeOrderException {
		ExchangeOrder eo = new ExchangeOrder();
		eo.setCountryCode("HK");
		service.saveExchangeOrder(eo);
		verify(repo, times(1)).saveOrUpdate(eo);
	}
	
	@Test
	public void shouldCallSaveOrUpdateExisting() throws ExchangeOrderException {
		ExchangeOrder eo = new ExchangeOrder();
		eo.setEoNumber("eoNumber");
		eo.setCountryCode("HK");
		when(repo.getExchangeOrder(eo.getEoNumber())).thenReturn(eo);
		when(repo.saveOrUpdate(eo)).thenReturn(eo.getEoNumber());
		
		ExchangeOrder result = service.saveExchangeOrder(eo);		
		verify(repo, times(1)).saveOrUpdate(eo);
		assertEquals(eo.getEoNumber(), result.getEoNumber());		
	}
	
	@Test(expected = ExchangeOrderNotFoundException.class)
	public void shouldNotUpdateIfEoNumberIsNotExisting() throws ExchangeOrderNotFoundException {
		ExchangeOrder eo = new ExchangeOrder();
		eo.setEoNumber("1122334455");
		eo.setCountryCode("HK");
		service.saveExchangeOrder(eo);		
	}
	
	@Test
	public void shouldCallSaveOrUpdateUpdated() throws ExchangeOrderException {
		
		SequenceNumber sn = mock(SequenceNumber.class);
		when(sequentNumberRepo.get(anyString())).thenReturn(Arrays.asList(sn));
		
		ExchangeOrder order = new ExchangeOrder();
		order.setCountryCode("HK");
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
