package com.cardaddy.batch.repository;

import com.cardaddy.batch.domain.lookup.VehicleTrim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleTrimRepository extends JpaRepository<VehicleTrim, Long> {

    List<VehicleTrim> getVehicleTrimByNameIn(List<String> names);
}
