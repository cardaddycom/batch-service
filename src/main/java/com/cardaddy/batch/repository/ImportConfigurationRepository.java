package com.cardaddy.batch.repository;

import com.cardaddy.batch.domain.task.lookup.ImportConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImportConfigurationRepository extends JpaRepository<ImportConfiguration, Long> {

    @Query("SELECT importConfig FROM ImportConfiguration importConfig WHERE importConfig.importSystem.id = ?1 order by importConfig.csvColumnPosition asc ")
    List<ImportConfiguration> getImportConfiguration(Long importSystemId);

}
