package com.cardaddy.batch.repository;

import com.cardaddy.batch.domain.lookup.VehicleBodyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleBodyTypeRepository extends JpaRepository<VehicleBodyType, Long> {

}
