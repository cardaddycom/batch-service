package com.cardaddy.batch.domain.base;

import lombok.Data;
import org.hibernate.search.engine.backend.types.Aggregable;
import org.hibernate.search.engine.backend.types.Projectable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import javax.persistence.MappedSuperclass;

/**
 *
 * @author George
 */
@Data
@MappedSuperclass
public class LookupBaseEntity extends VoteEntity implements TransitoryData {

    @FullTextField
    @KeywordField(name = "name_aggregate_idx", aggregable = Aggregable.YES)
    @KeywordField(name = "name_lowercase_idx", normalizer  = "lowercase", projectable = Projectable.YES)
    private String name;

}
