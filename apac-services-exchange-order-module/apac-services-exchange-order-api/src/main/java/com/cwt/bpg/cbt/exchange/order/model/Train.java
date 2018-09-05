package com.cwt.bpg.cbt.exchange.order.model;

import com.cwt.bpg.cbt.exchange.order.model.india.TrainSegment;

import java.io.Serializable;
import java.util.List;

public class Train implements Serializable {

	private static final long serialVersionUID = -3988004129798285136L;

	private List<TrainSegment> segments;

	public List<TrainSegment> getSegments() {
		return segments;
	}

	public void setSegments(List<TrainSegment> segments) {
		this.segments = segments;
	}
}
