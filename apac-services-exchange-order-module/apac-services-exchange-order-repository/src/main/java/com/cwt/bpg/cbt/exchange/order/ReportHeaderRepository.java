package com.cwt.bpg.cbt.exchange.order;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.ReportHeader;
import com.cwt.bpg.cbt.repository.CommonRepository;

@Repository
public class ReportHeaderRepository extends CommonRepository<ReportHeader, String>{

    public static final String KEY_COLUMN = "countryCode";

    public ReportHeaderRepository() {
        super(ReportHeader.class, KEY_COLUMN);
    }

    public ReportHeader get(String countryCode){
        return morphia.getDatastore().createQuery(ReportHeader.class)
                .field("countryCode")
                .equalIgnoreCase(countryCode).get();
    }

    public String remove(String countryCode) {
        final Datastore datastore = morphia.getDatastore();
        final Query<ReportHeader> query =  datastore.createQuery(ReportHeader.class)
                .field("countryCode")
                .equalIgnoreCase(countryCode);

        datastore.delete(query);
        return countryCode;
    }

    public ReportHeader put(ReportHeader reportHeader){
        morphia.getDatastore().save(reportHeader);
        return reportHeader;
    }
}
