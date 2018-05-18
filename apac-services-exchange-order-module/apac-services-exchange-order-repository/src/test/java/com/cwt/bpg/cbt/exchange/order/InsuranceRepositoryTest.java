package com.cwt.bpg.cbt.exchange.order;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.Insurance;

public class InsuranceRepositoryTest
{
    @Test
    public void shouldCreateInsuranceRepository()
    {
        InsuranceRepository repo = new InsuranceRepository();

        assertThat(repo.getKeyColumn(), is(equalTo(InsuranceRepository.KEY_COLUMN)));
        assertThat(repo.getTypeClass(), is(equalTo(Insurance.class)));
    }
}
