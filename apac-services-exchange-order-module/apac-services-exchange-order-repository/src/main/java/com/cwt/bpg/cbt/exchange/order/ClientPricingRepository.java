package com.cwt.bpg.cbt.exchange.order;

import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.Client;

@Repository
public class ClientPricingRepository extends CommonRepository<Client> {
	
	public ClientPricingRepository() {
		super(Client.class, "Key");
	}

}
