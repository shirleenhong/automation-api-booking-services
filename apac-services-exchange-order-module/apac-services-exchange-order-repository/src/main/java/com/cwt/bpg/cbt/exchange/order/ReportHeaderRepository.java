package com.cwt.bpg.cbt.exchange.order;

import com.cwt.bpg.cbt.exchange.order.model.ReportHeader;
import org.springframework.stereotype.Repository;

@Repository
public class ReportHeaderRepository extends CommonRepository<ReportHeader, String>{

    public static final String KEY_COLUMN = "companyName";

    public ReportHeaderRepository() {
        super(ReportHeader.class, KEY_COLUMN);
    }
}
