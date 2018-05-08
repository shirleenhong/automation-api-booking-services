package com.cwt.bpg.cbt.cache.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cwt.bpg.cbt.documentation.annotation.Internal;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.Api;


@RestController
@Api(tags = "Cache Information")
@Internal
public class ApplicationCacheResource {

	@Autowired
	private CacheManager cacheManager;

	@GetMapping(path = "/caches", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@SuppressWarnings( { "unchecked", "rawtypes" })
	public List<CacheStatistics> list() {
		final List<CacheStatistics> result = new ArrayList<>();
		final Collection<String> cacheNames = cacheManager.getCacheNames();
		
		for (final String cacheName : cacheNames) {
			final Cache cache = cacheManager.getCache(cacheName);
			final ConcurrentHashMap<Object, Object> concurrentMap = (ConcurrentHashMap<Object, Object>) cache
					.getNativeCache();
			
			final int size = concurrentMap.size();
			result.add(new CacheStatistics(cacheName, size));
		}
		return result;
	}

	@GetMapping(path = "/caches/{cacheName}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<CacheResult> fetch(@PathVariable("cacheName") String cacheName) {
		final List<CacheResult> result = new ArrayList<>();
		final Cache cache = cacheManager.getCache(cacheName);

		if (cache != null) {
			final ConcurrentMap<Object, Object> concurrentMap = (ConcurrentMap<Object, Object>) cache
					.getNativeCache();
			
			concurrentMap.forEach((k, v) -> result.add(new CacheResult(k, v)));
		}

		return result;
	}
	
	@GetMapping(path = "/caches/evict/{cacheName}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<CacheResult> evict(@PathVariable("cacheName") String cacheName) {
		final List<CacheResult> result = new ArrayList<>();
		final Cache cache = cacheManager.getCache(cacheName);

		if (cache != null) {
			cache.clear();
			result.add(new CacheResult("evict", "completed!"));
		}

		return result;
	}
	
	private class CacheResult {
		@JsonIgnore
		private Object key;
		private Object result;

		public CacheResult(Object key, Object result) {
			super();
			this.key = key;
			this.result = result;
		}

		public Object getKey() {
			return key;
		}

		public Object getResult() {
			return result;
		}

	}

	private class CacheStatistics {
		private String cacheName;
		private int size;

		public CacheStatistics(String cacheName, int size) {
			super();
			this.cacheName = cacheName;
			this.size = size;
		}

		public String getCacheName() {
			return cacheName;
		}

		public int getSize() {
			return size;
		}

	}

}
