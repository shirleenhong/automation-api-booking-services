package com.cwt.bpg.cbt.exchange.order;

import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.Client;

@Repository
public class ClientRepository extends CommonRepository<Client, Integer> {
	
	private static final String ID = "clientId";

	public ClientRepository() {
		super(Client.class, ID);
	}

	public Client getClient(String profileName) {
		return morphia.getDatastore().createQuery(Client.class)
			.field("profileName")
			.equal(profileName).get();
	}
}
