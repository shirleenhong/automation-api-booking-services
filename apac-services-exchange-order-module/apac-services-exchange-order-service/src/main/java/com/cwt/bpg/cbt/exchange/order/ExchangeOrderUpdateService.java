package com.cwt.bpg.cbt.exchange.order;

import java.time.Instant;
import java.util.Optional;

import com.cwt.bpg.cbt.exchange.order.model.FopTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderNoContentException;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.utils.ServiceUtils;

@Service
public class ExchangeOrderUpdateService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeOrderService.class);

    @Autowired
    private ExchangeOrderRepository exchangeOrderRepo;

    @Autowired
    private ExchangeOrderAmountScaler exchangeOrderAmountScaler;


    ExchangeOrder update(ExchangeOrder exchangeOrder) throws ExchangeOrderNoContentException {
        final String eoNumber = exchangeOrder.getEoNumber();
        Optional<ExchangeOrder> isEoExist = Optional.ofNullable(exchangeOrderRepo.getExchangeOrder(eoNumber));

        ExchangeOrder existingExchangeOrder = isEoExist
                .orElseThrow(() -> new ExchangeOrderNoContentException(
                        "Exchange order number not found: [ " + eoNumber + " ]"));

        LOGGER.info("Existing Exchange order number: {} with country code {}",
                existingExchangeOrder.getEoNumber(),
                existingExchangeOrder.getCountryCode());

        if (exchangeOrder.getHeader() != null) {
            ServiceUtils.modifyTargetObject(exchangeOrder.getHeader(),
                    existingExchangeOrder.getHeader());
            exchangeOrder.setHeader(null);
        }

        if (exchangeOrder.getCreditCard() != null) {
            ServiceUtils.modifyTargetObject(exchangeOrder.getCreditCard(),
                    existingExchangeOrder.getCreditCard());
            exchangeOrder.setCreditCard(null);
        }

		if (exchangeOrder.getVendor() != null) {
			ServiceUtils.modifyTargetObject(exchangeOrder.getVendor(),
					existingExchangeOrder.getVendor());
			exchangeOrder.setVendor(null);
		}

        exchangeOrder.setUpdateDateTime(Instant.now());
        ServiceUtils.modifyTargetObject(exchangeOrder, existingExchangeOrder);

        if(existingExchangeOrder.getFopType() != null &&
                existingExchangeOrder.getFopType().equalsIgnoreCase(FopTypes.INVOICE.getCode())){
            existingExchangeOrder.setCreditCard(null);
        }

        exchangeOrderAmountScaler.scale(existingExchangeOrder);

        return exchangeOrderRepo.save(existingExchangeOrder);
    }
}
