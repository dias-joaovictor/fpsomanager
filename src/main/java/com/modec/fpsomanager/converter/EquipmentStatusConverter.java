package com.modec.fpsomanager.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.modec.fpsomanager.model.EquipmentStatus;

@Converter
public class EquipmentStatusConverter implements AttributeConverter<EquipmentStatus, Integer> {

	@Override
	public Integer convertToDatabaseColumn(final EquipmentStatus attribute) {
		return attribute.getValue();
	}

	@Override
	public EquipmentStatus convertToEntityAttribute(final Integer dbData) {
		return EquipmentStatus.fromIntegerValue(dbData);
	}

}
