package com.cwt.bpg.cbt.exchange.order;

import java.time.Instant;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderNoContentException;
import com.cwt.bpg.cbt.exchange.order.model.BaseExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.EoStatus;

@Service
public class ExchangeOrderUpdateService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeOrderService.class);

    @Autowired
    private ExchangeOrderRepository exchangeOrderRepo;

    BaseExchangeOrder update(BaseExchangeOrder exchangeOrder) throws ExchangeOrderNoContentException {
        final String eoNumber = exchangeOrder.getEoNumber();
        Optional<BaseExchangeOrder> isEoExist = Optional.ofNullable(exchangeOrderRepo.getExchangeOrder(exchangeOrder.getCountryCode(), eoNumber));

        BaseExchangeOrder existingExchangeOrder = isEoExist
                .orElseThrow(() -> new ExchangeOrderNoContentException(
                        "Exchange order number not found: [ " + eoNumber + " ]"));

        LOGGER.info("Existing Exchange order number: {} with country code {}",
                existingExchangeOrder.getEoNumber(),
                existingExchangeOrder.getCountryCode());

        exchangeOrder.setCreateDateTime(existingExchangeOrder.getCreateDateTime());
        exchangeOrder.setUpdateDateTime(Instant.now());
        exchangeOrder.setCompleteDateTime(existingExchangeOrder.getCompleteDateTime());

        if (EoStatus.COMPLETED.equals(exchangeOrder.getStatus()) && exchangeOrder.getCompleteDateTime() == null) {
            exchangeOrder.setCompleteDateTime(Instant.now());
        }

        String updatedEoNumber = exchangeOrderRepo.save(exchangeOrder);

        return exchangeOrderRepo.getExchangeOrder(existingExchangeOrder.getCountryCode(), updatedEoNumber);
    }
}
