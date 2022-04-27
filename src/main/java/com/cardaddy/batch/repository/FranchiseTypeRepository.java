package com.cardaddy.batch.repository;

import com.cardaddy.batch.domain.lookup.FranchiseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FranchiseTypeRepository extends JpaRepository<FranchiseType, Long> {

}
