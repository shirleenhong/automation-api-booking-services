package com.cwt.bpg.cbt.tpromigration.csv.converter;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import com.cwt.bpg.cbt.exchange.order.model.india.AirMiscInfo;

public class AirMiscInfoConverter implements Converter<Map<String, String>, AirMiscInfo>
{

    @Override
    public AirMiscInfo convert(final Map<String, String> source)
    {
        final AirMiscInfo airMiscInfo = new AirMiscInfo();
        airMiscInfo.setDescription(StringUtils.trim(source.get("Description")));
        airMiscInfo.setDataType(StringUtils.trim(source.get("DataType")));
        airMiscInfo.setSample(StringUtils.trim(source.get("SampleValue")));
        airMiscInfo.setGdsFormat(StringUtils.trim(source.get("CDROutput")));
        airMiscInfo.setMin(StringUtils.trim(source.get("Min")));
        airMiscInfo.setMax(StringUtils.trim(source.get("Max")));
        airMiscInfo.setMandatory(StringUtils.trim(source.get("Mandatory")));
        airMiscInfo.setReportingListId(StringUtils.trim(source.get("ReportingListID")));
        airMiscInfo.setClientId(StringUtils.trim(source.get("ClientID")));
        airMiscInfo.setClientAccountNumber(StringUtils.trim(source.get("CN")));
        airMiscInfo.setReportingFieldTypeId(StringUtils.trim(source.get("FieldType")));
        return airMiscInfo;
    }

}
