package com.cardaddy.batch.domain.base;

import lombok.Data;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class StatefulEntity extends BaseEntity {

    @GenericField
    @Column(nullable = false, columnDefinition = "BIT")
    private boolean active = true;

}
