package com.cwt.bpg.cbt.exchange.order;

import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.util.Optional;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.exception.ExchangeOrderNoContentException;
import com.cwt.bpg.cbt.exchange.order.model.BaseExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.FopTypes;
import com.cwt.bpg.cbt.exchange.order.model.FormOfPayment;
import com.cwt.bpg.cbt.exchange.order.model.Vendor;
import com.cwt.bpg.cbt.exchange.order.model.india.BaseVendor;
import com.cwt.bpg.cbt.exchange.order.model.india.IndiaExchangeOrder;
import com.cwt.bpg.cbt.utils.ObjectModifier;

@Service
public class ExchangeOrderUpdateService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeOrderService.class);

    @Autowired
    private ExchangeOrderRepository exchangeOrderRepo;

    @Autowired
    private ExchangeOrderAmountScaler exchangeOrderAmountScaler;
    
    
//    BaseExchangeOrder update(BaseExchangeOrder exchangeOrder) throws ExchangeOrderNoContentException {
//        final String eoNumber = exchangeOrder.getEoNumber();
//        Optional<BaseExchangeOrder> isEoExist = Optional.ofNullable(exchangeOrderRepo.getExchangeOrder(exchangeOrder.getCountryCode(),eoNumber));
//
//        BaseExchangeOrder existingExchangeOrder = isEoExist
//                .orElseThrow(() -> new ExchangeOrderNoContentException(
//                        "Exchange order number not found: [ " + eoNumber + " ]"));
//
//        LOGGER.info("Existing Exchange order number: {} with country code {}",
//                existingExchangeOrder.getEoNumber(),
//                existingExchangeOrder.getCountryCode());
//        FormOfPayment fop = getFormOfPayment(exchangeOrder);
//        FormOfPayment existfop = getFormOfPayment(existingExchangeOrder);
//        if (exchangeOrder.getServiceInfo().getFormOfPayment().getCreditCard() != null) {
//            ObjectModifier.modifyTargetObject(exchangeOrder.getServiceInfo().getFormOfPayment().getCreditCard(),
//                    existingExchangeOrder.getServiceInfo().getFormOfPayment().getCreditCard());
//            exchangeOrder.getServiceInfo().getFormOfPayment().setCreditCard(null);
//        }
//
//		if (exchangeOrder.getVendor() != null) {
//			ObjectModifier.modifyTargetObject(exchangeOrder.getVendor(),
//					existingExchangeOrder.getVendor());
//			exchangeOrder.setVendor(null);
//		}
//
//        exchangeOrder.setUpdateDateTime(Instant.now());
//        ObjectModifier.modifyTargetObject(exchangeOrder, existingExchangeOrder);
//
//        if(existingExchangeOrder.getServiceInfo().getFormOfPayment().getFopType() != null &&
//                existingExchangeOrder.getServiceInfo().getFormOfPayment().getFopType() == FopTypes.INVOICE){
//            existingExchangeOrder.getServiceInfo().getFormOfPayment().setCreditCard(null);
//        }
//
//        exchangeOrderAmountScaler.scale(existingExchangeOrder);
//        String updatedEoNumber = exchangeOrderRepo.save(existingExchangeOrder);
//        
//        return exchangeOrderRepo.getExchangeOrder(updatedEoNumber);
//    }
//    
//    private FormOfPayment getFormOfPayment(BaseExchangeOrder exchangeOrder){
//    	
//    	if (exchangeOrder.getCountryCode().equals("IN")) {
//			return ((IndiaExchangeOrder) exchangeOrder).getServiceInfo().getFormOfPayment();
//		}
//		else {
//			return ((ExchangeOrder) exchangeOrder).getServiceInfo().getFormOfPayment();
//		}
//    	
//    }
    
    

    ExchangeOrder update(ExchangeOrder exchangeOrder) throws ExchangeOrderNoContentException {
        final String eoNumber = exchangeOrder.getEoNumber();
        Optional<ExchangeOrder> isEoExist = Optional.ofNullable(exchangeOrderRepo.getExchangeOrder(eoNumber));

        ExchangeOrder existingExchangeOrder = isEoExist
                .orElseThrow(() -> new ExchangeOrderNoContentException(
                        "Exchange order number not found: [ " + eoNumber + " ]"));

        LOGGER.info("Existing Exchange order number: {} with country code {}",
                existingExchangeOrder.getEoNumber(),
                existingExchangeOrder.getCountryCode());
  
        if (exchangeOrder.getServiceInfo().getFormOfPayment().getCreditCard() != null) {
            ObjectModifier.modifyTargetObject(exchangeOrder.getServiceInfo().getFormOfPayment().getCreditCard(),
                    existingExchangeOrder.getServiceInfo().getFormOfPayment().getCreditCard());
            exchangeOrder.getServiceInfo().getFormOfPayment().setCreditCard(null);
        }

		if (exchangeOrder.getVendor() != null) {
			ObjectModifier.modifyTargetObject(exchangeOrder.getVendor(),
					existingExchangeOrder.getVendor());
			exchangeOrder.setVendor(null);
		}

        exchangeOrder.setUpdateDateTime(Instant.now());
        ObjectModifier.modifyTargetObject(exchangeOrder, existingExchangeOrder);

        if(existingExchangeOrder.getServiceInfo().getFormOfPayment().getFopType() != null &&
                existingExchangeOrder.getServiceInfo().getFormOfPayment().getFopType() == FopTypes.INVOICE){
            existingExchangeOrder.getServiceInfo().getFormOfPayment().setCreditCard(null);
        }

        exchangeOrderAmountScaler.scale(existingExchangeOrder);
        String updatedEoNumber = exchangeOrderRepo.save(existingExchangeOrder);
        
        return exchangeOrderRepo.getExchangeOrder(updatedEoNumber);
    }
//    
//    IndiaExchangeOrder update(IndiaExchangeOrder exchangeOrder) throws ExchangeOrderNoContentException {
//        final String eoNumber = exchangeOrder.getEoNumber();
//        Optional<IndiaExchangeOrder> isEoExist = Optional.ofNullable(exchangeOrderRepo.getIndiaExchangeOrder(eoNumber));
//
//        IndiaExchangeOrder existingExchangeOrder = isEoExist
//                .orElseThrow(() -> new ExchangeOrderNoContentException(
//                        "Exchange order number not found: [ " + eoNumber + " ]"));
//
//        LOGGER.info("Existing Exchange order number: {} with country code {}",
//                existingExchangeOrder.getEoNumber(),
//                existingExchangeOrder.getCountryCode());
//  
//        if (exchangeOrder.getServiceInfo().getFormOfPayment().getCreditCard() != null) {
//            ObjectModifier.modifyTargetObject(exchangeOrder.getServiceInfo().getFormOfPayment().getCreditCard(),
//                    existingExchangeOrder.getServiceInfo().getFormOfPayment().getCreditCard());
//            exchangeOrder.getServiceInfo().getFormOfPayment().setCreditCard(null);
//        }
//
//		if (exchangeOrder.getVendor() != null) {
//			ObjectModifier.modifyTargetObject(exchangeOrder.getVendor(),
//					existingExchangeOrder.getVendor());
//			exchangeOrder.setVendor(null);
//		}
//
//        exchangeOrder.setUpdateDateTime(Instant.now());
//        ObjectModifier.modifyTargetObject(exchangeOrder, existingExchangeOrder);
//
//        if(existingExchangeOrder.getServiceInfo().getFormOfPayment().getFopType() != null &&
//                existingExchangeOrder.getServiceInfo().getFormOfPayment().getFopType() == FopTypes.INVOICE){
//            existingExchangeOrder.getServiceInfo().getFormOfPayment().setCreditCard(null);
//        }
//
//        exchangeOrderAmountScaler.scale(existingExchangeOrder);
//        String updatedEoNumber = exchangeOrderRepo.save(existingExchangeOrder);
//        
//        return exchangeOrderRepo.getIndiaExchangeOrder(updatedEoNumber);
//    }
}
