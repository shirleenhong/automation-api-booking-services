package com.cwt.bpg.cbt.exchange.order.model.india;

import java.io.Serializable;
import java.util.List;

import com.cwt.bpg.cbt.exchange.order.model.ContactInfo;

public class IndiaVendor implements Serializable {

    private static final long serialVersionUID = 8214137480361551399L;

    private String name;

	private String address1;

	private String address2;

	private String city;

	private String country;

	private String contactNo;

	private String supportEmail;

	private List<ContactInfo> contactInfo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getSupportEmail() {
        return supportEmail;
    }

    public void setSupportEmail(String supportEmail) {
        this.supportEmail = supportEmail;
    }

    public List<ContactInfo> getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(List<ContactInfo> contactInfo) {
        this.contactInfo = contactInfo;
    }
}
