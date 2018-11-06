package com.cwt.bpg.cbt.exchange.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cwt.bpg.cbt.calculator.model.Country;
import com.cwt.bpg.cbt.exchange.order.model.HkSgProductList;
import com.cwt.bpg.cbt.exchange.order.model.InProductList;

@Component
public class ProductFactory {

    @Autowired
    private ProductRepository<InProductList> indiaRepository;

    @Autowired
    private ProductRepository<HkSgProductList> hkSgRepository;

    
	public ProductDao getProductRepository(String countryCode) {

		if (Country.INDIA.getCode().equalsIgnoreCase(countryCode)) {
			return indiaRepository;
		}
		return hkSgRepository;
	}
}
