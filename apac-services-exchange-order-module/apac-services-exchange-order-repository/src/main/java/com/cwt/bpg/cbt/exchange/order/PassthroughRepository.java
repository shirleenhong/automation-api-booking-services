package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.PassthroughInput;
import com.cwt.bpg.cbt.exchange.order.model.Passthrough;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;

@Repository
public class PassthroughRepository {

	@Autowired
	private MorphiaComponent morphia;
	
	private static final String AIRLINE_CODE = "airlineCode";
	private static final String CC_VENDOR_CODE = "ccVendorCode";
	private static final String BOOKING_CLASS = "bookingClass";
	private static final String COUNTRY_CODE = "countryCode";
	private static final String CLIENT_ACCT_NUM = "clientAccountNumber";
	
	public List<Passthrough> getPassthrough(PassthroughInput params) {
		
		final Query<Passthrough> query = morphia.getDatastore().createQuery(Passthrough.class);
		
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
