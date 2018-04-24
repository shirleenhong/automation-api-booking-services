package com.cwt.bpg.cbt.exchange.order;

import com.cwt.bpg.cbt.exchange.order.model.OtherServiceFeesInput;
import com.cwt.bpg.cbt.exchange.order.model.FeesBreakdown;

public interface OtherServiceFeesApi {
	
	FeesBreakdown calculateMiscFee(OtherServiceFeesInput input);

	FeesBreakdown calculateBspAirFee(OtherServiceFeesInput input);

}
