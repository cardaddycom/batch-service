package com.cardaddy.batch.repository;

import com.cardaddy.batch.domain.task.lookup.ImportConfiguration;
import com.cardaddy.batch.domain.task.lookup.ImportSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImportConfigurationRepository extends JpaRepository<ImportConfiguration, Long> {

    List<ImportConfiguration> getByImportSystemOrderByCsvColumnPositionAsc(ImportSystem importSystem);
}
