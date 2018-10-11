package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.india.AirMiscInfo;
import com.cwt.bpg.cbt.repository.CommonRepository;

@Repository
public class AirMiscInfoRepository extends CommonRepository<AirMiscInfo, ObjectId> {

	private static final String ID = "id";
	private static final String CLIENT_ACCOUNT_NUMBER = "clientAccountNumber";
	private static final String REPORTING_FIELD_TYPE_ID = "reportingFieldTypeId";

	public AirMiscInfoRepository() {
		super(AirMiscInfo.class, ID);
	}

	public List<AirMiscInfo> getAirMiscInfos(String clientAccountNumber,
			List<String> reportingFieldTypeIds) {

		final Query<AirMiscInfo> query = morphia.getDatastore()
				.createQuery(AirMiscInfo.class);

		query.field(CLIENT_ACCOUNT_NUMBER).equal(clientAccountNumber);
		query.field(REPORTING_FIELD_TYPE_ID).in(reportingFieldTypeIds);

		return query.asList();
	}
}
