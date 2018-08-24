package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.AirTransaction;
import com.cwt.bpg.cbt.exchange.order.model.AirTransactionInput;
import com.mongodb.BasicDBObjectBuilder;

@Repository
public class AirTransactionRepository extends CommonRepository<AirTransaction, ObjectId>{

	private static final String ID = "id";
	private static final String AIRLINE_CODE = "airlineCode";
	private static final String CC_VENDOR_CODE = "ccVendorCode";
	private static final String BOOKING_CLASS = "bookingClass";
	private static final String COUNTRY_CODE = "countryCode";
	private static final String CLIENT_ACCT_NUM = "clientAccountNumber";
	private static final String BOOKING_CLASS_CODE = "code";
	private static final String ELEM_MATCH = " elem";
	
	public AirTransactionRepository() {
		super(AirTransaction.class, ID);
	}
	
	public List<AirTransaction> getAirTransactions(AirTransactionInput params) {
		
		final Query<AirTransaction> query = morphia.getDatastore().createQuery(AirTransaction.class);
		
		if(StringUtils.isNotBlank(params.getAirlineCode())) {
			query.field(AIRLINE_CODE).equal(params.getAirlineCode());
		}
		if(StringUtils.isNotBlank(params.getCcVendorCode())) {
			query.field(CC_VENDOR_CODE).equal(params.getCcVendorCode());
		}
		if (StringUtils.isNotBlank(params.getBookingClass())) {
			query.filter(BOOKING_CLASS + ELEM_MATCH, BasicDBObjectBuilder
					.start(BOOKING_CLASS_CODE, params.getBookingClass()).get());
		}
		if(StringUtils.isNotBlank(params.getCountryCode())) {
			query.field(COUNTRY_CODE).equal(params.getCountryCode());
		}
		if(StringUtils.isNotBlank(params.getClientAccountNumber())) {
			query.field(CLIENT_ACCT_NUM).equal(params.getClientAccountNumber());
		}
		
		return query.asList();	
	}
}
