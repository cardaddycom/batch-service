package com.cardaddy.batch.repository;

import com.cardaddy.batch.domain.lookup.InventoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryTypeRepository extends JpaRepository<InventoryType, Long> {

}
