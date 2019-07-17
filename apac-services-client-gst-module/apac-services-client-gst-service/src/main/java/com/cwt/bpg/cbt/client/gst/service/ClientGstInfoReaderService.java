package com.cwt.bpg.cbt.client.gst.service;

import static com.cwt.bpg.cbt.client.gst.service.Constants.*;

import java.io.InputStream;
import java.util.List;

import org.springframework.util.StringUtils;

import com.cwt.bpg.cbt.client.gst.model.ClientGstInfo;
import com.cwt.bpg.cbt.client.gst.model.OrgType;

public abstract class ClientGstInfoReaderService {

	protected static final int ROWS_TO_SKIP_GST_DATA = 2;

	protected static final String NON_ALPHANUMERIC_REGEX = "[^0-9a-zA-Z]";
	protected static final String LINE_BREAK_REGEX = "\\r\\n|\\r|\\n";
	protected static final String EMPTY_STRING = "";
	protected static final String SPACE = " ";

	protected abstract List<ClientGstInfo> readFile(InputStream inputStream);

	protected boolean populateClientGstInfo(Integer rowIteration, Object row, List<ClientGstInfo> clientGstInfo) {
		if (rowIteration <= ROWS_TO_SKIP_GST_DATA) {
			return true;
		}
		ClientGstInfo info = extractFromRow(row);
		if (info.allValuesNull()) {
			return false; // stop reading excel if no values can be extracted
		} else if (!StringUtils.isEmpty(info.getGstin())) {
			clientGstInfo.add(info);
		}
		return true;
	}

	protected ClientGstInfo extractFromRow(Object row) {
		ClientGstInfo info = new ClientGstInfo();

		info.setClient(getValue(row, CLIENT_INDEX));
		info.setGstin(getValue(row, GSTIN_INDEX));
		String gstin = info.getGstin();
		if (!StringUtils.isEmpty(gstin)) {
			info.setGstin(gstin.replaceAll(NON_ALPHANUMERIC_REGEX, EMPTY_STRING));
			info.setGstin(gstin.toUpperCase());
		}
		info.setClientEntityName(getValue(row, ENTITY_NAME_INDEX));
		info.setBusinessPhoneNumber(getValue(row, BUSINESS_PHONE_INDEX));
		info.setBusinessEmailAddress(getValue(row, EMAIL_ADDRESS_INDEX));
		info.setEntityAddressLine1(getValue(row, ADDRESS_LINE_1_INDEX));
		info.setEntityAddressLine2(getValue(row, ADDRESS_LINE2_INDEX));
		info.setPostalCode(getValue(row, POSTAL_CODE_INDEX));
		info.setCity(getValue(row, CITY_INDEX));
		info.setState(getValue(row, STATE_INDEX));
		String orgType = getValue(row, ORGTYPE_INDEX);
		if (!StringUtils.isEmpty(orgType)) {
			info.setOrgType(OrgType.valueOf(orgType));
		}
		return info;
	}

	protected abstract String getValue(Object obj, int index);
}
