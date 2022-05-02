package com.cardaddy.batch.repository;

import com.cardaddy.batch.repository.listing.VehicleListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleListingRepository extends JpaRepository<VehicleListing, Long> {

    List<VehicleListing> getVehicleListingByVinIn(List<String> vinNumbers);
}
