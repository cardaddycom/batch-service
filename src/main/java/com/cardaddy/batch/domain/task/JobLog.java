package com.cardaddy.batch.domain.task;

import com.cardaddy.batch.domain.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author george
 */
@Data
@Entity
@Table(name = "job_log")
public class JobLog extends BaseEntity {

    @Column(name = "job_completed", nullable = false, columnDefinition = "BIT")
    private boolean jobCompleted;

}
