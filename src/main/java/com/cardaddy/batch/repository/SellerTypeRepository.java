package com.cardaddy.batch.repository;

import com.cardaddy.batch.domain.lookup.SellerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerTypeRepository extends JpaRepository<SellerType, Long> {

}
