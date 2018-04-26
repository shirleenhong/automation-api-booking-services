package com.cwt.bpg.cbt.cache.configuration;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.cache.CacheManager;

public class CacheConfigurationTest {

	private CacheConfiguration cacheConfig = new CacheConfiguration();
	
	@Test
	public void canCreateCachConfig() {
		CacheManager cacheManager = cacheConfig.cacheManager();
		assertNotNull(cacheManager);
	}
}
