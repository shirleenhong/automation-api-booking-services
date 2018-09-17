package com.cwt.bpg.cbt.security.service;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cwt.bpg.cbt.security.api.model.Token;
import com.cwt.bpg.cbt.security.repository.TokenRepository;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@Service
public class TokenService {

    @Value("${security.secret.key}")
    private String secretKey;

    @Autowired
    private TokenRepository tokenRepo;

    public boolean isTokenExist(String tokenKey) {
        Token token = tokenRepo.getToken(tokenKey);
        return token != null && parseToken(token);
    }

    private boolean parseToken(Token token) {
        try {
            Jwts.parser().setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token.getKey());
            return true;
        }
        catch (JwtException e) {
            return false;
        }
    }
}
