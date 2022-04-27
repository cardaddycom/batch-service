package com.cardaddy.batch.repository;

import com.cardaddy.batch.domain.lookup.VehicleMake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface VehicleMakeRepository extends JpaRepository<VehicleMake, Long> {

    List<VehicleMake> getVehicleMakeByNameIn(Set<String> names);
}
