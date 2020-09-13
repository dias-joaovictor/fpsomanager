package com.modec.fpsomanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.modec.fpsomanager.dto.EquipmentDTO;
import com.modec.fpsomanager.dto.VesselDTO;
import com.modec.fpsomanager.endpoint.EquipmentPutRequest;
import com.modec.fpsomanager.exception.BusinessException;
import com.modec.fpsomanager.exception.EntityNotFoundException;
import com.modec.fpsomanager.model.EquipmentStatus;
import com.modec.fpsomanager.repository.EquipmentRepository;
import com.modec.fpsomanager.repository.VesselRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EquipmentServiceTest {

	@Autowired
	private EquipmentService equipmentService;

	@Autowired
	private VesselService vesselService;

	@Autowired
	private VesselRepository vesselRepository;

	@Autowired
	private EquipmentRepository equipmentRepository;

	@Rule
	public final ExpectedException expectedException = ExpectedException.none();

	@Before
	public void before() {
		this.equipmentRepository.deleteAll();
		this.vesselRepository.deleteAll();
	}

	@Test
	public void saveEquipmentRegistryTest() {
		final VesselDTO dto = new VesselDTO("M234DX");
		this.vesselService.saveVesselRegistry(dto);

		final EquipmentDTO equipmentDTO = new EquipmentDTO("Stuff1", "Stuff1CODE", dto, null, "Brazil");
		final EquipmentDTO equipmentDTOReturned = this.equipmentService.saveEquipmentRegistry(equipmentDTO);

		assertEquals(1, this.equipmentRepository.count());
		assertEquals("STUFF1", equipmentDTOReturned.getName());
		assertEquals("STUFF1CODE", equipmentDTOReturned.getCode());
		assertEquals(EquipmentStatus.ACTIVE.getDescription(), equipmentDTOReturned.getEquipmentStatus());
		assertEquals("BRAZIL", equipmentDTOReturned.getLocation());
		assertNotNull(equipmentDTOReturned.getVessel());
		assertEquals("M234DX", equipmentDTOReturned.getVessel().getCode());

	}

	@Test
	public void saveEquipmentRegistryTestFail() {
		this.expectedException.expectMessage("Vessel not set");
		this.expectedException.expect(BusinessException.class);
		final EquipmentDTO equipmentDTO = new EquipmentDTO("Stuff1", "Stuff1CODE", null, null, "Brazil");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);
	}

	@Test
	public void saveEquipmentRegistryTestFail1() {
		this.expectedException.expectMessage("Invalid Equipment Code");
		this.expectedException.expect(BusinessException.class);

		final VesselDTO dto = new VesselDTO("M234DX");
		this.vesselService.saveVesselRegistry(dto);

		final EquipmentDTO equipmentDTO = new EquipmentDTO("Stuff1", null, dto, null, "Brazil");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);
	}

	@Test
	public void saveEquipmentRegistryTestFail2() {
		this.expectedException.expectMessage("Invalid Equipment Name");
		this.expectedException.expect(BusinessException.class);
		final VesselDTO dto = new VesselDTO("M234DX");
		this.vesselService.saveVesselRegistry(dto);

		final EquipmentDTO equipmentDTO = new EquipmentDTO("", "Stuff1CODE", dto, null, "Brazil");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);
	}

	@Test
	public void saveEquipmentRegistryTestFail3() {
		this.expectedException.expectMessage("Invalid Equipment Location");
		this.expectedException.expect(BusinessException.class);
		final VesselDTO dto = new VesselDTO("M234DX");
		this.vesselService.saveVesselRegistry(dto);

		final EquipmentDTO equipmentDTO = new EquipmentDTO("Stuff1", "Stuff1CODE", dto, null, "");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);
	}

	@Test
	public void saveEquipmentRegistryTestFail4() {
		this.expectedException.expectMessage("The equipment STUFF1 [STUFF1CODE] is Active on Vessel M234DX");
		this.expectedException.expect(BusinessException.class);

		final VesselDTO dto = new VesselDTO("M234DX");
		this.vesselService.saveVesselRegistry(dto);

		EquipmentDTO equipmentDTO = new EquipmentDTO("Stuff1", "Stuff1CODE", dto, null, "Brazil");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);

		equipmentDTO = new EquipmentDTO("Stuff1", "Stuff1CODE", dto, null, "Brazil");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);
	}

	@Test
	public void findAllTest() {
		final VesselDTO dto = new VesselDTO("M234DX");
		this.vesselService.saveVesselRegistry(dto);

		EquipmentDTO equipmentDTO = new EquipmentDTO("Stuff1", "Stuff1CODE", dto, null, "Brazil");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);

		equipmentDTO = new EquipmentDTO("Stuff2", "Stuff2CODE", dto, null, "Brazil");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);

		equipmentDTO = new EquipmentDTO("Stuff3", "Stuff3CODE", dto, null, "Brazil");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);

		assertEquals(3, this.equipmentRepository.count());
		assertEquals(3, this.equipmentService.findAll().size());

	}

	@Test
	public void inactiveEquipmentsTest() {

		final VesselDTO dto = new VesselDTO("M234DX");
		this.vesselService.saveVesselRegistry(dto);

		EquipmentDTO equipmentDTO = new EquipmentDTO("Stuff1", "Stuff1CODE", dto, null, "Brazil");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);

		equipmentDTO = new EquipmentDTO("Stuff2", "Stuff2CODE", dto, null, "Brazil");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);

		equipmentDTO = new EquipmentDTO("Stuff3", "Stuff3CODE", dto, null, "Brazil");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);

		equipmentDTO = new EquipmentDTO("Stuff4", "Stuff4CODE", dto, null, "Brazil");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);

		equipmentDTO = new EquipmentDTO("Stuff5", "Stuff5CODE", dto, null, "Brazil");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);

		final EquipmentPutRequest request = new EquipmentPutRequest(Arrays.asList("Stuff4CODE", "StUFF2CodE"));
		final List<EquipmentDTO> inactiveEquipments = this.equipmentService.inactiveEquipments(request);
		assertEquals(2, inactiveEquipments.size());

		assertEquals(2, this.equipmentRepository.findAll().stream().filter(item -> !item.isActive()).count());

	}

	@Test
	public void inactiveEquipmentsTestFail1() {
		this.expectedException.expectMessage("The codes [STUFF4CO, STUFF2COD] were not found");
		this.expectedException.expect(EntityNotFoundException.class);
		final VesselDTO dto = new VesselDTO("M234DX");
		this.vesselService.saveVesselRegistry(dto);

		EquipmentDTO equipmentDTO = new EquipmentDTO("Stuff1", "Stuff1CODE", dto, null, "Brazil");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);

		equipmentDTO = new EquipmentDTO("Stuff2", "Stuff2CODE", dto, null, "Brazil");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);

		equipmentDTO = new EquipmentDTO("Stuff3", "Stuff3CODE", dto, null, "Brazil");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);

		equipmentDTO = new EquipmentDTO("Stuff4", "Stuff4CODE", dto, null, "Brazil");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);

		equipmentDTO = new EquipmentDTO("Stuff5", "Stuff5CODE", dto, null, "Brazil");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);

		final EquipmentPutRequest request = new EquipmentPutRequest(Arrays.asList("Stuff4CO", "StUFF2Cod"));
		final List<EquipmentDTO> inactiveEquipments = this.equipmentService.inactiveEquipments(request);

	}

	@Test
	public void inactiveEquipmentsTestFail2() {
		this.expectedException.expectMessage("The code [STUFF4CO] was not found");
		this.expectedException.expect(EntityNotFoundException.class);
		final VesselDTO dto = new VesselDTO("M234DX");
		this.vesselService.saveVesselRegistry(dto);

		EquipmentDTO equipmentDTO = new EquipmentDTO("Stuff1", "Stuff1CODE", dto, null, "Brazil");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);

		equipmentDTO = new EquipmentDTO("Stuff2", "Stuff2CODE", dto, null, "Brazil");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);

		equipmentDTO = new EquipmentDTO("Stuff3", "Stuff3CODE", dto, null, "Brazil");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);

		equipmentDTO = new EquipmentDTO("Stuff4", "Stuff4CODE", dto, null, "Brazil");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);

		equipmentDTO = new EquipmentDTO("Stuff5", "Stuff5CODE", dto, null, "Brazil");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);

		final EquipmentPutRequest request = new EquipmentPutRequest(Arrays.asList("Stuff4CO", "StUFF2Code"));
		final List<EquipmentDTO> inactiveEquipments = this.equipmentService.inactiveEquipments(request);

	}

	@Test
	public void findAllVesselActiveItensTest() {

		final VesselDTO dto = new VesselDTO("M234DX");
		this.vesselService.saveVesselRegistry(dto);

		EquipmentDTO equipmentDTO = new EquipmentDTO("Stuff1", "Stuff1CODE", dto, null, "Brazil");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);

		equipmentDTO = new EquipmentDTO("Stuff2", "Stuff2CODE", dto, null, "Brazil");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);

		equipmentDTO = new EquipmentDTO("Stuff3", "Stuff3CODE", dto, null, "Brazil");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);

		equipmentDTO = new EquipmentDTO("Stuff4", "Stuff4CODE", dto, null, "Brazil");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);

		equipmentDTO = new EquipmentDTO("Stuff5", "Stuff5CODE", dto, null, "Brazil");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);

		final EquipmentPutRequest request = new EquipmentPutRequest(
				Arrays.asList("Stuff4CODE", "StUFF2CodE", "StUFF1CodE"));
		final List<EquipmentDTO> inactiveEquipments = this.equipmentService.inactiveEquipments(request);
		assertEquals(3, inactiveEquipments.size());

		assertEquals(3, this.equipmentRepository.findAll().stream().filter(item -> !item.isActive()).count());

		assertEquals(2, this.equipmentService.findAllVesselActiveItens("M234DX").size());

	}

	@Test
	public void findAllVesselActiveItensTest2() {

		final VesselDTO dto = new VesselDTO("M234DX");
		this.vesselService.saveVesselRegistry(dto);

		EquipmentDTO equipmentDTO = new EquipmentDTO("Stuff1", "Stuff1CODE", dto, null, "Brazil");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);

		equipmentDTO = new EquipmentDTO("Stuff2", "Stuff2CODE", dto, null, "Brazil");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);

		equipmentDTO = new EquipmentDTO("Stuff3", "Stuff3CODE", dto, null, "Brazil");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);

		equipmentDTO = new EquipmentDTO("Stuff4", "Stuff4CODE", dto, null, "Brazil");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);

		equipmentDTO = new EquipmentDTO("Stuff5", "Stuff5CODE", dto, null, "Brazil");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);

		final EquipmentPutRequest request = new EquipmentPutRequest(
				Arrays.asList("Stuff4CODE", "StUFF2CodE", "StUFF1CodE"));
		final List<EquipmentDTO> inactiveEquipments = this.equipmentService.inactiveEquipments(request);
		assertEquals(3, inactiveEquipments.size());

		assertEquals(3, this.equipmentRepository.findAll().stream().filter(item -> !item.isActive()).count());

		assertEquals(2, this.equipmentService.findAllVesselActiveItens("m234Dx").size());

	}

	@Test
	public void findAllVesselActiveItensTestFail1() {
		this.expectedException.expectMessage("Vessel with Registry code m234Dx45 not found");
		this.expectedException.expect(EntityNotFoundException.class);

		final VesselDTO dto = new VesselDTO("M234DX");
		this.vesselService.saveVesselRegistry(dto);

		EquipmentDTO equipmentDTO = new EquipmentDTO("Stuff1", "Stuff1CODE", dto, null, "Brazil");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);

		equipmentDTO = new EquipmentDTO("Stuff2", "Stuff2CODE", dto, null, "Brazil");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);

		equipmentDTO = new EquipmentDTO("Stuff3", "Stuff3CODE", dto, null, "Brazil");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);

		equipmentDTO = new EquipmentDTO("Stuff4", "Stuff4CODE", dto, null, "Brazil");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);

		equipmentDTO = new EquipmentDTO("Stuff5", "Stuff5CODE", dto, null, "Brazil");
		this.equipmentService.saveEquipmentRegistry(equipmentDTO);

		final EquipmentPutRequest request = new EquipmentPutRequest(
				Arrays.asList("Stuff4CODE", "StUFF2CodE", "StUFF1CodE"));
		final List<EquipmentDTO> inactiveEquipments = this.equipmentService.inactiveEquipments(request);
		assertEquals(3, inactiveEquipments.size());

		assertEquals(3, this.equipmentRepository.findAll().stream().filter(item -> !item.isActive()).count());

		this.equipmentService.findAllVesselActiveItens("m234Dx45");

	}

}
