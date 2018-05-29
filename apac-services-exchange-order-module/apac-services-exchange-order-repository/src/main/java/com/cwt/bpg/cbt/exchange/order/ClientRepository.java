package com.cwt.bpg.cbt.exchange.order;

import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.Client;

@Repository
public class ClientRepository extends CommonRepository<Client, Integer> {
	
	private static final String _ID = "clientId";

	public ClientRepository() {
		super(Client.class, _ID);
	}

	public Client getClient(int id) {
		return morphia.getDatastore().createQuery(Client.class).field(_ID).equal(id).get();
	}
	
	public Client getClient(String profileName) {
		return morphia.getDatastore().createQuery(Client.class)
			.field("profileName")
			.equal(profileName).get();
	}
}
