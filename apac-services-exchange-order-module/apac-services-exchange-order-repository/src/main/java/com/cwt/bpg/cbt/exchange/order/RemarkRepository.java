package com.cwt.bpg.cbt.exchange.order;

import com.cwt.bpg.cbt.exchange.order.model.Remark;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RemarkRepository extends CommonRepository<Remark, ObjectId>{
    public static final String KEY_COLUMN = "id";

    public RemarkRepository() {
        super(Remark.class, KEY_COLUMN);
    }

    public List<Remark> getRemarks(String countryCode, String productType, String remarkType) {
        List<Remark> remarks =  morphia.getDatastore().createQuery(Remark.class)
        		.order("text")
                .field("countryCode")
                .equal(countryCode)
                .field("productType")
                .equal(productType)
                .field("remarkType")
                .equal(remarkType).asList();
        return remarks;
    }

}
