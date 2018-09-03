package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Sort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.exchange.order.model.RoomType;

@Repository
public class RoomTypeRepository extends CommonRepository<RoomType, ObjectId>{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RoomTypeRepository.class);
	
	private static final String CODE = "code";
	
	public RoomTypeRepository() {
		super(RoomType.class, CODE);
	}
	
	public List<RoomType> getRoomTypes() {
		return morphia.getDatastore().createQuery(RoomType.class).order(Sort.ascending(CODE))
				.asList();
	}

}
