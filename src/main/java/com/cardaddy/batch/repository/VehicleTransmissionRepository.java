package com.cardaddy.batch.repository;

import com.cardaddy.batch.domain.lookup.VehicleTransmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleTransmissionRepository extends JpaRepository<VehicleTransmission, Long> {

}
