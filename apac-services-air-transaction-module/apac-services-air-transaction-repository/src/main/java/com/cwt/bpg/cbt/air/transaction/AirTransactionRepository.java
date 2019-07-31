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
	private static final String COUNTRY_CODE = "countryCode";
	private static final String AIRLINE_CODE = "airlineCode";
	private static final String CC_VENDOR_CODE = "ccVendorCode";
	private static final String BOOKING_CLASSES = "bookingClasses";
	private static final String CLIENT_ACCT_NUM = "clientAccountNumber";
	
	public AirTransactionRepository() {
		super(AirTransaction.class, ID);
	}
	
	public List<AirTransaction> getAirTransactions(AirTransactionInput params) {
		
		final Query<AirTransaction> query = morphia.getDatastore().createQuery(AirTransaction.class);
		
		if(StringUtils.isNotBlank(params.getCountryCode())) {
			query.field(COUNTRY_CODE).equalIgnoreCase(params.getCountryCode());
		}
		
		if(StringUtils.isNotBlank(params.getAirlineCode())) {
			query.field(AIRLINE_CODE).equal(params.getAirlineCode());
		}
		if(StringUtils.isNotBlank(params.getCcVendorCode())) {
			query.field(CC_VENDOR_CODE).equal(params.getCcVendorCode());
		}
		if (params.getBookingClasses()!=null && !params.getBookingClasses().isEmpty()) {
			query.or(query.criteria(BOOKING_CLASSES).doesNotExist(),
                    query.criteria(BOOKING_CLASSES).hasAnyOf(params.getBookingClasses()));
		}
		
		if(StringUtils.isNotBlank(params.getClientAccountNumber())) {
			query.field(CLIENT_ACCT_NUM).equal(StringUtils.stripStart(params.getClientAccountNumber(),"0"));
		}else {
			query.field(CLIENT_ACCT_NUM).doesNotExist();
		}
		
		return query.asList();	
	}
}
