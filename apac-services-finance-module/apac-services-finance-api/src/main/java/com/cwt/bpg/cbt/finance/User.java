package com.cwt.bpg.cbt.finance;

import org.hibernate.validator.constraints.SafeHtml;
import org.mongodb.morphia.annotations.*;

@Entity(value = "users", noClassnameStored = true)
@Indexes(@Index(fields = @Field("uid")))
public class User {

    @Id
    @SafeHtml
    private String uid;

    private String[] countryCodes;

    private String[] roles;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String[] getCountryCodes() {
        return countryCodes;
    }

    public void setCountryCodes(String[] countryCodes) {
        this.countryCodes = countryCodes;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }
}
