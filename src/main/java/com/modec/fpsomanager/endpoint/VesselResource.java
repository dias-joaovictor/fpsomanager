package com.modec.fpsomanager.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.modec.fpsomanager.dto.VesselDTO;
import com.modec.fpsomanager.service.VesselService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/vessels")
@Api
public class VesselResource {

	@Autowired
	private VesselService vesselService;

	@GetMapping
	public List<VesselDTO> getAllVessels() {
		return this.vesselService.findAll();
	}

	@GetMapping("/byId")
	public VesselDTO getVessel(@RequestParam final String code) {
		return this.vesselService.findByRegistryCode(code);
	}

	@PostMapping
	public ResponseEntity<VesselDTO> addVessel(@RequestBody final VesselDTO vesselDTO) {
		return new ResponseEntity<>(this.vesselService.saveVesselRegistry(vesselDTO), HttpStatus.CREATED);
	}

}
