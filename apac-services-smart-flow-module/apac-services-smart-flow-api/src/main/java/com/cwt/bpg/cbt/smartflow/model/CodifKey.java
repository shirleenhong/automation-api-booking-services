package com.cwt.bpg.cbt.smartflow.model;

public class CodifKey {
    private String gdsPropId;
    private String keyType;

    public CodifKey() {

    }

    public CodifKey(String gdsPropId, String keyType) {
        this.gdsPropId = gdsPropId;
        this.keyType = keyType;
    }

    public String getGdsPropId() {
        return gdsPropId;
    }

    public String getKeyType() {
        return keyType;
    }
}
