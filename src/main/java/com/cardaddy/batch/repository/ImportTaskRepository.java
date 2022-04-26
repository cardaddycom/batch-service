package com.cardaddy.batch.repository;

import com.cardaddy.batch.domain.task.imports.ImportTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportTaskRepository extends JpaRepository<ImportTask, Long> {
}
