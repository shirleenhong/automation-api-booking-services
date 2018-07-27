package com.cwt.bpg.cbt.exchange.order;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.InsurancePlan;

public class InsuranceRepositoryTest {
	
	@Test
	public void shouldCreateInsuranceRepository() {
		InsurancePlanRepository repo = new InsurancePlanRepository();

		repo.identity((i) -> {
			assertThat(i[0], is(equalTo(InsurancePlan.class)));
			assertThat(i[1], is(equalTo(InsurancePlanRepository.KEY_COLUMN)));
		});
	}
}
