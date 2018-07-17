package com.cwt.bpg.cbt.exchange.order.model.converter;

import com.cwt.bpg.cbt.exchange.order.model.EoAction;
import org.mongodb.morphia.converters.SimpleValueConverter;
import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class EoActionCoverter extends TypeConverter implements SimpleValueConverter {

	public EoActionCoverter() {
		super(EoAction.class);
	}

	@Override
	public Object decode(final Class targetClass, final Object fromDBObject, final MappedField optionalExtraInfo) {
		return EoAction.getEoAction((String )fromDBObject);
	}

	@Override
	public Object encode(final Object value, final MappedField optionalExtraInfo) {
		return ((EoAction) value).toString();
	}
}
