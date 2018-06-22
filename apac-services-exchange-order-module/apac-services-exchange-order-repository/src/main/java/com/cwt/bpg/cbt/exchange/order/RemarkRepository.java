package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.Remark;

@Repository
public class RemarkRepository extends CommonRepository<Remark, ObjectId>{
    private static final String KEY_COLUMN = "id";

    public RemarkRepository() {
        super(Remark.class, KEY_COLUMN);
    }

    public List<Remark> getRemarks(String countryCode, String productType, String remarkType) {
        return morphia.getDatastore().createQuery(Remark.class)
                .field("countryCode")
                .equal(countryCode)
                .field("productType")
                .equal(productType)
                .field("remarkType")
                .equal(remarkType)
                .order("text").asList();
    }

}
