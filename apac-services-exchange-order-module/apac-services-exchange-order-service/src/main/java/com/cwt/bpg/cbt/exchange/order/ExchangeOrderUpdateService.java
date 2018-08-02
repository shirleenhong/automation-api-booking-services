package com.cwt.bpg.cbt.exchange.order;

import java.time.Instant;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderNoContentException;
import com.cwt.bpg.cbt.exchange.order.model.BaseExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.FopTypes;
import com.cwt.bpg.cbt.exchange.order.utils.ExchangeOrderObjectModifier;

@Service
public class ExchangeOrderUpdateService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeOrderService.class);

    @Autowired
    private ExchangeOrderRepository exchangeOrderRepo;

    @Autowired
    private ExchangeOrderAmountScaler exchangeOrderAmountScaler;
    
    ExchangeOrder update(BaseExchangeOrder exchangeOrder) throws ExchangeOrderNoContentException {
        final String eoNumber = exchangeOrder.getEoNumber();
        Optional<ExchangeOrder> isEoExist = Optional.ofNullable(exchangeOrderRepo.getExchangeOrder(eoNumber));

        ExchangeOrder existingExchangeOrder = isEoExist
                .orElseThrow(() -> new ExchangeOrderNoContentException(
                        "Exchange order number not found: [ " + eoNumber + " ]"));

        LOGGER.info("Existing Exchange order number: {} with country code {}",
                existingExchangeOrder.getEoNumber(),
                existingExchangeOrder.getCountryCode());

        exchangeOrder.setUpdateDateTime(Instant.now());
        ExchangeOrderObjectModifier.modifyTargetObject(exchangeOrder, existingExchangeOrder);

        if(existingExchangeOrder.getServiceInfo().getFormOfPayment().getFopType() != null &&
                existingExchangeOrder.getServiceInfo().getFormOfPayment().getFopType() == FopTypes.INVOICE){
            existingExchangeOrder.getServiceInfo().getFormOfPayment().setCreditCard(null);
        }

        exchangeOrderAmountScaler.scale(existingExchangeOrder);
        String updatedEoNumber = exchangeOrderRepo.save(existingExchangeOrder);
        
        return exchangeOrderRepo.getExchangeOrder(updatedEoNumber);
    }
}
