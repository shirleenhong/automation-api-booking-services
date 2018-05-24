package com.cwt.bpg.cbt.exchange.order;

import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.Client;

@Repository
public class ClientPricingRepository extends CommonRepository<Client, Integer> {

	private static final String _ID = "clientId";

	public ClientPricingRepository() {
		super(Client.class, _ID);
	}

	public Client getClient(int Id) {
		return morphia.getDatastore().createQuery(Client.class).field(_ID).equal(Id).get();
	}
}
