package com.cardaddy.batch.repository;

import com.cardaddy.batch.domain.lookup.VehicleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleModelRepository extends JpaRepository<VehicleModel, Long> {

    List<VehicleModel> getVehicleModelByNameIn(List<String> names);
}
