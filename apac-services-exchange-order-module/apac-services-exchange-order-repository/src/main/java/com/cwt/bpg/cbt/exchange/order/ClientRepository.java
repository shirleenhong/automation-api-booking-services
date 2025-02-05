package com.cwt.bpg.cbt.exchange.order;

import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.Client;
import com.cwt.bpg.cbt.repository.CommonRepository;
import com.mongodb.WriteResult;

@Repository
public class ClientRepository extends CommonRepository<Client, Integer> {
	
	private static final String ID = "clientAccountNumber";

	public ClientRepository() {
		super(Client.class, ID);
	}

	public Client getClient(String clientAccountNumber) {
		return morphia.getDatastore().createQuery(Client.class)
			.field("clientAccountNumber")
			.equal(StringUtils.stripStart(clientAccountNumber,"0")).get();
	}

    public String remove(String clientAccountNumber) {
        final Datastore datastore = morphia.getDatastore();
        final Query<Client> query = datastore.createQuery(Client.class).field("clientAccountNumber").equal(StringUtils.stripStart(clientAccountNumber,"0"));

        WriteResult deleteResult = datastore.delete(query);

        LoggerFactory.getLogger(ClientRepository.class).info("Delete Result: {}", deleteResult);

        return deleteResult.getN() > 0 ? clientAccountNumber : "";
    }
}
