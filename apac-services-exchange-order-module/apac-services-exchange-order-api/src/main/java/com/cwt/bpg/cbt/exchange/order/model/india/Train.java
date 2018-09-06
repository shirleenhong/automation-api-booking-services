package com.cwt.bpg.cbt.exchange.order.model.india;

import java.io.Serializable;
import java.util.List;

public class Train implements Serializable {

	private static final long serialVersionUID = -3988004129798285136L;

	private List<TrainSegment> segments;
	
	private DOMCustomFields domCustomFields;
	
	private TransChargesCustomFields transChargesCustomFields;
	
	public DOMCustomFields getDomCustomFields() {
		return domCustomFields;
	}

	public void setDomCustomFields(DOMCustomFields domCustomFields) {
		this.domCustomFields = domCustomFields;
	}

	public TransChargesCustomFields getTransChargesCustomFields() {
		return transChargesCustomFields;
	}

	public void setTransChargesCustomFields(
			TransChargesCustomFields transChargesCustomFields) {
		this.transChargesCustomFields = transChargesCustomFields;
	}

	public List<TrainSegment> getSegments() {
		return segments;
	}

	public void setSegments(List<TrainSegment> segments) {
		this.segments = segments;
	}
}
