package com.cwt.bpg.cbt.exchange.order;

import com.cwt.bpg.cbt.exchange.order.model.Remark;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RemarkRepository extends CommonRepository<Remark, String>{
    public static final String KEY_COLUMN = "text";

    public RemarkRepository() {
        super(Remark.class, KEY_COLUMN);
    }

    public List<Remark> getRemarks(String productType, String remarkType) {
        List<Remark> remarks =  morphia.getDatastore().createQuery(Remark.class)
                .field("productType")
                .equal(productType)
                .field("remarkType")
                .equal(remarkType).asList();
        return remarks;
    }

}
