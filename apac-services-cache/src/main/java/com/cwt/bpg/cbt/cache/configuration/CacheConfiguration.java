package com.cwt.bpg.cbt.cache.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@EnableCaching
public class CacheConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(CacheConfiguration.class);

	private static final String[] cacheNames = new String[] {"products",
			"merchant-fees",
			"insurance-types",
			"airline-rules",
			"clients",
			"client-gst-info",
			"airports",
			"remarks",
			"exchange-orders",
			"report-headers",
			"air-transactions",
			"obt-list",
	        "room-types",
            "reason-codes",
            "car-vendors",
            "air-contracts",
            "air-misc-info",
			"airlines",
			"agent",
			"codif"};

	@Bean
	public CacheManager cacheManager() {
		return new ConcurrentMapCacheManager(cacheNames);
	}

	@CacheEvict(allEntries = true,
			cacheNames = {"products",
					"merchant-fees",
					"insurance-types",
					"airline-rules",
					"clients",
					"airports",
					"remarks",
					"exchange-orders",
					"report-headers",
					"air-transactions",
					"obt-list",
                    "room-types",
                    "reason-codes",
                    "car-vendors",
                    "air-contracts",
                    "air-misc-info",
					"airlines",
					"agent",
					"codif"})
	
	@Scheduled(cron = "${com.cwt.cache.cron.expression}")
	public void evictAllCache() {
		logger.info("Cache Evicted!");
	}
}
