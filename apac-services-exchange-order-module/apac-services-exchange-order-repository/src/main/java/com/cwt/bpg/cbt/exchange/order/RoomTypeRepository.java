package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.mongodb.morphia.query.Sort;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.RoomType;

@Repository
public class RoomTypeRepository extends CommonRepository<RoomType, String>{
	
	private static final String CODE = "code";
	
	public RoomTypeRepository() {
		super(RoomType.class, CODE);
	}
	
	public List<RoomType> getRoomTypes() {
		return morphia.getDatastore().createQuery(RoomType.class).order(Sort.ascending(CODE))
				.asList();
	}

}
