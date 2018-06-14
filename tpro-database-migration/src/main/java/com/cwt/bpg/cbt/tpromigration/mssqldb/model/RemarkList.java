package com.cwt.bpg.cbt.tpromigration.mssqldb.model;

import java.util.List;

public class RemarkList<Remark> {

    private String countryCode;

    private List<Remark> remarks;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public List<Remark> getRemarks() {
        return remarks;
    }

    public void setRemarks(List<Remark> remarks) {
        this.remarks = remarks;
    }
}
