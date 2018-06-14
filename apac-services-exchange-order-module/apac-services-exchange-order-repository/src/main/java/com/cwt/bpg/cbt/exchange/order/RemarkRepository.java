package com.cwt.bpg.cbt.exchange.order;

import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;
import com.cwt.bpg.cbt.exchange.order.model.Remark;
import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class RemarkRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemarkRepository.class);

    @Autowired
    private MorphiaComponent morphia;

    public List<Remark> getRemarks(String productType, String remarkType) {
        List<Remark> remarks =  morphia.getDatastore().createQuery(Remark.class)
                .field("productType")
                .equal(productType)
                .field("remarkType")
                .equal(remarkType).asList();
        return remarks;
    }
}
