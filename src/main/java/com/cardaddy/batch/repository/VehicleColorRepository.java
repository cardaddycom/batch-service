package com.cardaddy.batch.repository;

import com.cardaddy.batch.domain.lookup.VehicleColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleColorRepository extends JpaRepository<VehicleColor, Long> {

    List<VehicleColor> getVehicleColorByNameIn(List<String> names);
}
