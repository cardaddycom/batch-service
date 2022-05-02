package com.cardaddy.batch.domain.base;

import lombok.Data;
import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;

import javax.persistence.*;
import java.io.Serializable;

@Data
@MappedSuperclass
public abstract class BaseEntity implements Serializable, TransitoryBaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericField(name = "id", sortable = Sortable.YES)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGen")
    @SequenceGenerator(name = "seqGen", sequenceName = "seq", initialValue = 1)
    private Long id;

    @Transient
    private final Long tempId = -System.nanoTime();

}
