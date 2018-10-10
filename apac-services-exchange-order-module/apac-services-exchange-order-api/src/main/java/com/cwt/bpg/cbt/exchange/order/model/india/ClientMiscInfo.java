package com.cwt.bpg.cbt.exchange.order.model.india;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

import com.cwt.bpg.cbt.utils.ObjectIdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class ClientMiscInfo implements Serializable {

    private static final long serialVersionUID = -9097849686464242534L;

	@Id
	@JsonSerialize(using = ObjectIdSerializer.class)
	private ObjectId id;
	
    private String description;

    private String value;

    private String sample;

    private String dataType;

    private String gdsFormat;

    private String min;

    private String max;

    private String mandatory;

    public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

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

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMandatory() {
        return mandatory;
    }

    public void setMandatory(String mandatory) {
        this.mandatory = mandatory;
    }
}
