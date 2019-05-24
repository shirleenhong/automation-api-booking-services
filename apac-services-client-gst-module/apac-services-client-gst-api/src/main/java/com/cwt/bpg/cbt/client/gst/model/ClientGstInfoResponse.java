package com.cwt.bpg.cbt.client.gst.model;

import java.util.Set;

public class ClientGstInfoResponse {

    private ClientGstInfo clientGstInfo;
    private Set<String> airlineCodes;

    public ClientGstInfo getClientGstInfo() {
        return clientGstInfo;
    }

    public void setClientGstInfo(ClientGstInfo clientGstInfo) {
        this.clientGstInfo = clientGstInfo;
    }

    public Set<String> getAirlineCodes() {
        return airlineCodes;
    }

    public void setAirlineCodes(Set<String> airlineCodes) {
        this.airlineCodes = airlineCodes;
    }
}
