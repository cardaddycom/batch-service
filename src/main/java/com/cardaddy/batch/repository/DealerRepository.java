package com.cardaddy.batch.repository;

import com.cardaddy.batch.domain.account.DealerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface DealerRepository extends JpaRepository<DealerProfile, Long> {

    List<DealerProfile> getDealerProfileByCustomerNumberIn(Set<String> dealerIds);

}
