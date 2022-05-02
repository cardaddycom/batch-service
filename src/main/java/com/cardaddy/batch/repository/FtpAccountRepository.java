package com.cardaddy.batch.repository;

import com.cardaddy.batch.domain.task.lookup.FtpAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FtpAccountRepository extends JpaRepository<FtpAccount, Long> {
}
