package com.cardaddy.batch.domain.task.lookup;

import com.cardaddy.batch.domain.base.BaseEntity;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 *
 * @author george
 */
@Data
@Entity
@ToString
public class FileType extends BaseEntity {


    @Column(nullable = false)
    private String separator;

}
