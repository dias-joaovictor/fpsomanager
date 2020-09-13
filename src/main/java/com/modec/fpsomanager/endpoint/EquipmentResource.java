package com.modec.fpsomanager.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.modec.fpsomanager.dto.EquipmentDTO;
import com.modec.fpsomanager.service.EquipmentService;

@RestController
@RequestMapping("/equipments")
public class EquipmentResource {

	@Autowired
	private EquipmentService equipmentService;

	@GetMapping
	public List<EquipmentDTO> findAll() {
		return this.equipmentService.findAll();
	}

	@GetMapping("/actives/byVesselCode")
	public List<EquipmentDTO> findAllActiveByVesselCode(@RequestParam final String code) {
		return this.equipmentService.findAllVesselActiveItens(code);
	}

	@PostMapping
	public ResponseEntity<EquipmentDTO> saveEquipment(@RequestBody final EquipmentDTO equipmentDTO) {
		return new ResponseEntity<>(//
				this.equipmentService.saveEquipmentRegistry(equipmentDTO), //
				HttpStatus.CREATED);
	}

	@PutMapping("/inactive")
	public List<EquipmentDTO> inativateEquipments(@RequestBody final EquipmentPutRequest equipmentPutRequest) {
		return this.equipmentService.inactiveEquipments(equipmentPutRequest);

	}

}
