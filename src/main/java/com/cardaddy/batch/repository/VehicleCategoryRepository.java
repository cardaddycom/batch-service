package com.cardaddy.batch.repository;

import com.cardaddy.batch.domain.lookup.VehicleCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleCategoryRepository extends JpaRepository<VehicleCategory, Long> {

}
