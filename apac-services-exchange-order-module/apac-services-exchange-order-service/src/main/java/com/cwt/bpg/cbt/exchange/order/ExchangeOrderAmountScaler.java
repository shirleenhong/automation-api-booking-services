package com.cwt.bpg.cbt.exchange.order;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.calculator.CalculatorUtils;
import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.exchange.order.model.BaseExchangeOrder;

@Component
public class ExchangeOrderAmountScaler
{

    @Autowired
    private ScaleConfig scaleConfig;

    public void scale(BaseExchangeOrder exchangeOrder) {
    	
    	int scale = scaleConfig.getScale(exchangeOrder.getCountryCode());
    	
    	scale(exchangeOrder,scale);
    	
    }

        
    private void scale(Object source, int scale) {
    	
		String[] propertyNames = getPropertyNames(source);

		for (String fieldName : propertyNames) {

			try {
				Object obj = PropertyUtils.getProperty(source, fieldName);
				
				if (obj != null) {

					if (obj instanceof BigDecimal) {
						
						PropertyUtils.setProperty(source, fieldName, CalculatorUtils.scale((BigDecimal) PropertyUtils.getProperty(source, fieldName),scale));

					}else if (obj.getClass().getName().contains("com.cwt.bpg")
							&& PropertyUtils.getProperty(source, fieldName) != null) {

						scale(PropertyUtils.getProperty(source, fieldName),scale);
					}

				}
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
    
    private String[] getPropertyNames(Object source) {
    	
		final BeanWrapper src = new BeanWrapperImpl(source);
		PropertyDescriptor[] keys = src.getPropertyDescriptors();

		Set<String> names = new HashSet<>();
		for (PropertyDescriptor key : keys) {
			names.add(key.getName());
		}
		
		String[] result = new String[names.size()];
		return names.toArray(result);
	}
}
