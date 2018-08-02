package com.cwt.bpg.cbt.exchange.order.utils;

import com.cwt.bpg.cbt.exchange.order.model.BaseExchangeOrder;
import com.cwt.bpg.cbt.utils.ObjectModifier;

public class ExchangeOrderObjectModifier {

	public static void modifyTargetObject(BaseExchangeOrder source, BaseExchangeOrder target) {
        if (source.getServiceInfo().getFormOfPayment().getCreditCard() != null) {
            ObjectModifier.modifyTargetObject(source.getServiceInfo().getFormOfPayment().getCreditCard(),
                    target.getServiceInfo().getFormOfPayment().getCreditCard());
            source.getServiceInfo().getFormOfPayment().setCreditCard(null);
        }

        if (source.getVendor() != null) {
            ObjectModifier.modifyTargetObject(source.getVendor(),
                    target.getVendor());
            source.setVendor(null);
        }

        ObjectModifier.modifyTargetObject(source, target);
	}
}
