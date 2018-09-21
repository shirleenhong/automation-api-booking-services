package com.cwt.bpg.cbt.exchange.order.model.india;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class IndiaMiscInfo implements Serializable {

	private static final long serialVersionUID = 4914530217842938604L;

	private BigDecimal referenceFare;

	private BigDecimal lowFare;

	private String classOfServices;

	private String lowFareCarrier;

	private String bookingAction;

	private String realisedSavingsCode;

	private String missedSavingsCode;

	private String declinedAirline1;

	private BigDecimal declinedFare1;

	private String declinedAirline2;

	private BigDecimal declinedFare2;

	private List<ClientMiscInfo> clientMI;

	public BigDecimal getReferenceFare() {
		return referenceFare;
	}

	public void setReferenceFare(BigDecimal referenceFare) {
		this.referenceFare = referenceFare;
	}

	public BigDecimal getLowFare() {
		return lowFare;
	}

	public void setLowFare(BigDecimal lowFare) {
		this.lowFare = lowFare;
	}

	public String getClassOfServices() {
		return classOfServices;
	}

	public void setClassOfServices(String classOfServices) {
		this.classOfServices = classOfServices;
	}

	public String getLowFareCarrier() {
		return lowFareCarrier;
	}

	public void setLowFareCarrier(String lowFareCarrier) {
		this.lowFareCarrier = lowFareCarrier;
	}

	public String getBookingAction() {
		return bookingAction;
	}

	public void setBookingAction(String bookingAction) {
		this.bookingAction = bookingAction;
	}

	public String getRealisedSavingsCode() {
		return realisedSavingsCode;
	}

	public void setRealisedSavingsCode(String realisedSavingsCode) {
		this.realisedSavingsCode = realisedSavingsCode;
	}

	public String getMissedSavingsCode() {
		return missedSavingsCode;
	}

	public void setMissedSavingsCode(String missedSavingsCode) {
		this.missedSavingsCode = missedSavingsCode;
	}

	public String getDeclinedAirline1() {
		return declinedAirline1;
	}

	public void setDeclinedAirline1(String declinedAirline1) {
		this.declinedAirline1 = declinedAirline1;
	}

	public BigDecimal getDeclinedFare1() {
		return declinedFare1;
	}

	public void setDeclinedFare1(BigDecimal declinedFare1) {
		this.declinedFare1 = declinedFare1;
	}

	public String getDeclinedAirline2() {
		return declinedAirline2;
	}

	public void setDeclinedAirline2(String declinedAirline2) {
		this.declinedAirline2 = declinedAirline2;
	}

	public BigDecimal getDeclinedFare2() {
		return declinedFare2;
	}

	public void setDeclinedFare2(BigDecimal declinedFare2) {
		this.declinedFare2 = declinedFare2;
	}

    public List<ClientMiscInfo> getClientMI() {
        return clientMI;
    }

    public void setClientMI(List<ClientMiscInfo> clientMI) {
        this.clientMI = clientMI;
    }
}
