package com.cwt.bpg.cbt.air.transaction.model.converter;

import org.mongodb.morphia.converters.SimpleValueConverter;
import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;

import com.cwt.bpg.cbt.air.transaction.model.PassthroughType;

public class PassthroughTypeConverter extends TypeConverter implements SimpleValueConverter
{

    public PassthroughTypeConverter()
    {
        super(PassthroughType.class);
    }

    @Override
    public Object decode(@SuppressWarnings("rawtypes") Class targetClass, Object fromDBObject, MappedField optionalExtraInfo)
    {
        if (PassthroughType.class.isAssignableFrom(targetClass))
        {
            return decodeCustom(fromDBObject);
        }
        return null;
<<<<<<< HEAD
=======

        // else
        // {
        // return super.decode(targetClass, fromDBObject, optionalExtraInfo);
        // }
>>>>>>> develop
    }

    @Override
    public Object encode(Object value, MappedField optionalExtraInfo)
    {
        if (value == null)
        {
            return null;
        }
        if (PassthroughType.class.isAssignableFrom(value.getClass()))
        {
            return encodeCustom(value);
        }
        else
        {
<<<<<<< HEAD
=======
            // return super.encode(value, optionalExtraInfo);
>>>>>>> develop
            return null;
        }
    }

    protected Object decodeCustom(Object fromDBObject)
    {
        if (fromDBObject == null)
        {
            return null;
        }
        return PassthroughType.fromString(fromDBObject.toString());
    }

    protected Object encodeCustom(Object value)
    {
        if (value == null)
        {
            return null;
        }

        final PassthroughType result = (PassthroughType) value;
        return result.getCode();
    }

    @Override
    protected boolean isSupported(Class< ? > c, MappedField optionalExtraInfo)
    {
        return PassthroughType.class.isAssignableFrom(c);
    }

}
