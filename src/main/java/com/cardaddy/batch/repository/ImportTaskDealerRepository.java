package com.cardaddy.batch.repository;

import com.cardaddy.batch.domain.account.DealerProfile;
import com.cardaddy.batch.domain.task.imports.ImportTask;
import com.cardaddy.batch.domain.task.imports.ImportTaskDealer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportTaskDealerRepository extends JpaRepository<ImportTaskDealer, Long> {

    public ImportTaskDealer findByDealerProfileAndImportTask(DealerProfile dealerProfile, ImportTask importTask);

}
