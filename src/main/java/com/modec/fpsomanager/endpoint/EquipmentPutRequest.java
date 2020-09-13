package com.modec.fpsomanager.endpoint;

import java.io.Serializable;
import java.util.List;

public class EquipmentPutRequest implements Serializable {

	private static final long serialVersionUID = -6297561894977999584L;

	List<String> codes;

	public EquipmentPutRequest() {
		super();
	}

	public EquipmentPutRequest(final List<String> codes) {
		super();
		this.codes = codes;
	}

	public List<String> getCodes() {
		return this.codes;
	}

	public void setCodes(final List<String> codes) {
		this.codes = codes;
	}

}
