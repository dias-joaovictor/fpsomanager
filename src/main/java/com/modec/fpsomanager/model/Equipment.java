package com.modec.fpsomanager.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.modec.fpsomanager.converter.EquipmentStatusConverter;

@Entity
@Table(name = "FPSO_EQUIPMENT")
public class Equipment implements Serializable {

	private static final long serialVersionUID = -5821436078724845253L;

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "code", nullable = false, unique = true)
	private String code;

	@Column(name = "vesselId", nullable = false)
	@ManyToOne
	private Vessel vessel;

	@Column(name = "equipmentStatus", nullable = false)
	@Convert(converter = EquipmentStatusConverter.class)
	private EquipmentStatus equipmentStatus;

	public Equipment() {
		super();
	}

	public Equipment(//
			final int id, //
			final String name, //
			final String code, //
			final Vessel vessel, //
			final EquipmentStatus equipmentStatus) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.vessel = vessel;
		this.equipmentStatus = equipmentStatus;
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public Vessel getVessel() {
		return this.vessel;
	}

	public void setVessel(final Vessel vessel) {
		this.vessel = vessel;
	}

	public EquipmentStatus getEquipmentStatus() {
		return this.equipmentStatus;
	}

	public void setEquipmentStatus(final EquipmentStatus equipmentStatus) {
		this.equipmentStatus = equipmentStatus;
	}

	@Override
	public String toString() {
		return String.format(//
				"Equipment [id=%s, name=%s, code=%s, vessel=%s, equipmentStatus=%s]", //
				this.id, //
				this.name, //
				this.code, //
				this.vessel, //
				this.equipmentStatus);
	}

}
