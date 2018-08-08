package com.cwt.bpg.cbt.exchange.order;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.cwt.bpg.cbt.calculator.config.ScaleConfig;
import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.model.ExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.ServiceInfo;
import com.cwt.bpg.cbt.exchange.order.model.india.IndiaExchangeOrder;
import com.cwt.bpg.cbt.exchange.order.model.india.IndiaServiceInfo;

public class ExchangeOrderAmountScalerTest {
	@InjectMocks
	private ExchangeOrderAmountScaler scaler;

	@Mock
	private ScaleConfig scaleConfig;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldScaleHKAmounts() {

		ExchangeOrder exchangeOrder = new ExchangeOrder();
		Mockito.when(scaleConfig.getScale(Country.HONG_KONG.getCode())).thenReturn(0);
		exchangeOrder.setCountryCode(Country.HONG_KONG.getCode());
		exchangeOrder.setServiceInfo(new ServiceInfo());
		exchangeOrder.setGstAmount(BigDecimal.valueOf(Math.random() * 100));
		exchangeOrder.setTotal(BigDecimal.valueOf(Math.random() * 100));
		exchangeOrder.getServiceInfo().setCommission(BigDecimal.valueOf(Math.random() * 100));
		exchangeOrder.getServiceInfo().setMerchantFeeAmount(BigDecimal.valueOf(Math.random() * 100));
		exchangeOrder.getServiceInfo().setNettCost(BigDecimal.valueOf(Math.random() * 100));
		exchangeOrder.getServiceInfo().setGstAmount(BigDecimal.valueOf(Math.random() * 100));
		exchangeOrder.getServiceInfo().setTax1(BigDecimal.valueOf(Math.random() * 100));
		exchangeOrder.getServiceInfo().setTax2(BigDecimal.valueOf(Math.random() * 100));
		exchangeOrder.getServiceInfo().setSellingPrice(BigDecimal.valueOf(Math.random() * 100));
		exchangeOrder.getServiceInfo().setTotalSellingPrice(BigDecimal.valueOf(Math.random() * 100));

		scaler.scale(exchangeOrder);

		assertEquals(0, exchangeOrder.getGstAmount().scale());
		assertEquals(0, exchangeOrder.getTotal().scale());
		assertEquals(0, exchangeOrder.getServiceInfo().getCommission().scale());
		assertEquals(0, exchangeOrder.getServiceInfo().getMerchantFeeAmount().scale());
		assertEquals(0, exchangeOrder.getServiceInfo().getNettCost().scale());
		assertEquals(0, exchangeOrder.getServiceInfo().getGstAmount().scale());
		assertEquals(0, exchangeOrder.getServiceInfo().getTax1().scale());
		assertEquals(0, exchangeOrder.getServiceInfo().getTax2().scale());
		assertEquals(0, exchangeOrder.getServiceInfo().getSellingPrice().scale());
		assertEquals(0, exchangeOrder.getServiceInfo().getTotalSellingPrice().scale());

	}

	@Test
	public void shouldScaleSGAmounts() {

		ExchangeOrder exchangeOrder = new ExchangeOrder();
		Mockito.when(scaleConfig.getScale(Country.SINGAPORE.getCode())).thenReturn(2);
		exchangeOrder.setCountryCode(Country.SINGAPORE.getCode());
		exchangeOrder.setServiceInfo(new ServiceInfo());
		exchangeOrder.setGstAmount(BigDecimal.valueOf(Math.random() * 100));
		exchangeOrder.setTotal(BigDecimal.valueOf(Math.random() * 100));
		exchangeOrder.getServiceInfo().setCommission(BigDecimal.valueOf(Math.random() * 100));
		exchangeOrder.getServiceInfo().setMerchantFeeAmount(BigDecimal.valueOf(Math.random() * 100));
		exchangeOrder.getServiceInfo().setNettCost(BigDecimal.valueOf(Math.random() * 100));
		exchangeOrder.getServiceInfo().setGstAmount(BigDecimal.valueOf(Math.random() * 100));
		exchangeOrder.getServiceInfo().setTax1(BigDecimal.valueOf(Math.random() * 100));
		exchangeOrder.getServiceInfo().setTax2(BigDecimal.valueOf(Math.random() * 100));
		exchangeOrder.getServiceInfo().setSellingPrice(BigDecimal.valueOf(Math.random() * 100));
		exchangeOrder.getServiceInfo().setTotalSellingPrice(BigDecimal.valueOf(Math.random() * 100));

		scaler.scale(exchangeOrder);

		assertEquals(2, exchangeOrder.getGstAmount().scale());
		assertEquals(2, exchangeOrder.getTotal().scale());
		assertEquals(2, exchangeOrder.getServiceInfo().getCommission().scale());
		assertEquals(2, exchangeOrder.getServiceInfo().getMerchantFeeAmount().scale());
		assertEquals(2, exchangeOrder.getServiceInfo().getNettCost().scale());
		assertEquals(2, exchangeOrder.getServiceInfo().getGstAmount().scale());
		assertEquals(2, exchangeOrder.getServiceInfo().getTax1().scale());
		assertEquals(2, exchangeOrder.getServiceInfo().getTax2().scale());
		assertEquals(2, exchangeOrder.getServiceInfo().getSellingPrice().scale());
		assertEquals(2, exchangeOrder.getServiceInfo().getTotalSellingPrice().scale());

	}

	@Test
	public void shouldScaleINAmounts() {

		IndiaExchangeOrder exchangeOrder = new IndiaExchangeOrder();
		Mockito.when(scaleConfig.getScale(Country.SINGAPORE.getCode())).thenReturn(2);
		exchangeOrder.setCountryCode(Country.SINGAPORE.getCode());
		exchangeOrder.setServiceInfo(new IndiaServiceInfo());
		exchangeOrder.setGstAmount(BigDecimal.valueOf(Math.random()));
		exchangeOrder.setTotal(BigDecimal.valueOf(Math.random()));
		exchangeOrder.getServiceInfo().setCommission(BigDecimal.valueOf(Math.random()));
		exchangeOrder.getServiceInfo().setMerchantFeeAmount(BigDecimal.valueOf(Math.random()));
		exchangeOrder.getServiceInfo().setNettCost(BigDecimal.valueOf(Math.random()));
		exchangeOrder.getServiceInfo().setGstAmount(BigDecimal.valueOf(Math.random()));
		exchangeOrder.getServiceInfo().setSellingPrice(BigDecimal.valueOf(Math.random()));
		exchangeOrder.getServiceInfo().setTotalSellingPrice(BigDecimal.valueOf(Math.random()));

		scaler.scale(exchangeOrder);

		assertEquals(2, exchangeOrder.getGstAmount().scale());
		assertEquals(2, exchangeOrder.getTotal().scale());
		assertEquals(2, exchangeOrder.getServiceInfo().getCommission().scale());
		assertEquals(2, exchangeOrder.getServiceInfo().getMerchantFeeAmount().scale());
		assertEquals(2, exchangeOrder.getServiceInfo().getNettCost().scale());
		assertEquals(2, exchangeOrder.getServiceInfo().getGstAmount().scale());
		assertEquals(2, exchangeOrder.getServiceInfo().getSellingPrice().scale());
		assertEquals(2, exchangeOrder.getServiceInfo().getTotalSellingPrice().scale());

	}
    
}
