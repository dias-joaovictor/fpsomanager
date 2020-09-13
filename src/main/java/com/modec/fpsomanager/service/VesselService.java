package com.modec.fpsomanager.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modec.fpsomanager.dto.VesselDTO;
import com.modec.fpsomanager.exception.BusinessException;
import com.modec.fpsomanager.exception.EntityNotFoundException;
import com.modec.fpsomanager.model.Vessel;
import com.modec.fpsomanager.repository.VesselRepository;

@Service
public class VesselService {

	@Autowired
	private VesselRepository vesselRepository;

	public VesselDTO saveVesselRegistry(final VesselDTO vesselDTO) {
		final Vessel vessel = Vessel.fromDTO(vesselDTO);

		if (this.vesselRepository.findByVesselRegistryCode(vessel.getVesselRegistryCode()).isPresent()) {
			throw new BusinessException(MessageFormat.format(//
					"There is a vessel registered with code {0}", //
					vessel.getVesselRegistryCode()));
		}

		return this.vesselRepository.saveAndFlush(vessel).toDTO();
	}

	public List<VesselDTO> findAll() {
		return this.vesselRepository//
				.findAll()//
				.stream()//
				.filter(Objects::nonNull)//
				.map(Vessel::toDTO) //
				.collect(Collectors.toList());
	}

	public VesselDTO findByRegistryCode(final String code) {
		if (StringUtils.isBlank(code)) {
			throw new BusinessException("Inavalid Vessel Registry Code.");
		}

		return this.vesselRepository//
				.findByVesselRegistryCode(code)//
				.orElseThrow(() -> new EntityNotFoundException(
						MessageFormat.format("There is no vessel with registry code {0}", code)))//
				.toDTO();
	}

	public VesselRepository getVesselRepository() {
		return this.vesselRepository;
	}

}
