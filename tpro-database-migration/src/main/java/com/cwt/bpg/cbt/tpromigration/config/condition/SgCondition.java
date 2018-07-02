package com.cwt.bpg.cbt.tpromigration.config.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.stereotype.Component;

@Component
public class SgCondition  implements Condition {

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		String countryCode = context.getEnvironment().getProperty("country.code");
		return countryCode.equalsIgnoreCase("SG");
	}
}
