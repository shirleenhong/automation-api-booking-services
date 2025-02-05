package com.cwt.bpg.cbt.air.transaction;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.air.transaction.model.AirTransaction;
import com.cwt.bpg.cbt.air.transaction.model.AirTransactionInput;
import com.cwt.bpg.cbt.repository.CommonRepository;

@Repository
public class AirTransactionRepository extends CommonRepository<AirTransaction, ObjectId>{

	private static final String ID = "id";
	private static final String  GROUP_ID = "groupId";
	private static final String COUNTRY_CODE = "countryCode";
	private static final String AIRLINE_CODE = "airlineCode";
	private static final String CC_VENDOR_CODE = "ccVendorCode";
	private static final String BOOKING_CLASSES = "bookingClasses";
	
	public AirTransactionRepository() {
		super(AirTransaction.class, ID);
	}
	
	public List<AirTransaction> getAirTransactions(AirTransactionInput params) {
		
		final Query<AirTransaction> query = morphia.getDatastore().createQuery(AirTransaction.class);
		query.field(GROUP_ID).equal(params.getGroupId());
		
		if(StringUtils.isNotBlank(params.getCountryCode())) {
			query.field(COUNTRY_CODE).equalIgnoreCase(params.getCountryCode());
		}
		
		if(StringUtils.isNotBlank(params.getAirlineCode())) {
			query.field(AIRLINE_CODE).equalIgnoreCase(params.getAirlineCode());
		}
		if(StringUtils.isNotBlank(params.getCcVendorCode())) {
			query.field(CC_VENDOR_CODE).equalIgnoreCase(params.getCcVendorCode());
		}
		if (params.getBookingClasses()!=null && !params.getBookingClasses().isEmpty()) {
			query.or(query.criteria(BOOKING_CLASSES).doesNotExist(),
                    query.criteria(BOOKING_CLASSES).hasAnyOf(params.getBookingClasses()));
		}
		
		return query.asList();	
	}
}
