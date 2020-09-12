package com.modec.fpsomanager.model;

import java.util.Arrays;

import com.modec.fpsomanager.exception.BusinessException;

public enum EquipmentStatus {

	ACTIVE("Active", 1), INACTIVE("Inactive", 2);

	private String description;

	private int value;

	private EquipmentStatus(final String description, final int value) {
		this.description = description;
		this.value = value;
	}

	public String getDescription() {
		return this.description;
	}

	public int getValue() {
		return this.value;
	}

	public static EquipmentStatus fromValue(final int value) {
		return Arrays.asList(values())//
				.stream()//
				.filter(item -> item.getValue() == value)//
				.findFirst()//
				.orElseThrow(() -> new BusinessException("Invalid Equipment status"));
	}

	public static EquipmentStatus fromIntegerValue(final Integer value) {
		return fromValue(value == null ? 0 : value.intValue());
	}

}
