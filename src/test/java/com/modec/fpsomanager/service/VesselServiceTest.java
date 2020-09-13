package com.modec.fpsomanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.modec.fpsomanager.dto.VesselDTO;
import com.modec.fpsomanager.repository.VesselRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class VesselServiceTest {

	@Autowired
	private VesselRepository vesselRepository;

	@Autowired
	private VesselService vesselService;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Before
	public void before() {
		this.vesselRepository.deleteAll();
	}

	@Test
	public void saveVesselRegistryTest1() {
		final VesselDTO dto = new VesselDTO("M234DX");
		this.vesselService.saveVesselRegistry(dto);
		assertEquals(1l, this.vesselRepository.count());
	}

	@Test
	public void saveVesselRegistryTestFail() {
		this.expectedException.expectMessage("There is a vessel registered with code M234DX");
		final VesselDTO dto = new VesselDTO("M234DX");
		this.vesselService.saveVesselRegistry(dto);
		this.vesselService.saveVesselRegistry(dto);
	}

	@Test
	public void saveVesselRegistryTestFail2() {
		this.expectedException.expectMessage("Invalid vessel data");
		final VesselDTO dto = new VesselDTO("");
		this.vesselService.saveVesselRegistry(dto);
	}

	@Test
	public void findAllTest() {
		this.vesselService.saveVesselRegistry(new VesselDTO("M100"));
		this.vesselService.saveVesselRegistry(new VesselDTO("M101"));
		this.vesselService.saveVesselRegistry(new VesselDTO("M102"));
		this.vesselService.saveVesselRegistry(new VesselDTO("M103"));
		this.vesselService.saveVesselRegistry(new VesselDTO("M104"));
		this.vesselService.saveVesselRegistry(new VesselDTO("M105"));
		assertEquals(6, this.vesselRepository.count());
	}

	@Test
	public void findAllTestEmpty() {
		assertEquals(0, this.vesselRepository.count());
	}

	@Test
	public void findByRegistryCodeTest() {
		this.vesselService.saveVesselRegistry(new VesselDTO("M100"));
		this.vesselService.saveVesselRegistry(new VesselDTO("M101"));
		this.vesselService.saveVesselRegistry(new VesselDTO("M102"));
		this.vesselService.saveVesselRegistry(new VesselDTO("M103"));
		this.vesselService.saveVesselRegistry(new VesselDTO("M104"));
		this.vesselService.saveVesselRegistry(new VesselDTO("M105"));
		assertNotNull(this.vesselService.findByRegistryCode("M100"));
		assertNotNull(this.vesselService.findByRegistryCode("M101"));
		assertNotNull(this.vesselService.findByRegistryCode("M102"));
		assertNotNull(this.vesselService.findByRegistryCode("M103"));
		assertNotNull(this.vesselService.findByRegistryCode("M104"));
		assertNotNull(this.vesselService.findByRegistryCode("M105"));

	}

	@Test
	public void findByRegistryCodeTestFail() {
		this.expectedException.expectMessage("There is no vessel with registry code M106");
		this.vesselService.saveVesselRegistry(new VesselDTO("M100"));
		this.vesselService.saveVesselRegistry(new VesselDTO("M101"));
		this.vesselService.saveVesselRegistry(new VesselDTO("M102"));
		this.vesselService.saveVesselRegistry(new VesselDTO("M103"));
		this.vesselService.saveVesselRegistry(new VesselDTO("M104"));
		this.vesselService.saveVesselRegistry(new VesselDTO("M105"));
		assertNotNull(this.vesselService.findByRegistryCode("M106"));

	}

	@Test
	public void findByRegistryCodeTestFail2() {
		this.expectedException.expectMessage("Inavalid Vessel Registry Code.");
		assertNotNull(this.vesselService.findByRegistryCode(null));

	}

	@Test
	public void findByRegistryCodeTestFail3() {
		this.expectedException.expectMessage("Inavalid Vessel Registry Code.");
		assertNotNull(this.vesselService.findByRegistryCode(""));

	}

	@Test
	public void testOk() {
		assertTrue(true);
	}
}
