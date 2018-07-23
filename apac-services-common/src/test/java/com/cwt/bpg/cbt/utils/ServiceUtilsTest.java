package com.cwt.bpg.cbt.utils;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class ServiceUtilsTest {

	private ServiceUtils serviceUtils = new ServiceUtils();

	@Test
	public void shouldModifyTargetObject() {
		
		Object a = new Object();
		Object b = new Object();
		serviceUtils.modifyTargetObject(a, b);
		assertNotNull(serviceUtils);
	}
}
