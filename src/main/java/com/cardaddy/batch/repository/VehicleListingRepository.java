package com.cardaddy.batch.repository;

import com.cardaddy.batch.domain.listing.VehicleListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleListingRepository extends JpaRepository<VehicleListing, Long> {
}
