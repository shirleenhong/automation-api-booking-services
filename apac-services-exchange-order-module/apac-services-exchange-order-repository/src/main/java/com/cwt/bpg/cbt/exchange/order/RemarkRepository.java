package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.Remark;
import com.cwt.bpg.cbt.repository.CommonRepository;

@Repository
public class RemarkRepository extends CommonRepository<Remark, ObjectId>{
    private static final String KEY_COLUMN = "id";

    public RemarkRepository() {
        super(Remark.class, KEY_COLUMN);
    }

    public List<Remark> getRemarks(String countryCode, String productType, String remarkType) {
        return morphia.getDatastore().createQuery(Remark.class)
                .field("countryCode")
                .equalIgnoreCase(countryCode)
                .field("productType")
                .equalIgnoreCase(productType)
                .field("remarkType")
                .equalIgnoreCase(remarkType)
                .order("text").asList();
    }

}
