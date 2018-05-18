package com.cwt.bpg.cbt.exchange.order;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.cwt.bpg.cbt.exchange.order.model.Client;

public class ClientPricingRepositoryTest {

    @Test
    public void shouldCreateInsuranceRepository()
    {
        ClientPricingRepository repo = new ClientPricingRepository();

        assertThat(repo.getKeyColumn(), is(equalTo("Key")));
        assertThat(repo.getTypeClass(), is(equalTo(Client .class)));
    }

}
