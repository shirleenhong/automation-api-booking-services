package com.cwt.bpg.cbt.exchange.order;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.model.BaseExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.EoStatus;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrderSearchParam;
import com.cwt.bpg.cbt.exchange.order.model.india.IndiaExchangeOrder;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;
import com.mongodb.WriteResult;

@Repository
public class ExchangeOrderRepository {

	@Autowired
	private MorphiaComponent morphia;

	private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeOrderRepository.class);
	private static final String EO_NUMBER = "eoNumber";
	private static final String RECORD_LOCATOR = "recordLocator";
	private static final String CREATE_DATETIME = "createDateTime";

	public String save(BaseExchangeOrder eo) {
		Key<BaseExchangeOrder> savedEoKey = morphia.getDatastore().save(eo);
		LOGGER.info("Save: Exchange order, [{}]", eo.getEoNumber());

		return savedEoKey.getId().toString();
	}

	public BaseExchangeOrder getExchangeOrder(String countryCode, String eoNumber) {

		if (Country.INDIA.getCode().equalsIgnoreCase(countryCode)) {
			return getIndiaExchangeOrder(eoNumber);
		}
		else {
			return getExchangeOrder(eoNumber);
		}
	}

	public List<? extends BaseExchangeOrder> getExchangeOrderByRecordLocator(String countryCode, String recordLocator) {

	    if (Country.INDIA.getCode().equalsIgnoreCase(countryCode)) {
            return getIndiaExchangeOrderByRecordLocator(recordLocator);
        }
        else {
            return getByRecordLocator(recordLocator);
        }
    }

	public ExchangeOrder getExchangeOrder(String eoNumber) {
		return morphia.getDatastore().createQuery(ExchangeOrder.class)
			.field(EO_NUMBER)
			.equal(eoNumber).get();
	}

    public IndiaExchangeOrder getIndiaExchangeOrder(String eoNumber) {
        return morphia.getDatastore().createQuery(IndiaExchangeOrder.class)
                .field(EO_NUMBER)
                .equal(eoNumber).get();
    }

	public List<ExchangeOrder> getByRecordLocator(String recordLocator) {
		return morphia.getDatastore().createQuery(ExchangeOrder.class)
				.field(RECORD_LOCATOR)
				.equal(recordLocator)
				.order(Sort.descending(CREATE_DATETIME))
				.asList();
	}

    public List<IndiaExchangeOrder> getIndiaExchangeOrderByRecordLocator(String recordLocator) {
        return morphia.getDatastore().createQuery(IndiaExchangeOrder.class)
                .field(RECORD_LOCATOR)
                .equal(recordLocator)
                .order(Sort.descending(CREATE_DATETIME))
                .asList();
    }

	public List<ExchangeOrder> getExchangeOrderNoRecordLocator() {

		Query<ExchangeOrder> query = morphia.getDatastore()
				.createQuery(ExchangeOrder.class);

		Instant today = Instant.now();
		Instant yesterday = today.minus(1, ChronoUnit.DAYS);
		
		query.field(CREATE_DATETIME).lessThanOrEq(yesterday);
		query.field(RECORD_LOCATOR).doesNotExist();

		return query.asList();
	}

	public String remove(ExchangeOrder exchangeOrder) {
		final Datastore datastore = morphia.getDatastore();

		WriteResult deleteResult = datastore.delete(exchangeOrder);

		LoggerFactory.getLogger(ExchangeOrderRepository.class).info("Delete Result: {}",
				deleteResult);

		return deleteResult.getN() > 0 ? exchangeOrder.getEoNumber() : "";
	}

	public List<ExchangeOrder> search(final ExchangeOrderSearchParam param)
    {
        final Query<ExchangeOrder> query = morphia.getDatastore().createQuery(ExchangeOrder.class);
        if (StringUtils.isNotBlank(param.getEoNumber()))
        {
            query.field(EO_NUMBER).equal(param.getEoNumber());
        }
        if (StringUtils.isNotBlank(param.getVendor().getCode()))
        {
            query.field("vendor.code").equal(param.getVendor().getCode());
        }
        if (StringUtils.isNotBlank(param.getCountryCode()))
        {
            query.field("countryCode").equal(param.getCountryCode());
        }
        if (StringUtils.isNotBlank(param.getVendor().getRaiseType()))
        {
            query.field("vendor.raiseType").equal(param.getVendor().getRaiseType());
        }
        if (param.getStatus() != null)
        {
            query.field("status").equal(param.getStatus());
        }
        if (StringUtils.isNotBlank(param.getProductCode()))
        {
            query.field("productCode").equal(param.getProductCode());
        }
        if (StringUtils.isNotBlank(param.getRecordLocator()))
        {
            query.field(RECORD_LOCATOR).equal(param.getRecordLocator());
        }
        if (param.getStartCreationDate() != null && param.getEndCreationDate() != null)
        {
            query.and(
                    query.criteria(CREATE_DATETIME).greaterThanOrEq(param.getStartCreationDate()),
                    query.criteria(CREATE_DATETIME).lessThanOrEq(param.getEndCreationDate()));
        }
        else if (param.getStartCreationDate() != null)
        {
            query.field(CREATE_DATETIME).greaterThanOrEq(param.getStartCreationDate());
        }
        else if (param.getEndCreationDate() != null)
        {
            query.field(CREATE_DATETIME).lessThanOrEq(param.getEndCreationDate());
        }
        query.order(Sort.descending(CREATE_DATETIME));

        final FindOptions options = new FindOptions();
        if (param.getSize() != null) {
            options.limit(param.getSize());
        }
        else {
            options.limit(300);
        }
        
        return query.asList(options);
    }

    public boolean updateFinance(ExchangeOrder param)
    {
        final Query<ExchangeOrder> query = morphia.getDatastore().createQuery(ExchangeOrder.class);
        query.field(EO_NUMBER).equal(param.getEoNumber());

        final UpdateOperations<ExchangeOrder> ops = morphia.getDatastore().createUpdateOperations(ExchangeOrder.class);

        if (param.getStatus() != null) {
            ops.set("status", param.getStatus());
        }

        if (param.getLastUpdatedByUser() != null) {
            ops.set("lastUpdatedByUser", param.getLastUpdatedByUser());
        }

        ops.set("updateDateTime", Optional.ofNullable(param.getUpdateDateTime()).orElse(Instant.now()));

        if (param.getRaiseCheque() != null) {
            ops.set("raiseCheque", param.getRaiseCheque());
        }
        
        if(EoStatus.COMPLETED.equals(param.getStatus()) && param.getCompleteDateTime() == null) {
        	ops.set("completeDateTime", Instant.now());
        }

		final UpdateResults result = morphia.getDatastore().update(query, ops);
		return result.getUpdatedExisting();
	}
}
