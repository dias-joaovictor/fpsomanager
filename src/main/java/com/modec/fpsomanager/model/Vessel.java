package com.modec.fpsomanager.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FPSO_VESSEL")
public class Vessel implements Serializable {

	private static final long serialVersionUID = 3827787788271735861L;

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "registryCode", nullable = false, unique = true)
	private String vesselRegistryCode;

	public Vessel(final int id, final String vesselRegistryCode) {
		super();
		this.id = id;
		this.vesselRegistryCode = vesselRegistryCode;
	}

	public Vessel() {
		super();
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public String getVesselRegistryCode() {
		return this.vesselRegistryCode;
	}

	public void setVesselRegistryCode(final String vesselRegistryCode) {
		this.vesselRegistryCode = vesselRegistryCode;
	}

	@Override
	public String toString() {
		return String.format("Vessel [id=%s, vesselRegistryCode=%s]", this.id, this.vesselRegistryCode);
	}

}
