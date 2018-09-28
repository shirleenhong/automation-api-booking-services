package com.cwt.bpg.cbt.exchange.order;

import com.cwt.bpg.cbt.exchange.order.model.AirContract;
import com.cwt.bpg.cbt.repository.CommonRepository;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AirContractRepository extends CommonRepository<AirContract, ObjectId>{

	private static final String ID = "id";
	private static final String CLIENT_ACCOUNT_NUMBER = "clientAccountNumber";
	private static final String COUNTRY_CODE = "countryCode";
	private static final String AIRLINE_CODE = "airlineCode";
	private static final String FOR_CODE = "fopCode";

	public AirContractRepository() {
		super(AirContract.class, ID);
	}

	public AirContract get(String countryCode, String airlineCode, String clientAccountNumber) {

		final Query<AirContract> query = morphia.getDatastore().createQuery(AirContract.class);
		query.field(COUNTRY_CODE).equal(countryCode);
		query.field(AIRLINE_CODE).equal(airlineCode);
		query.field(CLIENT_ACCOUNT_NUMBER).equal(clientAccountNumber);

		return query.get();
	}
}
