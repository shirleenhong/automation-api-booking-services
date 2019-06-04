package com.cwt.bpg.cbt.client.gst.model;

class ValidationConstants {

    static final String GSTIN_REGEX = "^[A-Za-z0-9]*$";
    static final String GSTIN_EMPTY_ERROR_MSG = "gstin is required";
    static final String GSTIN_FORMAT_ERROR_MSG = "gstin should be alphanumeric";
    static final String GSTIN_LENGTH_ERROR_MSG = "gstin should be 15 characters";
    static final int GSTIN_LENGTH = 15;

    static final String CLIENT_EMPTY_ERROR_MSG = "client is required";

    static final String CLIENT_ENTITY_NAME_EMPTY_ERROR_MSG = "clientEntityName is required";

    static final String BUSINESS_PHONE_NUM_EMPTY_ERROR_MSG = "businessPhoneNumber is required";
    static final String BUSINESS_PHONE_NUM_REGEX = "^[0-9]*$";
    static final String BUSINESS_PHONE_NUM_FORMAT_ERROR_MSG = "businessPhoneNumber should be numeric";

    static final String BUSINESS_EMAIL_EMPTY_ERROR_MSG = "businessEmailAddress is required";
    static final String BUSINESS_EMAIL_FORMAT_ERROR_MSG = "businessEmailAddress format is invalid";
    static final String BUSINESS_EMAIL_LENGTH_ERROR_MSG = "businessEmailAddress should be at most 35 characters";
    static final int BUSINESS_EMAIL_ADDRESS_MAX_LENGTH = 35;

    static final String ADDRESS_LINE1_EMPTY_ERROR_MSG = "entityAddressLine1 is required";
    static final String ADDRESS_LINE1_LENGTH_ERROR_MSG = "entityAddressLine1 should be at most 35 characters";
    static final int ADDRESS_LINE1_MAX_LENGTH = 35;

    static final String ADDRESS_LINE2_LENGTH_ERROR_MSG = "entityAddressLine2 should be at most 25 characters";
    static final int ADDRESS_LINE2_MAX_LENGTH = 25;

    static final String POSTAL_CODE_EMPTY_ERROR_MSG = "postalCode is required";
    static final String POSTAL_CODE_LENGTH_ERROR_MSG = "postalCode should be at most 17 characters";
    static final int POSTAL_CODE_MAX_LENGTH = 17;

    static final String CITY_EMPTY_ERROR_MSG = "city is required";
    static final String CITY_LENGTH_ERROR_MSG = "city should be at most 25 characters";
    static final int CITY_MAX_LENGTH = 25;

    static final String STATE_EMPTY_ERROR_MSG = "state is required";
    static final String STATE_LENGTH_ERROR_MSG = "state should be at most 25 characters";
    static final int STATE_MAX_LENGTH = 25;


    private ValidationConstants() {
        //prevent instantiation of this class
    }
}
