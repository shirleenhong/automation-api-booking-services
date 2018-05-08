package com.cwt.bpg.cbt.cache.rest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

public class ApplicationCacheResourceTest {
	
	@Mock
	private CacheManager cacheManager;

	@InjectMocks
	private ApplicationCacheResource cacheResource = new ApplicationCacheResource();
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	@SuppressWarnings("rawtypes")
	public void canListCacheNames() {
		Cache cache = Mockito.mock(Cache.class);
		Mockito.when(cacheManager.getCacheNames()).thenReturn(Arrays.asList("kernel-cache"));
		Mockito.when(cacheManager.getCache(Mockito.anyString())).thenReturn(cache);
		Mockito.when(cache.getNativeCache()).thenReturn(new ConcurrentHashMap<>());
		
		List list = cacheResource.list();
		
		verify(cacheManager, times(1)).getCacheNames();
		verify(cacheManager, times(1)).getCache(Mockito.anyString());
		verify(cache, times(1)).getNativeCache();
	}
	
//	@Test
//	@SuppressWarnings("rawtypes")
//	public void canFetchCache() {
//		Cache cache = Mockito.mock(Cache.class);
//		Mockito.when(cacheManager.getCache(Mockito.anyString())).thenReturn(cache);
//		Mockito.when(cache.getNativeCache()).thenReturn(new ConcurrentHashMap<>());
//		
//		List list = cacheResource.fetch("products-cache");
//		
//		verify(cacheManager, times(1)).getCache(Mockito.anyString());
//		verify(cache, times(1)).getNativeCache();
//	}
	
	@Test
	@SuppressWarnings("rawtypes")
	public void canEvictCache() {
		Cache cache = Mockito.mock(Cache.class);
		Mockito.when(cacheManager.getCache(Mockito.anyString())).thenReturn(cache);
		
		List list = cacheResource.evict("products-cache");
		
		verify(cacheManager, times(1)).getCache(Mockito.anyString());
	}
}
