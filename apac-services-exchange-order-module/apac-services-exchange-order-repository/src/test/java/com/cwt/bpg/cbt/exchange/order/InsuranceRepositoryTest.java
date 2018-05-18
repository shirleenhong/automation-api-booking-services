package com.cwt.bpg.cbt.exchange.order;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.Client;
import com.cwt.bpg.cbt.exchange.order.model.Insurance;

public class InsuranceRepositoryTest {
	
	@Test
	public void shouldCreateInsuranceRepository() {
		InsuranceRepository repo = new InsuranceRepository();

		repo.identity((i) -> {
			assertThat(i[0], is(equalTo(Insurance.class)));
			assertThat(i[1], is(equalTo(InsuranceRepository.KEY_COLUMN)));
		});
	}
}
