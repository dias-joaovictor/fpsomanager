package com.modec.fpsomanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.modec.fpsomanager.model.Equipment;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {

	Optional<Equipment> findByCode(String code);

	List<Equipment> findAllByVesselVesselRegistryCode(String vesselRegistryCode);

}
