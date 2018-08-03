package com.cwt.bpg.cbt.exchange.order.utils;

import java.util.Optional;

import com.cwt.bpg.cbt.exchange.order.model.*;
import com.cwt.bpg.cbt.exchange.order.model.india.IndiaExchangeOrder;
import com.cwt.bpg.cbt.utils.ObjectModifier;

public class ExchangeOrderObjectModifier {

    private ExchangeOrderObjectModifier() {}

    public static void modifyTargetObject(BaseExchangeOrder source, BaseExchangeOrder target) {
        modifyTargetCreditCard(source, target);

        modifyTargetVendor(source, target);

        ObjectModifier.modifyTargetObject(source, target);
	}

    private static void modifyTargetCreditCard(BaseExchangeOrder source, BaseExchangeOrder target) {
        BaseServiceInfo serviceInfo = source.getServiceInfo();
        Optional<CreditCard> creditCardOptional = Optional.ofNullable(serviceInfo)
                .map(BaseServiceInfo::getFormOfPayment)
                .map(FormOfPayment::getCreditCard);

        creditCardOptional.ifPresent(creditCard -> {
            ObjectModifier.modifyTargetObject(creditCard,
                    target.getServiceInfo().getFormOfPayment().getCreditCard());
            serviceInfo.getFormOfPayment().setCreditCard(null);
        });
    }

    private static void modifyTargetVendor(BaseExchangeOrder source, BaseExchangeOrder target) {
        if (source.getVendor() != null) {
            ObjectModifier.modifyTargetObject(source.getVendor(), target.getVendor());
            if (source instanceof IndiaExchangeOrder) {
                ((IndiaExchangeOrder) source).setVendor(null);
            }
            else {
                ((ExchangeOrder) source).setVendor(null);
            }
        }
    }
}
