package com.cwt.bpg.cbt.exchange.order.model;

import java.io.Serializable;

public class Ticket implements Serializable {

    private static final long serialVersionUID = 3383920831129457090L;

    private String ticketType;

    private String airlineCode;

    private String ticketNumber;

    private String connector;

    private String exchangeTicketNumber;

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getConnector() {
        return connector;
    }

    public void setConnector(String connector) {
        this.connector = connector;
    }

    public String getExchangeTicketNumber() {
        return exchangeTicketNumber;
    }

    public void setExchangeTicketNumber(String exchangeTicketNumber) {
        this.exchangeTicketNumber = exchangeTicketNumber;
    }
}
