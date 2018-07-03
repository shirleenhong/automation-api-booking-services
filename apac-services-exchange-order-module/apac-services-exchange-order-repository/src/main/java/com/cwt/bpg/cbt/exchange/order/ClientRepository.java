package com.cwt.bpg.cbt.exchange.order;

import static org.apache.commons.lang.StringUtils.leftPad;

import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.Client;

@Repository
public class ClientRepository extends CommonRepository<Client, Integer> {
	
	private static final String ID = "clientId";

	public ClientRepository() {
		super(Client.class, ID);
	}

	public Client getClient(String clientAccountNumber) {
		return morphia.getDatastore().createQuery(Client.class)
			.field("clientAccountNumber")
			.equal(leftPad(clientAccountNumber, 10, '0')).get();
	}
}
