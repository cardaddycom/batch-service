package com.cardaddy.batch.repository;

import com.cardaddy.batch.domain.account.DealerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealerRepository extends JpaRepository<DealerProfile, Long> {

    List<DealerProfile> getDealerProfileByCustomerNumberIn(List<String> dealerIds);

}
