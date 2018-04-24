package com.cwt.bpg.cbt.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.security.repository.TokenRepository;

@Service
public class TokenService {

	@Autowired 
	private TokenRepository tokenRepo;
	
	public boolean isTokenExist(String token) {
		
		return tokenRepo.getToken(token) != null;
	}

}
