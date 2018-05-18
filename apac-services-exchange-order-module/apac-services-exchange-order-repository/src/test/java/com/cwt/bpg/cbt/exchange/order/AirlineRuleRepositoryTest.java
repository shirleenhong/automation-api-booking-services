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

        assertThat(repo.getKeyColumn(), is(equalTo(AirlineRuleRepository.KEY_COLUMN)));
        assertThat(repo.getTypeClass(), is(equalTo(AirlineRule.class)));
    }

}
