package com.cwt.bpg.cbt.utils;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class ObjectModifierTest {

	private ObjectModifier objectModifier = new ObjectModifier();

	@Test
	public void shouldModifyTargetObject() {
		
		Object a = new Object();
		Object b = new Object();
		objectModifier.modifyTargetObject(a, b);
		assertNotNull(objectModifier);
	}
}
