package com.cwt.bpg.cbt.security.service;

import java.io.IOException;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.mongodb.config.MongoDbConnection;
import com.cwt.bpg.cbt.mongodb.config.mapper.DBObjectMapper;
import com.cwt.bpg.cbt.security.api.TokenServiceApi;
import com.cwt.bpg.cbt.security.api.model.Token;
import com.mongodb.client.FindIterable;

@Service
public class TokenService implements TokenServiceApi {

	@Autowired
	private MongoDbConnection mongoDbConnection;

	@Autowired
	private DBObjectMapper dBObjectMapper;

	@SuppressWarnings("rawtypes")
	@Override
	public boolean isTokenExist(String token) {
		
		boolean isExist = false;
		
		FindIterable iterable = mongoDbConnection.getCollection(Token.COLLECTION).find(new Document("_id",token));

		Token tokenResult = null;
		try {
			tokenResult = dBObjectMapper.mapDocumentToBean((Document) iterable.first(), Token.class);
		} catch (IOException e) {
		}
		
		if(tokenResult != null) {
			isExist = true;
		}

		return isExist;
	}

}
