package com.cardaddy.batch.repository;

import com.cardaddy.batch.domain.account.PartnerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartnerRepository extends JpaRepository<PartnerProfile, Long> {

    List<PartnerProfile> getPartnerProfileByIdIn(List<Long> partnerIds);

}
