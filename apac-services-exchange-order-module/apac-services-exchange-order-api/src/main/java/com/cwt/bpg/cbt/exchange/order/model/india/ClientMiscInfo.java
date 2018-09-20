package com.cwt.bpg.cbt.exchange.order.model.india;

import java.io.Serializable;

public class ClientMiscInfo implements Serializable {

    private static final long serialVersionUID = -9097849686464242534L;

    private String description;

    private String value;

    private String sample;

    private String length;

    private String dataType;

    private String gdsFormat;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getGdsFormat() {
        return gdsFormat;
    }

    public void setGdsFormat(String gdsFormat) {
        this.gdsFormat = gdsFormat;
    }
}
