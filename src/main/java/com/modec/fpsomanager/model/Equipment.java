package com.modec.fpsomanager.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import com.modec.fpsomanager.converter.EquipmentStatusConverter;
import com.modec.fpsomanager.dto.EquipmentDTO;
import com.modec.fpsomanager.exception.BusinessException;

@Entity
@Table(name = "FPSO_EQUIPMENT")
public class Equipment implements Serializable {

	private static final long serialVersionUID = -5821436078724845253L;

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name", nullable = false, length = 50)
	private String name;

	@Column(name = "code", nullable = false, unique = true, length = 20)
	private String code;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "vesselId", nullable = false)
	private Vessel vessel;

	@Column(name = "equipmentStatus", nullable = false)
	@Convert(converter = EquipmentStatusConverter.class)
	private EquipmentStatus equipmentStatus;

	@Column(name = "location", nullable = false, unique = true, length = 30)
	private String location;

	public Equipment() {
		super();
	}

	public Equipment(//
			final int id, //
			final String name, //
			final String code, //
			final Vessel vessel, //
			final EquipmentStatus equipmentStatus, //
			final String location) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.vessel = vessel;
		this.equipmentStatus = equipmentStatus;
		this.location = location;
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
				"Equipment [id=%s, name=%s, code=%s, vessel=%s, equipmentStatus=%s, location=%s]", //
				this.id, //
				this.name, //
				this.code, //
				this.vessel, //
				this.equipmentStatus, //
				this.location);
	}

	public EquipmentDTO toDTO() {
		return new EquipmentDTO(//
				this.name, //
				this.code, //
				this.vessel.toDTO(), //
				this.equipmentStatus.getDescription(), //
				this.location);
	}

	public static Equipment fromDTO(final EquipmentDTO dto) {
		validateDTO(dto);

		return newActiveEquipment(dto);
	}

	private static Equipment newActiveEquipment(final EquipmentDTO dto) {
		final Equipment equipment = new Equipment(//
				0, //
				dto.getName().toUpperCase().trim(), //
				dto.getCode().toUpperCase().trim(), //
				Vessel.fromDTO(dto.getVessel()), //
				null, //
				dto.getLocation().toUpperCase().trim());

		equipment.setEquipmentStatus(EquipmentStatus.ACTIVE);
		return equipment;
	}

	private static void validateDTO(final EquipmentDTO dto) {
		if (dto == null) {
			throw new BusinessException("Invalid Equipment");
		}

		if (StringUtils.isBlank(dto.getCode())) {
			throw new BusinessException("Invalid Equipment Code");
		}

		if (StringUtils.isBlank(dto.getName())) {
			throw new BusinessException("Invalid Equipment Name");
		}

		if (StringUtils.isBlank(dto.getLocation())) {
			throw new BusinessException("Invalid Equipment Location");
		}

		if (dto.getVessel() == null) {
			throw new BusinessException("Vessel not set");
		}

	}

	public boolean isActive() {
		return this.equipmentStatus == EquipmentStatus.ACTIVE;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(final String location) {
		this.location = location;
	}

}
