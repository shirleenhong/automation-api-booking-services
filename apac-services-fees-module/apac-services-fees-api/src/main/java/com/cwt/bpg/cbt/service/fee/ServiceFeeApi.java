package com.cwt.bpg.cbt.service.fee;

import com.cwt.bpg.cbt.service.fee.model.PriceBreakdown;
import com.cwt.bpg.cbt.service.fee.model.PriceCalculationInput;

public interface ServiceFeeApi {
	
	PriceBreakdown calculate(PriceCalculationInput input);

}
