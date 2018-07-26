package com.cwt.bpg.cbt.exchange.order;

import static org.apache.commons.lang.StringUtils.leftPad;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.Client;
import com.mongodb.WriteResult;

@Repository
class ClientRepository extends CommonRepository<Client, Integer> {
	
	private static final String ID = "clientId";

	ClientRepository() {
		super(Client.class, ID);
	}

	Client getClient(String clientAccountNumber) {
		return morphia.getDatastore().createQuery(Client.class)
			.field("clientAccountNumber")
			.equal(leftPad(clientAccountNumber, 10, '0')).get();
	}

    String remove(String clientAccountNumber) {
        final Datastore datastore = morphia.getDatastore();
        final Query<Client> query = datastore.createQuery(Client.class).field("clientAccountNumber").equal(clientAccountNumber);

        WriteResult deleteResult = datastore.delete(query);

        LoggerFactory.getLogger(ClientRepository.class).info("Delete Result: {}", deleteResult);

        return deleteResult.getUpsertedId().toString();
    }
}
