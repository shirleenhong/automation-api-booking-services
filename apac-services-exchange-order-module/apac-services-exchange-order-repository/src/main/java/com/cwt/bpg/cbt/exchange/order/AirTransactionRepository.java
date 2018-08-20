package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import com.cwt.bpg.cbt.exchange.order.model.AirTransaction;
import org.apache.commons.lang.StringUtils;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.AirTransactionInput;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;

@Repository
public class AirTransactionRepository {

	@Autowired
	private MorphiaComponent morphia;
	
	private static final String AIRLINE_CODE = "airlineCode";
	private static final String CC_VENDOR_CODE = "ccVendorCode";
	private static final String BOOKING_CLASS = "bookingClass";
	private static final String COUNTRY_CODE = "countryCode";
	private static final String CLIENT_ACCT_NUM = "clientAccountNumber";
	
	public List<AirTransaction> getAirTransaction(AirTransactionInput params) {
		
		final Query<AirTransaction> query = morphia.getDatastore().createQuery(AirTransaction.class);
		
		if(StringUtils.isNotBlank(params.getAirlineCode())) {
			query.field(AIRLINE_CODE).equal(params.getAirlineCode());
		}
		if(StringUtils.isNotBlank(params.getCcVendorCode())) {
			query.field(CC_VENDOR_CODE).equal(params.getCcVendorCode());
		}
		if(StringUtils.isNotBlank(params.getBookingClass())) {
			query.field(BOOKING_CLASS).equal(params.getBookingClass());
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
