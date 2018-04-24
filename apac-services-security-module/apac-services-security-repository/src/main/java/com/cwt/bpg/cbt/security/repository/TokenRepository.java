package com.cwt.bpg.cbt.security.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cwt.bpg.cbt.mongodb.config.MorphiaComponent;
import com.cwt.bpg.cbt.security.api.model.Token;

@Repository
public class TokenRepository {
	
	@Autowired
	private MorphiaComponent morphia;

	public Token getToken(String token) {
		return morphia.getDatastore().createQuery(Token.class)
			.field("_id")
			.equal(token).get();
	}
}
