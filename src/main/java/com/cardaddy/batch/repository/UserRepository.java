package com.cardaddy.batch.repository;

import com.cardaddy.batch.domain.account.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<UserProfile, Long> {

    List<UserProfile> getUserProfileByIdIn(Set<Long> userIds);

}
