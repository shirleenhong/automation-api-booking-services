package com.cwt.bpg.cbt.client.gst.repository;

import com.cwt.bpg.cbt.client.gst.model.GstAirline;
import com.cwt.bpg.cbt.repository.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public class GstAirlineRepository extends CommonRepository<GstAirline, String> {

    private static final String KEY_COLUMN = "code";

    public GstAirlineRepository() {
        super(GstAirline.class, KEY_COLUMN);
    }
}
