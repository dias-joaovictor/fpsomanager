package com.modec.fpsomanager.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modec.fpsomanager.dto.EquipmentDTO;
import com.modec.fpsomanager.endpoint.EquipmentPutRequest;
import com.modec.fpsomanager.exception.BusinessException;
import com.modec.fpsomanager.exception.EntityNotFoundException;
import com.modec.fpsomanager.model.Equipment;
import com.modec.fpsomanager.model.EquipmentStatus;
import com.modec.fpsomanager.repository.EquipmentRepository;

@Service
public class EquipmentService {

	private static final String VESSEL_NOT_FOUND = "Vessel with Registry code {0} not found";

	@Autowired
	private EquipmentRepository equipmentRepository;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private VesselService vesselService;

	public List<EquipmentDTO> findAll() {
		return this.equipmentRepository//
				.findAll()//
				.stream()//
				.map(Equipment::toDTO)//
				.collect(Collectors.toList());

	}

	public List<EquipmentDTO> findAllVesselActiveItens(final String code) {
		if (StringUtils.isBlank(code)) {
			throw new BusinessException("The vessel code is invalid");
		}

		this.vesselService.getVesselRepository().findByVesselRegistryCode(code.trim().toUpperCase())
				.orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(VESSEL_NOT_FOUND, code)));

		return this.equipmentRepository//
				.findAllByVesselVesselRegistryCode(code.trim().toUpperCase())//
				.stream()//
				.filter(Objects::nonNull)//
				.filter(Equipment::isActive)//
				.map(Equipment::toDTO)//
				.collect(Collectors.toList());
	}

	public EquipmentDTO saveEquipmentRegistry(final EquipmentDTO equipmentDTO) {
		final Equipment equipment = Equipment.fromDTO(equipmentDTO);

		final Optional<Equipment> optEquipmentFound = this.equipmentRepository.findByCode(equipment.getCode());

		if (optEquipmentFound.isPresent()) {
			throw new BusinessException(MessageFormat.format(//
					"The equipment {0} [{1}] is {2} on Vessel {3}", //
					optEquipmentFound.get().getName(), //
					optEquipmentFound.get().getCode(), //
					optEquipmentFound.get().getEquipmentStatus().getDescription(), //
					optEquipmentFound.get().getVessel().getVesselRegistryCode()));
		}

		equipment.setVessel(this.vesselService//
				.getVesselRepository()//
				.findByVesselRegistryCode(equipment.getVessel().getVesselRegistryCode())//
				.orElseThrow(() -> new EntityNotFoundException(
						MessageFormat.format(VESSEL_NOT_FOUND, equipment.getVessel().getVesselRegistryCode()))));

		return this.equipmentRepository.saveAndFlush(equipment).toDTO();
	}

	public List<EquipmentDTO> inactiveEquipments(final EquipmentPutRequest equipmentPutRequest) {
		if (equipmentPutRequest == null //
				|| equipmentPutRequest.getCodes() == null //
				|| equipmentPutRequest.getCodes().isEmpty()) {
			throw new BusinessException("The Requested object is invalid");
		}

		final List<String> notFoundObjects = new ArrayList<>();
		final List<Equipment> equipments = new ArrayList<>();
		equipmentPutRequest//
				.getCodes()//
				.stream()//
				.filter(Objects::nonNull).forEach(item -> {
					final String value = item.trim().toUpperCase();
					final Optional<Equipment> optEquipment = this.equipmentRepository.findByCode(value);
					if (optEquipment.isPresent()) {
						final Equipment equipment = optEquipment.get();
						equipment.setEquipmentStatus(EquipmentStatus.INACTIVE);
						equipments.add(equipment);
					} else {
						notFoundObjects.add(value);
					}
				});

		if (!notFoundObjects.isEmpty()) {
			String message = "";
			if (notFoundObjects.size() == 1) {
				message = "The code {0} was not found";
			} else {
				message = "The codes {0} were not found";
			}
			throw new EntityNotFoundException(MessageFormat.format(message, notFoundObjects));
		}
		this.equipmentRepository.saveAll(equipments);
		this.equipmentRepository.flush();
		return equipments//
				.stream()//
				.map(Equipment::toDTO)//
				.collect(Collectors.toList());
	}
}
