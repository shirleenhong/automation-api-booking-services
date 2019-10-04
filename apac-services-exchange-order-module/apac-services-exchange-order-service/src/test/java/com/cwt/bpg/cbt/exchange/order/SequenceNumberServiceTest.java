package com.cwt.bpg.cbt.exchange.order;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mongodb.morphia.Key;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.test.util.ReflectionTestUtils;

import com.cwt.bpg.cbt.exchange.order.model.SequenceNumber;

public class SequenceNumberServiceTest {
	@InjectMocks
	private SequenceNumberService service;

	@Mock
	private SequenceNumberRepository sequentNumberRepo;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(service, "maxRetryCount", 3);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void resetSgHkSequenceNumber() {

		SequenceNumber sn = mock(SequenceNumber.class);
		List list = new ArrayList<SequenceNumber>();
		list.add(sn);
		list.add(sn);

		when(sequentNumberRepo.get("SG", "HK")).thenReturn(list);

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

	@Test
	public void shouldThrowException() {
		SequenceNumber sn = mock(SequenceNumber.class);
		when(sequentNumberRepo.get(anyString())).thenReturn(Collections.singletonList(sn));
		when(sequentNumberRepo.save(eq(sn))).thenThrow(ConcurrentModificationException.class);
		service.getSequenceNumber("HK");
		verify(sequentNumberRepo, times(3)).save(sn);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldReturn1() {
		final SequenceNumber sn = mock(SequenceNumber.class);
		Key<SequenceNumber> key = mock(Key.class);
		when(sequentNumberRepo.get(anyString())).thenReturn(Collections.emptyList());
		when(sequentNumberRepo.save(any(SequenceNumber.class))).thenReturn(key);

		int result = service.getSequenceNumber("HK");

		assertThat(result, is(equalTo(1)));
		verify(sequentNumberRepo, times(1)).save(any(SequenceNumber.class));
	}

	@Test
	public void testResetIndiaSchedule() {

		Calendar today = Calendar.getInstance();

		Date nextExecutionTime = getNextExecutionTime(today, "0 30 11 1 * ?");

		Calendar result = Calendar.getInstance();
		result.setTime(nextExecutionTime);

		assertEquals(1, result.get(Calendar.DAY_OF_MONTH));
		assertEquals(11, result.get(Calendar.HOUR_OF_DAY));
		assertEquals(30, result.get(Calendar.MINUTE));
	}

	@Test
	public void testResetSgHkSchedule() {

		Calendar today = Calendar.getInstance();
		Date nextExecutionTime = getNextExecutionTime(today, "0 0 9 1 * ?");

		Calendar result = Calendar.getInstance();
		result.setTime(nextExecutionTime);

		assertEquals(1, result.get(Calendar.DAY_OF_MONTH));
		assertEquals(9, result.get(Calendar.HOUR_OF_DAY));

	}

	private Date getNextExecutionTime(Calendar today, String schedule) {

		CronTrigger trigger = new CronTrigger(schedule);

		final Date firstOfMonth = today.getTime();

		return trigger.nextExecutionTime(new TriggerContext() {

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
	}
}
