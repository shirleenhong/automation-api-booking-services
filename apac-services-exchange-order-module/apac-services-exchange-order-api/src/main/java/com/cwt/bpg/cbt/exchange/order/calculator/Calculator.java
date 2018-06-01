package com.cwt.bpg.cbt.exchange.order.calculator;

import com.cwt.bpg.cbt.exchange.order.model.FeesInput;
import com.cwt.bpg.cbt.exchange.order.model.MerchantFee;

@FunctionalInterface
public interface Calculator<T, I extends FeesInput> {

	T calculate(I input, MerchantFee merchantFee);

}
