package com.cwt.bpg.cbt.exchange.order.calculator;

import com.cwt.bpg.cbt.exchange.order.model.FeesBreakdown;
import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;
import com.cwt.bpg.cbt.exchange.order.model.OtherServiceFeesInput;

public interface Calculator {

	FeesBreakdown calculateFee(OtherServiceFeesInput input, MerchantFee merchantFee);

}
