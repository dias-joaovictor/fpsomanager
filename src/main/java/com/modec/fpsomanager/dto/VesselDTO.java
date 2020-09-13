package com.modec.fpsomanager.dto;

import java.io.Serializable;

public class VesselDTO implements Serializable {

	private static final long serialVersionUID = 4119565125033652328L;

	private String code;

	public VesselDTO(final String code) {
		super();
		this.code = code;
	}

	public VesselDTO() {
		super();
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return String.format("VesselDTO [code=%s]", this.code);
	}

}
