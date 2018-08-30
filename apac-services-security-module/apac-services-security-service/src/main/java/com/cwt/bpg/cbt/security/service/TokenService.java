package com.cwt.bpg.cbt.security.service;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.security.api.model.Token;
import com.cwt.bpg.cbt.security.repository.TokenRepository;

import io.jsonwebtoken.Jwts;

@Service
public class TokenService {

	@Value("${security.secret.key}")
	private String secretKey;

	@Autowired
	private TokenRepository tokenRepo;

	public boolean isTokenExist(String tokenKey) {
		Token token = tokenRepo.getToken(tokenKey);
		if (token != null) {
			Jwts.parser().setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token.getKey());
			return true;
		} else {
			return false;
		}
	}

}
