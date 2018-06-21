package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
	}
  
	@Test
	public void shouldCallSaveOrUpdateNew() {
		ExchangeOrder eo = new ExchangeOrder();
		eo.setCountryCode("HK");
		service.saveExchangeOrder(eo);
		verify(repo, times(1)).saveOrUpdate(eo);
	}
	
	@Test
	public void shouldCallSaveOrUpdateUpdated() {
		
		SequenceNumber sn = mock(SequenceNumber.class);
		when(sequentNumberRepo.get(anyString())).thenReturn(sn);
		
		ExchangeOrder order = new ExchangeOrder();
		order.setCountryCode("HK");
		service.saveExchangeOrder(order);
		verify(sn, times(1)).getValue();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void resetSequenceNumber() {
		
		SequenceNumber sn = mock(SequenceNumber.class);
		List list = new ArrayList<SequenceNumber>();
		list.add(sn);
		list.add(sn);
				
		when(sequentNumberRepo.getAll()).thenReturn(list);
		
		service.resetSequenceNumber();
		
		verify(sn, times(2)).setValue(0);
		verify(sequentNumberRepo, times(1)).save(list);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldThrowException() {
		SequenceNumber sn = mock(SequenceNumber.class);
		when(sequentNumberRepo.get(anyString())).thenReturn(sn);		
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
	public void testResetSchedule(){
	    
	    org.springframework.scheduling.support.CronTrigger trigger = 
	                                      new CronTrigger("0 0 12 1 * ?");
	    Calendar today = Calendar.getInstance();
	    today.set(Calendar.DAY_OF_MONTH, 1);

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

	    Calendar result = Calendar.getInstance();
	    
	    result.setTime(nextExecutionTime);
	    assertEquals(1, result.get(Calendar.DAY_OF_MONTH));
	    assertEquals(today.get(Calendar.MONTH) + 1, result.get(Calendar.MONTH));   

	}
}
