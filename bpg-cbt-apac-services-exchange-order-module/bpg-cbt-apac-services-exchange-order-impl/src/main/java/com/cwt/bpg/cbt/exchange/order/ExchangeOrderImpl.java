package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.exchange.order.model.Product;

@Service
public class ExchangeOrderImpl implements ExchangeOrderApi {
	
	@Override
	public List<Product> getProducts(String countryCode) {
		return null;
	}

}
