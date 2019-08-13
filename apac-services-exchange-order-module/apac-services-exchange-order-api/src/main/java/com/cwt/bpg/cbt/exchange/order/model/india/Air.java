package com.cwt.bpg.cbt.exchange.order.model.india;

import java.io.Serializable;
import java.util.List;

public class Air implements Serializable {
	
	private static final long serialVersionUID = 1184885404521305510L;
	
	private List<String> airSegments;
	
	private String platingCarrier;
	
	private String fareType;

	private VirtualMiscMultipurposeDoc vmpd;

	private ConsolInfo consolInfo;

	private AirCustomFields airCustomFields;

	public List<String> getAirSegments() {
		return airSegments;
	}

	public void setAirSegments(List<String> airSegments) {
		this.airSegments = airSegments;
	}

	public String getPlatingCarrier() {
		return platingCarrier;
	}

	public void setPlatingCarrier(String platingCarrier) {
		this.platingCarrier = platingCarrier;
	}

	public String getFareType() {
		return fareType;
	}

	public void setFareType(String fareType) {
		this.fareType = fareType;
	}

	public ConsolInfo getConsolInfo() {
		return consolInfo;
	}

	public void setConsolInfo(ConsolInfo consolInfo) {
		this.consolInfo = consolInfo;
	}

    public VirtualMiscMultipurposeDoc getVmpd()
    {
        return vmpd;
    }

    public void setVmpd(VirtualMiscMultipurposeDoc vmpd)
    {
        this.vmpd = vmpd;
    }

    public AirCustomFields getAirCustomFields() {
        return airCustomFields;
    }

    public void setAirCustomFields(AirCustomFields airCustomFields) {
        this.airCustomFields = airCustomFields;
    }
}
