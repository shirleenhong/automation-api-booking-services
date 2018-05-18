package com.cwt.bpg.cbt.exchange.order;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.AirlineRule;

public class AirlineRuleRepositoryTest
{
    @Test
    public void shouldCreateInsuranceRepository()
    {
        AirlineRuleRepository repo = new AirlineRuleRepository();
        
        repo.identity((i) -> {
        	assertThat(i[0], is(equalTo(AirlineRule.class)));
        	assertThat(i[1], is(equalTo(AirlineRuleRepository.KEY_COLUMN)));
        });
    }

}
