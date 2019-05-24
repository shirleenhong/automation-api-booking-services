package com.cwt.bpg.cbt.client.gst.model;

import java.util.List;

public class ClientGstInfoResponse {

    private ClientGstInfo clientGstInfo;
    private List<String> airlineCodes;

    public ClientGstInfo getClientGstInfo() {
        return clientGstInfo;
    }

    public void setClientGstInfo(ClientGstInfo clientGstInfo) {
        this.clientGstInfo = clientGstInfo;
    }

    public List<String> getAirlineCodes() {
        return airlineCodes;
    }

    public void setAirlineCodes(List<String> airlineCodes) {
        this.airlineCodes = airlineCodes;
    }
}
