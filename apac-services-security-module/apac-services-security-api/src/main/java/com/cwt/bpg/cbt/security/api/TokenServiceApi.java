package com.cwt.bpg.cbt.security.api;

@FunctionalInterface
public interface TokenServiceApi {
	
	boolean isTokenExist(String token);

}
