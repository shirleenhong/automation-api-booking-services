package com.cwt.bpg.cbt.exchange.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.Client;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;

@Repository
public class ClientRepository {
	
	@Autowired
	private MorphiaComponent morphia;

	public Client getClient(String profileName) {
		return morphia.getDatastore().createQuery(Client.class)
			.field("profileName")
			.equal(profileName).get();
	}

	public Client getClient(int id) {
		return morphia.getDatastore().createQuery(Client.class)
				.field("clientId")
				.equal(id)
				.get();
	}
}
