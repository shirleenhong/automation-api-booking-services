package com.cwt.bpg.cbt.tpromigration.csv.converter;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.cwt.bpg.cbt.exchange.order.model.india.AirMiscInfo;

public class AirMiscInfoConverter implements Converter<Map<String, String>, AirMiscInfo>
{

    @Override
    public void convert(final Map<String, String> source, List<AirMiscInfo> results)
    {
        final String cdrOutputValue = StringUtils.trim(source.get("CDROutput"));
        if (StringUtils.isNotBlank(cdrOutputValue))
        {
            final String[] cdrOutputs = cdrOutputValue.split(",");
            for (final String cdrOutput : cdrOutputs)
            {
                final AirMiscInfo airMiscInfo = new AirMiscInfo();
                airMiscInfo.setGdsFormat(cdrOutput);

                airMiscInfo.setDescription(StringUtils.trim(source.get("Description")));
                airMiscInfo.setDataType(StringUtils.trim(source.get("DataType")));
                airMiscInfo.setSample(StringUtils.trim(source.get("SampleValue")));
                airMiscInfo.setMin(StringUtils.trim(source.get("Min")));
                airMiscInfo.setMax(StringUtils.trim(source.get("Max")));
                airMiscInfo.setMandatory(StringUtils.trim(source.get("Mandatory")));
                airMiscInfo.setReportingListId(StringUtils.trim(source.get("ReportingListID")));
                airMiscInfo.setClientId(StringUtils.trim(source.get("ClientID")));
                airMiscInfo.setClientAccountNumber(StringUtils.trim(source.get("CN")));
                airMiscInfo.setReportingFieldTypeId(StringUtils.trim(source.get("FieldType")));

                results.add(airMiscInfo);
            }
        }
    }
}
