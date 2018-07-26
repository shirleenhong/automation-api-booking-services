package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

public class Route implements Serializable {

	private static final long serialVersionUID = 7858317666413989443L;

	private String origin;

    private String destination;

    private String description;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
