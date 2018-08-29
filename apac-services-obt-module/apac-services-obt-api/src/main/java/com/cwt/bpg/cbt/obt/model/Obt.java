package com.cwt.bpg.cbt.obt.model;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

public class Obt implements Serializable {

    private static final long serialVersionUID = 4124056458110125375L;

	@NotEmpty
	@ApiModelProperty(required = true)
	private String code;

    private String name;

    private String agentAssistedCode;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAgentAssistedCode() {
		return agentAssistedCode;
	}

	public void setAgentAssistedCode(String agentAssistedCode) {
		this.agentAssistedCode = agentAssistedCode;
	}
}
