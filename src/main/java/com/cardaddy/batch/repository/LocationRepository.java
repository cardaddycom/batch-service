package com.cardaddy.batch.repository;

import com.cardaddy.batch.domain.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, String> {

    List<Location> getLocationByZipIn(List<String> zipcodes);

}
