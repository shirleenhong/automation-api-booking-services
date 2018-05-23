package com.cwt.bpg.cbt.exchange.order;

import com.cwt.bpg.cbt.exchange.order.model.City;
import org.springframework.stereotype.Repository;

@Repository
public class CityRepository extends CommonRepository<City,String> {

	public static final String KEY_COLUMN = "code";

	public CityRepository() {
		super(City.class, KEY_COLUMN);
	}
}
