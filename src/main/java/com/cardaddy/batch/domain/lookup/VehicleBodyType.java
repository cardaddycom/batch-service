package com.cardaddy.batch.domain.lookup;

import com.cardaddy.batch.domain.base.BaseEntity;
import com.cardaddy.batch.domain.base.TransitoryData;
import lombok.Data;
import org.hibernate.search.engine.backend.types.Aggregable;
import org.hibernate.search.engine.backend.types.Projectable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import javax.persistence.*;

/**
 *
 * @author george
 */
@Data
@Entity
public class VehicleBodyType extends BaseEntity implements TransitoryData {

    @ManyToOne
    @JoinColumn(name = "vehicle_category_id", nullable = false)
    private VehicleCategory vehicleCategory;

    @Column(nullable = false)
    @FullTextField(projectable = Projectable.YES)
    @KeywordField(name = "name_aggregate_idx", aggregable = Aggregable.YES)
    @KeywordField(name = "name_lowercase_idx", normalizer  = "lowercase", projectable = Projectable.YES)
    private String name;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private boolean display;

}
