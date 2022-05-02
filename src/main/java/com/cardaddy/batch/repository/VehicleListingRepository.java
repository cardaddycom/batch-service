package com.cardaddy.batch.repository;

import com.cardaddy.batch.domain.listing.VehicleListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleListingRepository extends JpaRepository<VehicleListing, Long> {

    List<VehicleListing> getVehicleListingByVinIn(List<String> vinNumbers);

    List<VehicleListing> getVehicleListingByIdIn(List<Long> pks);

    @Query("select v.id from VehicleListing v where v.importTask.id = :importTaskId and v.jobExecutionId != :jobId")
    List<Long> vehiclesToBeDeleted(Long importTaskId, Long jobId);
}
