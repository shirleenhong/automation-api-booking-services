package com.cwt.bpg.cbt.tpromigration.mssqldb.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tblProductCodes database table.
 * 
 */
@Entity
@Table(name="tblProductCodes")
@NamedQuery(name="ProductCode.findAll", query="SELECT t FROM ProductCode t")
public class ProductCode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ProductCode")
	private String productCode;

	@Column(name="Description")
	private String description;

	@Column(name="EnableCCFOP")
	private Boolean enableCCFOP;

	@Column(name="FullComm")
	private Boolean fullComm;

	@Column(name="GST")
	private Integer gst;

	@Column(name="MI")
	private Boolean mi;

	@Column(name="SortKey")
	private String sortKey;

	@Column(name="TktNo")
	private Boolean tktNo;

	@Column(name="TktPrefix")
	private String tktPrefix;

	@Column(name="Type")
	private String type;

	public String getProductCode() {
		return this.productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getEnableCCFOP() {
		return this.enableCCFOP;
	}

	public void setEnableCCFOP(Boolean enableCCFOP) {
		this.enableCCFOP = enableCCFOP;
	}

	public Boolean getFullComm() {
		return this.fullComm;
	}

	public void setFullComm(Boolean fullComm) {
		this.fullComm = fullComm;
	}

	public Integer getGst() {
		return this.gst;
	}

	public void setGst(Integer gst) {
		this.gst = gst;
	}

	public Boolean getMi() {
		return this.mi;
	}

	public void setMi(Boolean mi) {
		this.mi = mi;
	}

	public String getSortKey() {
		return this.sortKey;
	}

	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}

	public Boolean getTktNo() {
		return this.tktNo;
	}

	public void setTktNo(Boolean tktNo) {
		this.tktNo = tktNo;
	}

	public String getTktPrefix() {
		return this.tktPrefix;
	}

	public void setTktPrefix(String tktPrefix) {
		this.tktPrefix = tktPrefix;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}