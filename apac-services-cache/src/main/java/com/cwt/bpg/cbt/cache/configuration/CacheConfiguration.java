package com.cwt.bpg.cbt.cache.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class CacheConfiguration {
	
    private static final Logger logger = LoggerFactory.getLogger(CacheConfiguration.class);
	
	private final static String[] cacheNames = new String[] { "products", "merchant-fee", "currency-rounding-rule" };
	
	@Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(cacheNames);
    }
	
	@CacheEvict(allEntries = true, cacheNames = { "products", "merchant-fee", "currency-rounding-rule" })
	@Scheduled(cron = "0 0 0,12 * * *")
	public void evictAllCache() {
		logger.info("Cache Evicted!");
	}
}
