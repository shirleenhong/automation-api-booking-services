
package com.cwt.bpg.cbt.exchange.order.model;

import com.cwt.bpg.cbt.exchange.order.model.deserializer.DateDeserializer;
import com.cwt.bpg.cbt.exchange.order.model.serializer.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.Instant;

public class AdditionalInfo implements Serializable {

    private static final long serialVersionUID = -8218995505606306771L;

    private String description;

	private String btaDescription;

    @ApiModelProperty(value = "Date in UTC", example = "2008-05-29T00:00:00.000Z")
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    private Instant date;

    private String touchLevel;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBtaDescription() {
        return btaDescription;
    }

    public void setBtaDescription(String btaDescription) {
        this.btaDescription = btaDescription;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getTouchLevel()
    {
        return touchLevel;
    }

    public void setTouchLevel(String touchLevel)
    {
        this.touchLevel = touchLevel;
    }
}
