package com.cwt.bpg.cbt.client.gst.model;

import java.util.List;

public class GstLookup {

    private List<ClientGstInfo> clientGstInfo;
    private List<GstAirline> gstAirlines;

    public List<ClientGstInfo> getClientGstInfo() {
        return clientGstInfo;
    }

    public void setClientGstInfo(List<ClientGstInfo> clientGstInfo) {
        this.clientGstInfo = clientGstInfo;
    }

    public List<GstAirline> getGstAirlines() {
        return gstAirlines;
    }

    public void setGstAirlines(List<GstAirline> gstAirlines) {
        this.gstAirlines = gstAirlines;
    }
}
