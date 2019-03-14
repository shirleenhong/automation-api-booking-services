package com.cwt.bpg.cbt.agent.model;

public class AgentInfoKey {
    private String uid;
    private String countryCode;

    public AgentInfoKey() {

    }

    public AgentInfoKey(String uid, String countryCode) {
        this.uid = uid;
        this.countryCode = countryCode;
    }

    public String getUid() {
        return uid;
    }

    public String getCountryCode() {
        return countryCode;
    }
}
