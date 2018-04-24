package com.cwt.bpg.cbt.services.rest;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class AppInfoResourceTest {


	@Test
	public void test() throws IOException {
		AppInfoResource resource = new AppInfoResource();
		Map<String, String> appInfo = resource.appInfo();
		assertEquals(2, appInfo.size());
	}
}
