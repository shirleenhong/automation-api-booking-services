package com.cwt.bpg.cbt.exchange.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.model.HkSgProductList;
import com.cwt.bpg.cbt.exchange.order.model.InProductList;

@Component
public class ProductRepositoryFactory {

    @Autowired
    private ProductRepository<InProductList> indiaRepository;

    @Autowired
    private ProductRepository<HkSgProductList> hkSgRepository;

    
	@SuppressWarnings("rawtypes")
	public ProductRepository getProductRepository(String countryCode) {
		return Country.INDIA.getCode().equalsIgnoreCase(countryCode) ? indiaRepository
				: hkSgRepository;
	}
}
