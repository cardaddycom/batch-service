package com.cardaddy.batch.repository;

import com.cardaddy.batch.domain.lookup.VehicleYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleYearRepository extends JpaRepository<VehicleYear, Long> {

    List<VehicleYear> getVehicleYearByNameIn(List<String> names);
}
