package com.cwt.bpg.cbt.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class ObjectModifier {

	public static void modifyTargetObject(Object source, Object target) {
		if (target == null) {
			target = new Object();
		}
		BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
	}

	private static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		PropertyDescriptor[] keys = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<>();
		for (PropertyDescriptor key : keys) {
			Object srcValue = src.getPropertyValue(key.getName());
			if (srcValue == null) {
				emptyNames.add(key.getName());
			}
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}
}
