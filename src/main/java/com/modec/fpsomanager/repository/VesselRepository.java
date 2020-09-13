package com.modec.fpsomanager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.modec.fpsomanager.model.Vessel;

@Repository
public interface VesselRepository extends JpaRepository<Vessel, Integer> {

	Optional<Vessel> findByVesselRegistryCode(String code);
}
