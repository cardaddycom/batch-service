package com.cardaddy.batch.repository;

import com.cardaddy.batch.domain.lookup.VehicleCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleConditionRepository extends JpaRepository<VehicleCondition, Long> {

}
