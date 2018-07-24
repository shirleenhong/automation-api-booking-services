package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.Sort;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrderSearchParam;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;

@Repository
public class ExchangeOrderRepository {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeOrderRepository.class);
	
	@Autowired
	private MorphiaComponent morphia;
	
	public ExchangeOrder save(ExchangeOrder eo) {		
		morphia.getDatastore().save(eo);
		LOGGER.info("Save: Exchange order, [{}]", eo.getEoNumber());
		return eo;
	}
	
	public ExchangeOrder getExchangeOrder(String eoNumber) {
		return morphia.getDatastore().createQuery(ExchangeOrder.class)
			.field("eoNumber")
			.equal(eoNumber).get();
	}

	public List<ExchangeOrder> getByRecordLocator(String recordLocator) {
		return morphia.getDatastore().createQuery(ExchangeOrder.class)
				.field("recordLocator")
				.equal(recordLocator)
				.order(Sort.descending("createDateTime"))
				.asList();
	}
	
	public List<ExchangeOrder> search(final ExchangeOrderSearchParam param)
    {
        final Query<ExchangeOrder> query = morphia.getDatastore().createQuery(ExchangeOrder.class);
        if (StringUtils.isNotBlank(param.getEoNumber()))
        {
            query.field("eoNumber").equal(param.getEoNumber());
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
        if (StringUtils.isNotBlank(param.getRecordLocator()))
        {
            query.field("recordLocator").equal(param.getRecordLocator());
        }
        if (param.getStartCreationDate() != null && param.getEndCreationDate() != null)
        {
            query.and(
                    query.criteria("createDateTime").greaterThanOrEq(param.getStartCreationDate()),
                    query.criteria("createDateTime").lessThanOrEq(param.getEndCreationDate()));
        }
        else if (param.getStartCreationDate() != null)
        {
            query.field("createDateTime").greaterThanOrEq(param.getStartCreationDate());
        }
        else if (param.getEndCreationDate() != null)
        {
            query.field("createDateTime").lessThanOrEq(param.getEndCreationDate());
        }
        query.order(Sort.descending("createDateTime"));

        final FindOptions options = new FindOptions();
        options.limit(100);
        return query.asList(options);
    }

    public boolean updateFinance(ExchangeOrder param)
    {
        final Query<ExchangeOrder> query = morphia.getDatastore().createQuery(ExchangeOrder.class);
        query.field("eoNumber").equal(param.getEoNumber());

        final UpdateOperations<ExchangeOrder> ops = morphia.getDatastore().createUpdateOperations(ExchangeOrder.class);
        ops.set("status", param.getStatus());
        ops.set("lastUpdatedByUser", param.getLastUpdatedByUser());
        ops.set("updateDateTime", param.getUpdateDateTime());
        ops.set("raiseCheque", param.getRaiseCheque());

        final UpdateResults result = morphia.getDatastore().update(query, ops);
        return result.getUpdatedExisting();
    }
}
