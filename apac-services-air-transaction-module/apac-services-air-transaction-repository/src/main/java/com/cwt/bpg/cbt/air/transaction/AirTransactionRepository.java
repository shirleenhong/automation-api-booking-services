package com.cwt.bpg.cbt.air.transaction;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.air.transaction.model.AirTransaction;
import com.cwt.bpg.cbt.air.transaction.model.AirTransactionInput;
import com.cwt.bpg.cbt.air.transaction.model.BookingClass;

@Repository
public class AirTransactionRepository extends CommonRepository<AirTransaction, ObjectId>{

	private static final String ID = "id";
	private static final String AIRLINE_CODE = "airlineCode";
	private static final String CC_VENDOR_CODE = "ccVendorCode";
	private static final String CC_TYPE = "ccType";
	private static final String BOOKING_CLASS = "bookingClass";
	private static final String CLIENT_ACCT_NUM = "clientAccountNumber";
	private static final String BOOKING_CLASS_CODE = "code";
	
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
			
			Query<BookingClass> bookingClassQuery = morphia.getDatastore()
					.createQuery(BookingClass.class);
			bookingClassQuery.field(BOOKING_CLASS_CODE).equal(params.getBookingClass());
			bookingClassQuery.asList();

			query.or(query.criteria(BOOKING_CLASS).doesNotExist(),
					query.criteria(BOOKING_CLASS).elemMatch(bookingClassQuery));
			
		}
		if(StringUtils.isNotBlank(params.getClientAccountNumber())) {
			query.field(CLIENT_ACCT_NUM).equal(params.getClientAccountNumber());
		}
		if(StringUtils.isNotBlank(params.getCcType())) {
			query.field(CC_TYPE).equal(params.getCcType());
		}
		
		return query.asList();	
	}
}
