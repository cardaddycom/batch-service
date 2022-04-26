package com.cardaddy.batch.domain.lookup;

import com.cardaddy.batch.domain.base.BaseEntity;
import com.cardaddy.batch.domain.base.TransitoryData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.search.engine.backend.types.Aggregable;
import org.hibernate.search.engine.backend.types.Projectable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import javax.persistence.Entity;

/**
 *
 * @author george
 */
@Data
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VehicleTransmission extends BaseEntity implements TransitoryData {

    @FullTextField(projectable = Projectable.YES)
    @KeywordField(name = "name_aggregate_idx", aggregable = Aggregable.YES)
    @KeywordField(name = "name_lowercase_idx", normalizer  = "lowercase", projectable = Projectable.YES)
    private String name;

}
